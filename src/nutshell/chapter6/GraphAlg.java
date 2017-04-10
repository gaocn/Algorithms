package nutshell.chapter6;

import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * Created by 高文文 on 2017/3/29.
 */
public class GraphAlg {

    public static void main(String[] args) {

    }

    enum COLOR {
        White(0),       //未被访问的顶点
        Gray(1),        //被访问过当邻接顶点没有被访问完的顶点
        Black(1);       //被访问且邻接顶点也被访问完毕的顶点
        COLOR(int color){
            this.color = color;
        }
        private int color;
    }
    enum EdgeType {
        Tree(0),
        Forward(1),
        Backward(2),  //(u, v)中u，v均为黑色，且d[u] < d[v]
        Cross(3);     //交叉边只在有向图中存在，(u, v)中u,v均为黑色，且d[u] > d[v]
        private int type;
        EdgeType(int type) {
            this.type = type;
        }
    }
    static class Graph {
        LinkedList<Integer>[] edges;
        int vertexNum;
        int edgeNum;
        int[] outdegree;
        boolean isDirescted;
    }

    static class EdgeLable {
        int u, v;
        EdgeType type;
        public EdgeLable(int u, int v, EdgeType type) {
            this.u = u;
            this.v = v;
            this.type = type;
        }
    }
    static class Counter {
        int counter;
        public int getCounter() {
            return counter;
        }
        public void setCounter(int counter) {
            this.counter = counter;
        }
        public void autoInc() {
            counter++;
        }
        public Counter(int counter) {
            this.counter = counter;
        }
    }

    public static void depthFirstSearch(Graph g, int s) {
        //用于恢复s到t的路径
        int prev[]       = new int[g.vertexNum];
        //discovered[i] 记录dfs第一次访问节点i时计数器增加后的值；
        int discovered[] = new int[g.vertexNum];
        //finished[i] 记录完成在定点i上的dfs搜索时计数器增加后的值；
        int finished[]   = new int[g.vertexNum];
        //记录定点颜色
        COLOR color[]    = new COLOR[g.vertexNum];
        // 记录边的类型 Tree，Forawrd，Backward，Cross
        ArrayList<EdgeLable> edgeLables = new ArrayList<>();
        //计数器
        Counter counter = new Counter(0);
        Arrays.fill(prev, -1);
        Arrays.fill(discovered, -1);
        Arrays.fill(finished, -1);
        Arrays.fill(color, COLOR.White);
        dfsVisit(g, s, prev, discovered, finished, color, counter, edgeLables);
        for(int u = 0; u < g.vertexNum; u++) {
            if(color[u] == COLOR.White) {
               dfsVisit(g, u, prev, discovered, finished, color, counter, edgeLables);
            }
        }
    }
    public static void dfsVisit(final Graph g, int u, int[] prev, int[] d, int[] f,
                                COLOR[] color ,Counter counter, ArrayList<EdgeLable> edgelabels) {
        color[u] =  COLOR.Gray;
        counter.autoInc();
        d[u] = counter.getCounter();

        for(int v : g.edges[u]) {
            //确定边类型
            EdgeType edgeType = EdgeType.Cross;
            if(color[v] == COLOR.White) {
                edgeType = EdgeType.Tree;
            } else if(color[v] == COLOR.Gray) {
                edgeType = EdgeType.Backward;
            } else if(d[u] < d[v]) {
                edgeType = EdgeType.Forward;
            }
            edgelabels.add(new EdgeLable(u, v, edgeType));
            //回溯
            if (color[v] == COLOR.White) {
                prev[v] = u;
                dfsVisit(g, v, prev, d, f, color, counter, edgelabels);
            }
        }

        color[u] = COLOR.Black;
        counter.autoInc();
        f[u] = counter.getCounter();
    }

    /**************************************************************************************************/

    public static void breadthFirstSearch(Graph g, int s) {
        //dist[i] 表示顶点s到顶点i最短路径的边数
        int[] dist           = new int[g.vertexNum];
        //pre[i] 表示BFS中i的前驱节点，若图是非连通的，则s不可达的所有点w有pre[w]=-1
        int[] pre            = new int[g.vertexNum];
        //记录图中顶点的状态
        COLOR[] colors       = new COLOR[g.vertexNum];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(pre, -1);
        Arrays.fill(colors, COLOR.White);

        dist[s] = 0;
        colors[s] = COLOR.Gray;
        queue.offer(s);

        while(!queue.isEmpty()) {
            int u = queue.poll();
            for(int v : g.edges[u]) {
                if(colors[v] == COLOR.White) {
                    dist[v] = dist[u] + 1;
                    pre[v] = u;
                    colors[v] = COLOR.Gray;
                    queue.offer(v);
                }
            }
            colors[u] = COLOR.Black;
        }
    }

