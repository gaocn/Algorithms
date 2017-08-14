package nutshell.chapter8;

import java.util.*;

/**
 * Created by 高文文 on 2017/4/17.
 */
public class NetworkFlowProblem {
    public static void main(String[] args) {

    }



}

class VertexInfo {
    /** 增广路径中的前部节点 */
    final int previous;
    /** 节点是否有一个前向边或后向边 */
    final boolean forward;
    public VertexInfo(int previous, boolean forward) {
        this.previous = previous;
        this.forward = forward;
    }
}

class EdgeInfo {
    final int start;
    final int end;
    final int capacity;
    final int cost;
    int flow;
    public EdgeInfo(int start, int end, int capacity, int cost, int flow) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = flow;
    }
    public int getFlow() {
        return flow;
    }
    public void setFlow(int flow) {
        this.flow = flow;
    }
}

class VertxrStructure {
    List<EdgeInfo> forward;
    List<EdgeInfo> backward;
    public VertxrStructure() {
        forward = new LinkedList<>();
        backward = new LinkedList<>();
    }
    public VertxrStructure(List<EdgeInfo> forward, List<EdgeInfo> backward) {
        this.forward = forward;
        this.backward = backward;
    }
    public void addForward(EdgeInfo ei) {
        forward.add(ei);
    }
    public void addBackward(EdgeInfo ei) {
        backward.add(ei);
    }
}

abstract class FlowNetWork<E> {
    final int sourceIndex;
    final int sinkIndex;
    final int numVertex;
    final VertexInfo[] vertices;

    public FlowNetWork(int sourceIndex, int sinkIndex, int numVertex, VertexInfo[] vertices) {
        this.sourceIndex = sourceIndex;
        this.sinkIndex = sinkIndex;
        this.numVertex = numVertex;
        this.vertices = vertices;
    }

    public abstract E getEdgeStructure();
    public abstract EdgeInfo get(int start, int end);
    public abstract int getFlow();
    public abstract int getCost();
}

class FlowNetworkArray extends FlowNetWork<EdgeInfo[][]>{
    EdgeInfo[][] info;
    public FlowNetworkArray(int sourceIndex, int sinkIndex, int numVertex, VertexInfo[] vertices, EdgeInfo[][] info) {
        super(sourceIndex, sinkIndex, numVertex, vertices);
        this.info = info;
    }
    @Override
    public EdgeInfo[][] getEdgeStructure() {
        return info;
    }
    @Override
    public EdgeInfo get(int start, int end) {
        return info[start][end];
    }
    @Override
    public int getFlow() {
        return 0;
    }
    @Override
    public int getCost() {
        return 0;
    }
}

class FlowNetworkAdjacentList extends FlowNetWork<VertxrStructure[]> {
    VertxrStructure[] info;
    public FlowNetworkAdjacentList(int sourceIndex, int sinkIndex, int numVertex, VertexInfo[] vertices, VertxrStructure[] info) {
        super(sourceIndex, sinkIndex, numVertex, vertices);
        this.info = info;
    }
    @Override
    public VertxrStructure[] getEdgeStructure() {
        return info;
    }
    @Override
    public EdgeInfo get(int start, int end) {
        return null;
    }
    @Override
    public int getFlow() {
        return 0;
    }
    @Override
    public int getCost() {
        return 0;
    }
}

class OptimizedFlowNetWork {
    final int sourceIndex;
    final int sinkIndex;
    final int numVertex;

    int[][] capacity;
    int[][] flow;
    int[] previous; //路节点的前驱信息
    int[] visited;  //记录节点是否被访问过

    Queue<Integer> queue;

    public OptimizedFlowNetWork(int sourceIndex, int sinkIndex, int numVertex, Iterator<EdgeInfo> edges) {
        this.sourceIndex = sourceIndex;
        this.sinkIndex = sinkIndex;
        this.numVertex = numVertex;

        queue = new LinkedList<>();
        capacity = new int[numVertex][numVertex];
        flow = new int[numVertex][numVertex];
        previous = new int[numVertex];
        visited = new int[numVertex];
        //初始化
        while(edges.hasNext()) {
            EdgeInfo ei = edges.next();
            capacity[ei.start][ei.end] = ei.capacity;
            flow[ei.start][ei.end] = ei.flow;
        }
    }

    /**计算并返回最大流
     *
     */
    public int compute(int source, int sink) {
        int maxFlow = 0;
        while(search(source, sink)) {
            maxFlow += processPath(source, sink);
        }
        return maxFlow;
    }
    /** 采用BFS寻找源点到汇点的增广路径，
     * 从最短路径再找最长路径
     *
     */
    public boolean search(int source, int sink) {
        Arrays.fill(previous, -1);
        /** 0=未访问 1=队列中 2=已访问 */
        Arrays.fill(visited, 0);
        queue.offer(source);
        visited[source] = 1;

    loop: while(!queue.isEmpty()) {
            int  u = queue.poll();
            visited[u] = 2;
            //u的邻接顶点中，仍然有剩余流量的顶点加入队列中
            for(int v= 0; v < numVertex; v++) {
                if(visited[v] == 0 && capacity[u][v] > flow[u][v]) {
                    queue.offer(v);
                    visited[v] = 1;
                    previous[v] = u;
                    if(v == sink) break loop;
                }
            }
        }
        //sink节点访问过了？
        return visited[sink] != 0;
    }

    /**
     * 1. 计算要增加的流量
     *        || min{inc, capacity[i][j] - flow[i][j]} (i, j)为正向边
     *  inc = ||
     *        || min{inc, flow[i][j]}  (i,j)为反向边  && f[i][j] > 0
     * 2. 沿着增广路径更新流量
     *    flow[i][j] += inc   (i,j)为正向边
     *    flow[i][j] -= inc   (i,j)为反向边
     *
     **/
    public int processPath(int source, int sink) {
        //1. 计算要增加的流量
        int increment = Integer.MAX_VALUE;
        int v = sink;
        while (previous[v] != -1) {
            int unit = capacity[previous[v]][v] - flow[previous[v]][v];
            increment = Math.min(unit, increment);
            v = previous[v];
        }
        System.out.print("increment: " + increment);

        //2. 沿着增广路径更新流量
        v = sink;
        while(-1 != previous[v]) {
            flow[previous[v]][v] += increment;
            flow[v][previous[v]] -= increment;
            v = previous[v];
        }
        return increment;
    }


}