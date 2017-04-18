package gww.geeks.hash;

import java.util.*;

/**
 * Created by 高文文 on 2017/4/18.
 * 比如说有Hash环上有A、B、C三个服务器节点，分别有100个请求会被路由到相应服务器上。现在在A与B之间增加了一个节点D，
 * 这导致了原来会路由到B上的部分节点被路由到了D上，这样A、C上被路由到的请求明显多于B、D上的，原来三个服务器节点上
 * 均衡的负载被打破了。某种程度上来说，这失去了负载均衡的意义，因为负载均衡的目的本身就是为了使得目标服务器均分所有的请求.
 *
 * 原理：
 *   将一个物理节点拆分为多个虚拟节点，并且同一个物理节点的虚拟节点尽量均匀分布在Hash环上。
 *   采取这样的方式，就可以有效地解决增加或减少节点时候的负载不均衡的问题。
 *物理节点与虚拟节点的关系
 *   物理服务器很少，需要更大的虚拟节点；反之物理服务器比较多，虚拟节点就可以少一些。
 *   比如有10台物理服务器，那么差不多需要为每台服务器增加100~200个虚拟节点才可以达到真正的负载均衡。
 *
 * 1、一个真实结点如何对应成为多个虚拟节点？
 * 2、虚拟节点找到后如何还原为真实结点？
 * 简单实现：
 *   给每个真实结点后面根据虚拟节点加上后缀再取Hash值，比如"192.168.0.0:111"就把它变成"192.168.0.0:111&&VN0"
 *   到"192.168.0.0:111&&VN4"，VN就是Virtual Node的缩写，还原的时候只需要从头截取字符串到"&&"的位置就可以了。
 */
public class ConsistentHashWithVirtualNode<T> {

    //真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
    private final List<T> realNodes ;
    //虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
    private final SortedMap<Integer, T> virtualNodes = new TreeMap<Integer, T>();
    //虚拟节点的数目，一个真实结点对应5个虚拟节点
    private final int VIRTUAL_NODES = 5;
    private final HashFunction hashFunction;
    private final int numberOfReplicas;

    public ConsistentHashWithVirtualNode(HashFunction hashFunction, int numberOfReplicas, List<T> realNodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        this.realNodes = realNodes;
        for(T node : realNodes)
            add(node);
    }

    public void add(T node) {
        for(int i = 0; i < numberOfReplicas; i++) {
            for(int j = 0; j < VIRTUAL_NODES; j++) {
                String virtualNodeName = node.toString() + "&&VN" + String.valueOf(j);
                int hash = hashFunction.hash(virtualNodeName);
                System.out.println(virtualNodeName + "  [" + node + "]加入集合中, 其Hash值为" + hash);
                virtualNodes.put(hash, (T)virtualNodeName);
            }
        }
    }

    public void remove(T node) {
        for(int i = 0; i < numberOfReplicas; i++) {
            for(int j = 0; j < VIRTUAL_NODES; j++) {
                String virtualNodeName = node.toString() + "&&VN" + String.valueOf(j);
                int hash = hashFunction.hash(virtualNodeName);
                System.out.println(virtualNodeName + "  [" + node + "]移除集合中, 其Hash值为" + hash);
                virtualNodes.remove(hash);
            }
        }
    }

    public T get(Object key) {
        if(virtualNodes.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if(!virtualNodes.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = virtualNodes.tailMap(hash);
            hash = tailMap.isEmpty() ? virtualNodes.firstKey() : tailMap.firstKey();
        }
        T virtualNodeName = virtualNodes.get(hash);

        return (T)((String)virtualNodeName).substring(0, ((String)virtualNodeName).indexOf("&&"));
    }


    public static void main(String[] arrgs) {
        String[] serverStr = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111"
                ,"192.168.0.3:111", "192.168.0.4:111"};
        List<String> servers = new LinkedList<>();
        for(String server : serverStr) servers.add(server);
        HashFunction hashFunction = new HashFunction() {};
        ConsistentHashWithVirtualNode<String> consistentHashWithVirtualNode = new
                ConsistentHashWithVirtualNode<>(hashFunction, 2, servers);
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    hashFunction.hash(nodes[i]) + ", 被路由到结点[" + consistentHashWithVirtualNode.get(nodes[i]) + "]");

        consistentHashWithVirtualNode.remove("192.168.0.0:111");

        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    hashFunction.hash(nodes[i]) + ", 被路由到结点[" + consistentHashWithVirtualNode.get(nodes[i]) + "]");
    }
}