    /**************************************************************************************************/

    static class WeightedGraph {
        LinkedList<Edge>[] edges;
        int vertexNum;
        int edgeNum;
        int[] outdegree;
        boolean isDirected;
        class Edge implements Comparable<Edge> {
            int u, v;
            int weight;
            public Edge(int u, int v, int weight) {
                this.u = u;
                this.v = v;
                this.weight = weight;
            }
            @Override
            public int compareTo(Edge o) {
                if(this.weight < o.weight) return -1;
                else if(this.weight > o.weight) return 1;
                return 0;
            }
        }
    }
    /** 假设：1. 边的权值为正数； 2. 不会有算术溢出
     *   采用二叉堆的时间复杂度为O((V+E)*logV)，适用于稀疏图
     *   时间复杂度：O( V*V + E )  适用于稠密图
     *             循环执行n次，每次都找查找最短距离顶点V*V,整个外循环会遍历所有边E。
     *   缺点：不能用于含有负权值的图中；
     */
    public static void singleSourceShortest(WeightedGraph g, int s,  //输入
                                            int pre[], int[] dist    //输出
                                            ) {
        boolean[] treeSet = new boolean[g.vertexNum];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(pre, -1);
        Arrays.fill(treeSet, false);

        dist[s] = 0;
        //寻找S到所有未访问顶点的最短距离；计算所有可能的新路径，如果u为-1，退出。
        while(true) {
            int u = -1;
            int sd = Integer.MAX_VALUE;
            for(int i = 0; i < g.vertexNum; i++) {
                if(!treeSet[i] && dist[i] < sd) {
                    sd = dist[i];
                    u = i;
                }
            }
            if(u == -1) break;
            treeSet[u] = true;
            for(WeightedGraph.Edge edge : g.edges[u]) {
                if(!treeSet[edge.v] && dist[u] + edge.weight < dist[edge.v]) {
                    dist[edge.v] = dist[u] + edge.weight;
                    pre[edge.v] = u;
                }
            }
        }
    }

    /**
     * 适用于含有负权值的图中，但不能含有负权环。时间复杂度为O(V*E)
     */
    public static void bellmanFord(WeightedGraph g, int s, int[] pre, int[] dist) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(pre, -1);
        dist[s] = 0;

