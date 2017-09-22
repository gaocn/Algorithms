package gww.geeks.graph.tarjan;


/*
【概念】 割点 连通度
在无向连通图中，删除一个顶点v及其相连的边后，原图从一个连通分量变成了两个或多个连通分量，则称顶点v为割点，
 同时也称关节点(Articulation Point)。一个没有关节点的连通图称为重连通图(biconnected graph)。若在连通
 图上至少删去k 个顶点才能破坏图的连通性，则称此图的连通度为k。

 割点： 无向连通图中，如果删除某点后，图变成不连通，则称该点为割点。
 桥：存在于无向图中的这样的一条边，如果去掉这一条边，那么整张无向图会分为两部分，这样的一条边称为桥无向连通
     图中，如果删除某边后，图变成不连通，则称该边为桥。

 强连通图：任意两个点都通过一定路径互相连通
 强连通分量（SCC）：有向图中最大强连通子图

【tarjan算法原理】
    基于DFS，每个强连通分量是DFS搜索树的一颗子树，若能够找到该子树的head，就能够打印出该子树对应的SCC。
 搜索时，把当前搜索树中未处理的节点加入一个堆栈，回溯时可以判断栈顶到栈中的节点是否为一个强连通分量。

    前后连通分量所在子树的head满足条件：disc[u] = low[u]

    Tarjan算法中为每个节点i维护以下变量：
    1). disc[i] 深度优先搜索时节点i被搜索的次序；
    2). low[i]  节点i能够回溯到的最早位于栈中的节点的次序(topmost reachable ancestor)
    3). inStack[i] 标记节点i是否在栈中

low[u]更新步骤：
    1. 从任意一个节点u开始，low[u] = disc[u]
    2. 采用DFS搜索对u的每一个孩子节点v进行处理同时需要更新low[u]的值，分以下两种情况
       case 1(Tree edge):
          若节点v尚未被访问，则当v通过DFS遍历后，更新low[u] = min(low[u], low[v])
       case 2(Back edge):
          若节点v已经被访问过，则更新low[u] = min(low[u], disc[v])

http://www.geeksforgeeks.org/tarjan-algorithm-find-strongly-connected-components/

http://www.geeksforgeeks.org/bridge-in-a-graph/

   一、强连通分量

   三、桥

【应用】
关节点和重连通图在实际中较多应用。显然，一个表示通信网络的图的连通度越高，其系统越可靠，无论是哪一个站点
 出现故障或遭到外界破坏，都不影响系统的正常工作；又如，一个航空网若是重连通的，则当某条航线因天气等某种原
 因关闭时，旅客仍可从别的航线绕道而行；再如，若将大规模的集成电路的关键线路设计成重连通的话，则在某些元件
 失效的情况下，整个片子的功能不受影响，反之，在战争中，若要摧毁敌方的运输线，仅需破坏其运输网中的关节点即可。

 */

import java.util.Arrays;
import java.util.Stack;

class Graph {
    int V,E;
    int[][] edges;

    public Graph(int v, int e, int[][] edges) {
        V = v;
        E = e;
        this.edges = edges;
    }
}

public class Tarjan {


    final static int NIL = -1;
    final static int INF = Integer.MAX_VALUE;
    static int timer = 0;
    public  void tarjanSCC(Graph g) {
        Stack<Integer> stack = new Stack<>();
        int disc[] = new int[g.V];
        int low[] = new int[g.V];
        boolean inStack[] = new boolean[g.V];

        //initial
        Arrays.fill(disc, NIL);
        Arrays.fill(low, NIL);
        Arrays.fill(inStack, false);

        timer = 0;
        for (int u = 0; u < g.V; u++) {
            if(disc[u] != -1)
                dfs(g, u, stack, disc, low, inStack);
        }
    }

    public void dfs(Graph g, int u, Stack<Integer> stack, int[] disc, int[] low, boolean[] inStack) {

        low[u] = disc[u] = ++timer;
        stack.push(u);
        inStack[u] = true;

        for (int v = 0; v < g.V && g.edges[u][v] != INF; v++) {
            if(disc[v] == NIL) {
                dfs(g, v, stack, disc, low, inStack);

                //tree edge, check if subtree rooted with "v" has a connection to on of
                // the ancestors of "u"
                low[u] = Math.min(low[u], low[v]);

            } else if(inStack[v]){
                //back edge(not cross edge)
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        //head node found, pop the stack and print SCC
        int w = 0; // to store stack extracted vertices
        if(low[u] == disc[u]) {
            while (stack.peek() != u) {
                w = stack.peek();
                System.out.print(w +" ");
                inStack[w] = false;
                stack.pop();
            }
            w = stack.peek();
            System.out.print(w +" ");
            inStack[w] = false;
            stack.pop();
        }
    }





}
