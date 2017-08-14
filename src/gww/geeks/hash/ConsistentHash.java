package gww.geeks.hash;

import java.util.*;

/**
 * Created by 高文文 on 2017/4/18.
 *
 *
 * https://github.com/google/guava/tree/master/guava/src/com/google/common/hash
 * Guava is solving a (simpler) problem when the input is hashed into [1..N], and the hash should be consistent for [1..N+1]
 *
 *
 *  The problem solved here is hashing the input into a set of N arbitrary elements, and the hash should be consistent
 *  for the addition of one more arbitrary element into that set.
 *
 *
 * http://www.blogjava.net/hello-yun/archive/2012/10/10/389289.html
 * http://www.cnblogs.com/xrq730/p/5186728.html
 *
 * 一致性Hash算法:
 *      可以解决很多分布式环境下不好的路由算法导致系统伸缩性差的问题，
 *      但是会带来另外一个问题：负载不均。==> 虚拟节点的一致性哈希算法
 */
public class ConsistentHash<T> {
    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        for(T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for(int i = 0; i < numberOfReplicas; i++) {
            int hash = hashFunction.hash(node.toString() + i);
            System.out.println("[" + node + "]加入集合中, 其Hash值为" + hash);
            circle.put(hash, node);
        }
    }

    public void remove(T node) {
        for(int i =0 ; i < numberOfReplicas; i++) {
            int hash = hashFunction.hash(node.toString() + i);
            System.out.println("[" + node + "]移出集合中, 其Hash值为" + hash);
            circle.remove(hash);
        }
    }

    /**
     * To find a node for an object (the get method), the hash value of the object is used to look in the map.
     * Most of the time there will not be a node stored at this hash value (since the hash value space is
     * typically much larger than the number of nodes, even with replicas), so the next node is found by
     * looking for the first key in the tail map. If the tail map is empty then we wrap around the circle
     * by getting the first key in the circle
     */
    public T get(Object key) {
        if(circle.isEmpty()) {
            return null;
        }
        // 得到带路由的结点的Hash值
        int hash = hashFunction.hash(key);
        if(!circle.containsKey(hash)) {
            // 得到大于该Hash值的所有Map
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            // 第一个Key就是顺时针过去离node最近的那个结点
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String[] args) {
        String[] serverStr = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111"
                ,"192.168.0.3:111", "192.168.0.4:111"};
        List<String> servers = new ArrayList<>();
        for(String server : serverStr) servers.add(server);
        HashFunction hashFunction = new HashFunction() {};
        ConsistentHash<String> consistentHash = new ConsistentHash<>(
                hashFunction,2, servers);
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
         for (int i = 0; i < nodes.length; i++)
             System.out.println("[" + nodes[i] + "]的hash值为" +
                hashFunction.hash(nodes[i]) + ", 被路由到结点[" + consistentHash.get(nodes[i]) + "]");

         consistentHash.remove("192.168.0.0:111");

        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    hashFunction.hash(nodes[i]) + ", 被路由到结点[" + consistentHash.get(nodes[i]) + "]");
    }

}

interface HashFunction {
    /**
     *  为什么不适用string.hash？1. 返回负数； 2. 返回hash值不均匀导致单个集群节点负载过大。
     *  常用hash算法：CRC32_HASH、FNV1_32_HASH、KETAMA_HASH等，
     *  其中KETAMA_HASH是默认的MemCache推荐的一致性Hash算法
     *  下面实现FNV1_32_HASH
     */
    default int hash(Object obj) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        String str = obj.toString();
        int strLen = str.length();
        for(int i = 0; i < strLen; i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        hash = hash < 0 ? Math.abs(hash) : hash;
        return hash;
    }

    /**
     * hash function有两类：
     *   1. to disperse data points uniformly into n bits.
     *   2. to securely identify the input data.
     *   FNV SHA-1 or AES
     *   http://isthe.com/chongo/tech/comp/fnv/
     *   http://www.larc.usp.br/~pbarreto/hflounge.html
     */
}
