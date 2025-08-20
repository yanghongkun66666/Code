package AWeiDianDong.LRU.LRUTTL;

import java.util.HashMap;
import java.util.Map;

public class LRUTTL {
    private final int capacity;
    private final long ttlNanos; //TTL纳秒

    private final Map<Integer, Node> keyToNode;

    private final Node dummy; //哨兵，dummy.next 为头， dummy.prev为尾

    public LRUTTL(int capacity, int ttlSeconds) {
        this.capacity = capacity;
        this.ttlNanos = ttlSeconds <= 0 ? 0 : (long) ttlSeconds * 1_000_000_000L;
        keyToNode = new HashMap<>();
        dummy = new Node();

        dummy.next = dummy;
        dummy.prev = dummy;
    }

    // ========== 内部工具方法 ==========
    private boolean isExpired(Node node) {
        if (ttlNanos <= 0L) return false; //ttl <= 0视为永不过期
        long now = System.nanoTime();
        return (now - node.tsNanos) > ttlNanos; //如果一个节点在ttlNanos段时间内都没有被访问过了，那么就是过期了
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void pushFront(Node node) {
        node.prev = dummy;
        node.next = dummy.next;

        dummy.next.prev = node;
        dummy.next = node;
    }

    // 找到节点：如存在且未过期 → 提升到头部并返回；过期或不存在 → 返回 null
    private Node getNode(int key) {
        Node node = keyToNode.get(key);
        if (node == null) {
            return null;
        }

        if (isExpired(node) == true) {
            //过期了，从链表和map移除
            remove(node);
            keyToNode.remove(key); //这个key就是node.key
            return null;
        }

        //没过期，移动到头部，刷新时间戳
        remove(node);
        pushFront(node);
        node.tsNanos = System.nanoTime();
        return node;
    }

    // 对外 API：O(1)
    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.value;
    }


    // 对外 API：O(1)
    public void put(int key, int value) {
        Node node = getNode(key);
        long now = System.nanoTime();

        if (node != null) {
            node.value = value;
            node.tsNanos = now;
            return;
        }

        node = new Node(key, value, now);
        keyToNode.put(key, node);
        pushFront(node);

        //容量淘汰，移除尾部最近最少使用
        if (keyToNode.size() > capacity) {
            Node to_del = dummy.prev;

            remove(to_del);
            keyToNode.remove(to_del.key);
        }
    }

}