        /**
         * n-1次之后，我们能够保证s到其他所有顶点的最短距离已经计算出来。
         * 在第n次计算时，如果有值改变，那么说明有负环；若没有负环就会很早退出；
         */
        for(int i = 1; i <= g.vertexNum; i++) {
            boolean failOnUpdate = (i == g.vertexNum);
            boolean leaveEarly = true;

            /**
             * 处理每个顶点u，稽查某条边(u.v)是否能构建一条s->u->v的路径
             * 这条路径的距离小于已知的s->v最短距离，使用长整型避免溢出；
             */
            for(int u = 0; u < g.vertexNum; u++) {
                for(WeightedGraph.Edge edge : g.edges[u]) {
                    if(dist[u] + edge.weight < dist[edge.v]) {
                        if(failOnUpdate) throw new IllegalArgumentException("图中有负权环");
                        dist[edge.v] = dist[u] + edge.weight;
                        pre[edge.v]  = u;
                        leaveEarly = false;
                    }
                }
            }
            if(leaveEarly) break;
        }
    }


    /**
     * 假设：边的权值必须为正值
     */
    public static void allPairsShortest(WeightedGraph g, int[][] dist, int[][] pred) {
        /** 输出为：dist， prev
         * 将对角线上的dist[][]设置为0，如果没有边设置为INFINITY, dist[u][v]的值为边(u, v)的权值；
         * pre[u][v]存放的是从u到v的最短路径中的u->k1->k2->...kn->v中距离v最近的一个顶点kn
         */
        for(int u = 0; u < g.vertexNum; u++) {
            Arrays.fill(dist[u], Integer.MAX_VALUE);
            Arrays.fill(pred[u], -1);
            dist[u][u] = 0;
            for(WeightedGraph.Edge edge : g.edges[u]) {
                dist[u][edge.v] = edge.weight;
                pred[u][edge.v]  = u;
            }
        }
        for(int k = 0; k < g.vertexNum; k++) {
            for(int i = 0; i < g.vertexNum; i++) {
                if(dist[i][k] == Integer.MAX_VALUE) continue;
                /**
                 *找到一条能够减少距离的路径，更新dist[][]
                 * 使用长整型避免溢出
                 */
                for(int j = 0; j < g.vertexNum; j++) {
                    if(dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        pred[i][j]  = pred[k][j];
                    }
                }
            }
        }
    }

    /**
     *输出从s到t的最短路径，s，t必须是有效的顶点描述；若s和t不连通返回空路径
     * pre[u][v]存放的是从u到v的最短路径中的u->k1->k2->...kn->v中距离v最近的一个顶点kn
     * @param path 输出
     */
    public static void constructShortestPath(int s, int t, int[][] pred, LinkedList<Integer> path) {
        path.clear();
        if(t < 0 || t >= pred.length || s < 0 || s >= pred.length) return;
        //不断扩张路径直到到达源点s或者得到表示没有路径的-1
        path.addFirst(t);
        while(t != s) {
            t = pred[s][t];
            if(t == -1) {path.clear(); return;}
            path.addFirst(t);
        }
    }

    /**
     * 最小生成树MST算法: Prime  贪心 数组 有权图 优先队列
     * 基于二叉堆： 适用于稀疏图，顶点插入优先队列开销O(V*logV);更新操作开销O(logV)，最多执行2*E次;
     *             中的时间开销O((V+E)*logV)
     * 基于斐波那契堆：适用于稠密图 O(E + V*logV)
     * 基于数组实现：O( V*V + E )  适用于稠密图
     *             循环执行V次，每次都找查找最短距离顶点V*V,整个外循环会遍历所有边E。
     */
    public static void prime(WeightedGraph g, int s, int[] pred) {
        boolean[] inTreeSet = new boolean[g.vertexNum];
        int[] dist = new int[g.vertexNum];
        Arrays.fill(pred, -1);
        Arrays.fill(inTreeSet, false);
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[s] = 0;
        while(true) {
            int u = -1;
            int sd = Integer.MAX_VALUE;
            for(int v = 0; v < g.vertexNum; v++) {
                if(!inTreeSet[v] && dist[v] < sd) {
                    sd = dist[v];
                    u = v;
                }
            }
            if(u == -1) break;
            inTreeSet[u] = true;
            for(WeightedGraph.Edge edge : g.edges[u]) {
                if(!inTreeSet[edge.v] && edge.weight < dist[edge.v]) {
                    dist[edge.v] = edge.weight;
                    pred[edge.v] = u;
                }
            }
        }
    }


    /**
     * 每一个顶点都是一个集合，每一次合并权值最小且满足所有合并的集合不构成环。
     * 基于并查集：O(E*logE)
     *
     */
    public static void kruskal(WeightedGraph g, int pred) {
        UnionSet unionSet = new UnionSet(g.vertexNum);
        LinkedList<WeightedGraph.Edge> edges = sortedEdges(g);
        for(WeightedGraph.Edge edge : edges) {
            if(!unionSet.sameComponent(edge.u, edge.v)) {
                unionSet.union(edge.u, edge.v);
            }
        }
    }
    private static LinkedList<WeightedGraph.Edge> sortedEdges(WeightedGraph g) {
        LinkedList<WeightedGraph.Edge> edges = new LinkedList<>();
        for(int i = 0; i < g.vertexNum; i++) {
            for (WeightedGraph.Edge edge : g.edges[i])
                edges.add(edge);
        }
        Collections.sort(edges);
        return edges;
    }

    static class UnionSet {
        int[] p;    //parent element
        int[] size; //number of elements in subtree i
        int n;        //number of elements in set
         public UnionSet(int n) {
             p = new int[n];
             size = new int[n];
             this.n = n;
             for(int i = 0; i < n; i++) {
                 p[i] = i;
                 size[i] = 1;
             }
         }
         public  int find(int u) {
            if(p[u] == u) return u;
            else return find(p[u]);
         }
        public  void union(int u, int v) {
            int r1 = find(u);
            int r2 = find(v);

            if(r1 == r2) return; //same component
            if(size[r1] >= size[r2]) {
                size[r1] = size[r1] + size[r2];
                p[r2] = r1;
            } else {
                size[r2] = size[r1] + size[r2];
                p[r1] = r2;
            }
        }
        boolean sameComponent(int u, int v) {
             return find(u) == find(v);
        }
    }

}
