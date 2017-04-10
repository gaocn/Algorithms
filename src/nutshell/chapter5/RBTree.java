package nutshell.chapter5;



/**
 * Created by 高文文 on 2017/3/21.
 *
 *红黑树定义：
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class RBTree<T extends Comparable<T>> {

    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    private RBNode<T> mRoot;

    public class RBNode<T extends Comparable<T>> {
        T key;              //关键字
        boolean color;      //节点颜色
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;
        public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }


    /**
     * 对节点x进行坐旋转 （分两种情况：x是p的左孩子、右孩子）
     *        p                                    p
     *      /                                     /
     *     x             rotateLeft              y
     *   /  \          ==============>          / \
     *  lx   y                                 x  ry
     *      / \                               / \
     *     ly ry                             lx  ly
     */
    private void rotateLeft(RBNode<T> x) {
        RBNode<T> y = x.right;

        //1. 断开x-y，并将y的左孩子指向x，同时若y左孩子不为空需要改变左孩子的父指针到x
        x.right = y.left;
        if(y.left != null) {
            y.left.parent = x;
        }

        //2. 将y节点的父指针指向x的父亲节点,
        //     若x父节点为空则y为mRoot，
        //     若不为空有两种情况(x分别为p的左孩子、右孩子)
        y.parent = x.parent;
        if(x.parent == null) {
            this.mRoot = y;
        } else {

            if(x.parent.left == x) { // x是左孩子
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        }
        //3. 设置x为y的左孩子
        y.left = x;
        //4. 更新x父节点指针
        x.parent = y;
    }

    /**
     * 对y节点进行右旋转 (分两种情况：x是p的左孩子、右孩子）)
     *        p                                     p
     *      /                                      /
     *     y             rotateRight              x
     *   /  \           =============>          / \
     *  x   ry                                 lx  y
     * / \                                        / \
     *lx rx                                      ry  ry
     *
     */
    private void rotateRight(RBNode<T> y) {
        RBNode<T> x = y.left;

        //1. 断开y与x，将x右孩子指向y左孩子同时修改x右孩子父指针
        y.left = x.right;
        if(x.right != null) {
            x.right.parent = y;
        }

        //2. 修改x父指针为y父指针的
        //     若x父节点为空则y为mRoot，
        //     若不为空有两种情况(x分别为p的左孩子、右孩子)
        x.parent = y.parent;
        if(y.parent == null) {
            this.mRoot = x;
        } else {
            if(y.parent.left == y) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        }

        //3. 修改y为x右孩子
        x.right = y;
        //4. 更新y父指针为x
        y.parent = x;
    }


    /**
     * 插入节点
     *   首先隔根据二叉查找树的性质插入节点，然后通过调整重新平衡红黑树
     *
     *
     */
    private void insert(RBNode<T> node) {

        RBNode<T> x = this.mRoot;
        RBNode<T> y = null;
        int cmp;
        //1. 根据查找树性质插入节点
        while(x != null) {
            y = x;
            cmp = node.key.compareTo(y.key);
            if(cmp < 0) {
                x = x.left;
            } else if(cmp > 0) {
                x = x.right;
            } else {
                //已经存在节点
                throw new IllegalArgumentException("Node already exists[node.key=" + node.key + "]");
            }
        }
        node.parent = y;
        if(y != null) {
            cmp = node.key.compareTo(y.key);
            if(cmp < 0) {
                y.left = node;
            } else {
                y.right = node;
            }
        } else {
            this.mRoot = node;
        }

        //2. 将node颜色着色为红色
        node.color = RED;
        //3. 对二叉树进行重新着色
        insertFixUp(node);
    }
    /** insert外部接口 **/
    public void insert(T key) {
        RBNode<T> node = new RBNode<>(BLACK, key, null, null, null);
        if(node != null) { //节点创建成功
            insert(node);
        }
    }

    /**
     * 插入元素后，二叉树会不平衡，对其进行调整并重新着色
     */
    private void insertFixUp(RBNode<T> node) {
        RBNode<T> parent = null, gparent = null;

        while((parent = parentOf(node)) != null && isRed(parent)) {
            gparent = parentOf(parent);

            if(gparent.left == parent) {
                /**
                 * if uncle is not RED
                 *   LR --(left rotate)--> LL  --(right rotate)--> coloring
                 * else
                 *   coloring
                 */
                //case 1. uncle is RED
                RBNode<T> uncle = gparent.right;
                if(uncle != null && isRed(uncle)) {
                    setRed(gparent);
                    setBlack(parent);
                    setBlack(uncle);
                    node = gparent;
                    continue;
                }
                // case 2. unclue is BLACK, and node is right child of parent
                if(parent.right == node) {
                    rotateLeft(parent);
                    RBNode<T> temp = parent;
                    parent = node;
                    node = temp;
                }
                //case 3. uncle is BLACK, and node is left child of parent
                rotateRight(gparent);
                // coloring
                setBlack(parent);
                setRed(gparent);


            } else {
                /**
                 * if uncle is not RED
                 *   RL --(right rotate)--> RR  --(left rotate)--> coloring
                 * else
                 *   coloring
                 */
                RBNode<T> uncle = gparent.left;
                //case 1. uncle is RED
                if(uncle != null && isRed(uncle)) {
                    setRed(gparent);
                    setBlack(parent);
                    setBlack(uncle);
                    node = gparent;
                    continue;
                }
                //case 2. uncle is BLACK, and node is left child of parent
                if(parent.left == node) {
                    rotateRight(parent);
                    RBNode<T> temp = parent;
                    parent = node;
                    node = temp;
                }

                //case 3. uncle is BLACK, and node is right child of parent
                rotateLeft(gparent);
                // coloring
                setBlack(parent);
                setRed(gparent);
            }

        }

        //why
        setBlack(this.mRoot);

    }


    public void remove(T key){}


    /**
     * there three cases:
     *  1. to-be-deleted node has no child
     *  2. to-be-deleted node has one child(left or right)
     *  3. to-be-deleted node has two child
     */
    private void remove(RBNode<T> node) {
        RBNode<T> child = null, parent = null;
        boolean color;

        if(node.left != null && node.right != null) {
            //case 3 被删除节点有2个子节点
            RBNode<T> replace = node.right;
            while(replace.left  != null)replace = replace.left;
            //TODO 有待验证 ！！首先node与replace进行值交换，然后对replace进行类似删除操作
            node.key = replace.key;

            parent = replace.parent;
            child  = replace.right;
            color = replace.color;

            if(child != null)
                child.parent = parent;
            if(parent != null) {
                if(replace == parent.left) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            } else {
                this.mRoot = child;
            }

            if(color == BLACK)
                removeFixUp(child, parent);
            //delete node
            replace = null;

        } else {
            //case 1,2 被删除节点有0个或1个子节点
            parent = parentOf(node);
            color = node.color;
            if(node.left != null) {
                child = node.left;
            } else {
                child = node.right;
            }

            if(child != null)
                child.parent = parent;
            if(parent != null) {
                if(node == parent.left) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            } else {
                this.mRoot = child;
            }

            if(color = BLACK)
                removeFixUp(child, parent);

            //delete node
            node = null;
        }
    }

    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {


    }




    private RBNode<T> parentOf(RBNode<T> node) {
        if(node != null) {
            return node.parent;
        }
        return null;
    }
    private boolean isRed(RBNode<T> node) {
        return node.color == RED;
    }
    private void setBlack(RBNode<T> node) {
        if(node != null) {
            node.color = BLACK;
        }
    }
    private void setRed(RBNode<T> node) {
        if(node != null) {
            node.color = RED;
        }
    }





}
