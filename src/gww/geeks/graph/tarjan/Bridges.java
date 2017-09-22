package gww.geeks.graph.tarjan;


/*

桥：无向连通图中，如果删除某边后，图变成不连通，则称该边为桥。
    对于非无向连通图，若删除某边后，图的连通分量个数增加了，则称该边为桥。

【方法1】
    对于图中的每一条，将其删除后，检查图的连通性，若是非连通的，则该边即为一个桥。
    时间复杂度为：O(E*(V+E))

【方法2】
    在DFS树中，树中的边(u,v)是桥满足条件： 在以孩子节点v为根的子树中，不存在能够到达u或u的祖先节点的边。
    low[v] > disc[u]

 */

import java.util.Arrays;

public class Bridges {

    final static int NIL = -1;
    final static int INF = Integer.MAX_VALUE;

    static int timer = 0;

    public void bridges(Graph g) {
        int[] parent = new int[g.V];
        int[] disc   = new int[g.V];
        int[] low    = new int[g.V];
        boolean[] visited = new boolean[g.V]; // can be replaced by disc?

        Arrays.fill(parent, NIL);
        Arrays.fill(disc, NIL);
        Arrays.fill(low, NIL);
        Arrays.fill(visited, false);

        timer = 0;
        for(int u = 0; u < g.V; u++) {
            if(disc[u] == -1) {
                dfs(g, u, parent, disc, low);
            }
        }

    }

    public void dfs(Graph g, int u, int[] parent, int[] disc, int[] low) {

        // visited[u] = true
        // initialize discovery and low value
        low[u] = disc[u] = ++timer;

        for(int v = 0; v < g.V && g.edges[u][v] != INF; v++) {
            if(disc[v] == -1) {
                parent[v] = u;
                dfs(g, v, parent, disc, low);

                //check if the subtree rooted with v has a connection to one of the ancestors of u
                low[u] = Math.min(low[u], low[v]);

                // edge (u, v) is bridge iif the lowest vertex reachable from subtree under v
                // is below u in DFS tree
                if(low[v] > disc[u])
                    System.out.println("Bridge: " + u + "---" + v);
            } else if(v != parent[u]) {
                //update low value of u for parent function calls
                low[u] = Math.min(low[u], disc[v]);
            }
        }

    }

}
