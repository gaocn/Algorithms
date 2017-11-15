package gww.geeks.networkflow;

import java.net.Inet4Address;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * 时间复杂度为：O(max_flow * E)=O(V*E^2),最差情况下是每一次增广路径都只增加了1单位的流量
 *
 * Residual Graph（残留图）：表示一个网络流图中的可用流量， r[i][j] = c[i][j] - f[i][j]
 *
 * 残留网络流图中若存在一条从S到T的路径，那么这条路径就是增广路径（可以增加流量的路径）
 *
 *                           [ 0                  若弧(i,j)不存在
 * residualCapacity[i][j] =  [
 *                           [c[i][j] - f[i][j]  若初始流量f[i][j]为0，则弧(i,j)的残留流量为容量
 *
 *
 * Edmods-Karp算法采用BFS，保证每一次的增广路径都是从边数最少开始，此时算法的最差时间复杂度为O(V*E^2)
 * 下面实现采用邻接矩阵，BFS花费时间为O(V^2)，总的时间复杂度为：O(EV^3)
 *
 */

class  Graph {
    int V, E;
    int[][] edges;  // false if there is no edge between (i,j)
    int[][] flows;

    public Graph(int v, int e, int[][] edges, int[][] flows) {
        V = v;
        E = e;
        this.edges = edges;
        this.flows = flows;
    }
}

public class FordFulkerson {


    // return max flow from s to t
    public int fordFulkerson(Graph g, int s, int t) {

        // residual graph
        int[][] residualGraph = new int[g.V][g.V];
        // initialize this residual graph
        for (int u = 0; u < g.V; u++) {
            for (int v = 0; v < g.V; v++) {
                if(g.edges[u][v] > 0) {
                    residualGraph[u][v] = g.edges[u][v] - g.flows[u][v];
                } else {
                    residualGraph[u][v] = 0;
                }
            }
        }

        // to store path in BFS
        int[] parent = new int[g.V];
        // initialize max flow
        int maxFlow = 0;

        while(bfs(g, residualGraph, s, t, parent)) {

            // 1. 计算可增加流量
            int d = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                d = Math.min(d, residualGraph[parent[v]][v]);
            }

            // 2. 更新流量
            for(int v = t; v != s; v = parent[v]) {
                residualGraph[parent[v]][v] -= d;
                residualGraph[v][parent[v]] += d;
            }

            //增加流
            maxFlow += d;
        }
        return maxFlow;
    }

    public boolean bfs(Graph g, int[][] residualGraph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[g.V];
        Arrays.fill(visited, false);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for(int v = 0; v < g.V; v++) {
                if(residualGraph[u][v] > 0 && !visited[v]) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;

                }
            }
        }
        return visited[t];
    }

    public static void main(String[] args) {
        // 0表示对应的边不存在
        int capacity[][] =new int[][] {
                {0, 16, 13, 0, 0, 0},
                {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0},
                {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4},
                {0, 0, 0, 0, 0, 0}
        };

        int[][] flow = new int[][] {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}

        };

        Graph g = new Graph(6, 36, capacity, flow);
        FordFulkerson alg = new FordFulkerson();
        System.out.println("The maximum possible flow is: " + alg.fordFulkerson(g, 0, 5));

    }

}
