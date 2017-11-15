package gww.geeks.networkflow;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Dinic's Algorithm时间复杂度比Edmond-Karp算法快，时间复杂度为O(E*V^2)
 *
 * Dinic采用和FordFulkerson同样的两个概念：
 *  1. 当且仅当在残留量图中不存在从S到T的路径时，就得到的最大流。
 *  2. 也在循环中使用BFS，但是会有点区别。
 *
 * 【Level Graph】
 *   Dinic在BFS检查是否存在可行流时，会同时创建一个level graph，在level graph中，一个顶点的level是从源点S该顶点的最短距离（边数），
 *     在创建level graph后，
 * 【Blocking Flow】
 *      一个流为阻塞流，当且仅当无法再用Level Graph发送更多流量时。
 *
 */



class Edge {
    int v; // u-v edge
    int flow;
    int capacity;
    int rev; //store index of reverse edge in adjacency list for quick accessing

    public Edge(int v, int flow, int capacity, int rev) {
        this.v = v;
        this.flow = flow;
        this.capacity = capacity;
        this.rev = rev;
    }
}


public class DinicsAlg {
    final static int INF = Integer.MAX_VALUE;
    int V;
    int[] level; // store level of a node
    LinkedList<Edge>[] edges;

    public DinicsAlg(int v) {
        V = v;
        this.level = new int[V];
        this.edges = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            edges[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v, int C) {
        Edge a = new Edge(v, 0, C, edges[v].size());
        Edge b = new Edge(u, 0, 0, edges[u].size());

        // forward edge: 0 flow, C capacity
        edges[u].add(a);
        // back edge: 0 flow, 0 capacity
        edges[v].add(b);
    }

    /**
     * BFS构建Level Graph需要时间是O(E)，sendFlow需要时间是O(VE)
     * 外循环至多循环O(V)次，因为每次循环通过Level Graph增加流量至少是1个单位，因此最多循环O(V)次
     *
     * 总时间复杂度为：O(E*V^2)
     */
    public int dinicMaxFlow(int s, int t) {

        if(s == t) return -1;

        int sumFlow = 0;
        //若存在增广路径，则沿路径增广
        while (bfs(s, t)) {
            //store how many edges are visited
            int[] start = new int[V + 1];

            while (true) {
                int flow = sendFlow(s, INF, t, start);
                if(flow == 0) break;
                sumFlow += flow;
            }
        }
        return sumFlow;
    }


    public boolean bfs(int s, int t) {
        Arrays.fill(level, -1);
        level[s] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);

        while(!queue.isEmpty()) {
            int u = queue.poll();
            for(Edge e : edges[u]) {
                if(level[e.v] == -1 && e.flow < e.capacity) {
                    level[e.v] = level[u] + 1;
                    queue.offer(e.v);
                }
            }
        }

        return level[t] == -1 ? false : true;
    }

    /**
     * 若BFS找到增广路径且构造了Level Graph后，下面基于DFS进行流量更新(send flow)。
     *
     *
     * @param u     Current Vertex
     * @param flow  Current flow send by parent function call
     * @param t     Sink Vertex
     * @param start keep track of next edge to be explored.
     *              start[i] stores count of edges explored from i.
     * @return
     */
    public int sendFlow(int u, int flow, int t, int[] start) {
        //已经到达了Sink点
        if(u == t) return flow;

        //遍历所有邻接边
        for (; start[u] < edges[u].size(); start[u]++) {
            Edge e = edges[u].get(start[u]);

            if(level[e.v] == level[u] + 1 && e.flow < e.capacity) {

                int tmpFlow = sendFlow(e.v, Math.min(flow, e.capacity - e.flow), t, start);
                if(tmpFlow > 0) {
                    e.flow += tmpFlow;
                    Edge reverseEdge = edges[e.v].get(e.rev);
                    reverseEdge.flow -= tmpFlow;
                    return tmpFlow;
                }
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        DinicsAlg g = new DinicsAlg(6);
        g.addEdge(0, 1, 16 );
        g.addEdge(0, 2, 13 );
        g.addEdge(1, 2, 10 );
        g.addEdge(1, 3, 12 );
        g.addEdge(2, 1, 4 );
        g.addEdge(2, 4, 14);
        g.addEdge(3, 2, 9 );
        g.addEdge(3, 5, 20 );
        g.addEdge(4, 3, 7 );
        g.addEdge(4, 5, 4);

        System.out.println("Maximum flow: " + g.dinicMaxFlow(0, 5));
    }

}
