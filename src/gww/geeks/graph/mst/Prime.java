package gww.geeks.graph.mst;

import java.util.Arrays;

public class Prime {


    /**
     * Prime最小生成树算法     TAG【贪心，优先队列，有权图】
     *  在带权连通图中V是包含所有顶点的集合， U已经在最小生成树中的节点，从图中任意某一顶点v开始，此时集合U={v}，重复一下操作：
     * LOOP:
     *    在所有u∈U,w∈V-U的边(u,w)∈E中找到一条权值最小的边，将(u,w)这条边加入到已找到边的集合，并且将点w加入到集合U中，
     *    当U=V时，就找到了这颗最小生成树。
     *
     * 【实现】
     *    1. 基于数组的实现，适用于稠密图，算法复杂度为O(V*V + E)
     *
     *    2. 基于二叉堆： 适用于稀疏图，顶点插入优先队列开销O(V*logV);更新操作开销O(logV)，最多执行2*E次; 时间开销O((V+E)*logV)
     *    3. 基于斐波那契堆：适用于稠密图 O(E + V*logV)
     *
     */

    public static class WeightedGraph {
        int V, E;
        int[][] edges;

        public WeightedGraph(int v, int e, int[][] edges) {
            V = v;
            E = e;
            this.edges = edges;
        }
    }

    public static void prime(WeightedGraph graph, int source, int prev[]) {
        boolean[] inTreeSet = new boolean[graph.V];
        int[] dist = new int[graph.V];
        Arrays.fill(inTreeSet, false);
        Arrays.fill(dist, -1);
        Arrays.fill(prev, -1);

        dist[source] = 0;
        while(true) {
            int u = -1;
            int sd = Integer.MAX_VALUE;

                for(int i = 0; i < graph.V; i++) {
                    if(!inTreeSet[i] && dist[i] < sd) {
                        u = i;
                        sd = dist[i];
                    }
            }

            if(u == -1) return;
            inTreeSet[u] = true;

            for(int v = 0; v < graph.V && graph.edges[u][v] != Integer.MAX_VALUE; v++) {
                if(!inTreeSet[v] && graph.edges[u][v] < dist[v]) {
                    dist[v] = graph.edges[u][v];
                    prev[v] = u;
                }
            }
        }
    }


}
