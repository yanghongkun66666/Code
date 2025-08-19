package AWeiDianDong.LRU.LRU;

import java.util.HashMap;
import java.util.Map;

public class LRU {
    private final int capacity;

    private final Map<Integer, Node> keyToNode;
    private final Node dummy; //哨兵：dummy.prev为尾,dummy.next为头

    public LRU(int capacity) {
        this.capacity = capacity;
        keyToNode = new HashMap<>();
        dummy = new Node();
        dummy.next = dummy;
        dummy.prev = dummy;
    }

    //从双向链表中移出节点node，node一定存在在链表中
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    //把节点node插入到表头（dummy之后）
    private void pushFront(Node node) {
        node.prev = dummy;
        node.next = dummy.next;
        dummy.next.prev = node;
        dummy.next = node;
    }

    //若存在key，就移动到链表头部并返回节点；否则返回null
    private Node getNode(int key) {
        Node node = keyToNode.get(key);
        if (node == null) return null;
        remove(node);
        pushFront(node);
        return node;
    }

    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.value;
    }

    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }

        node = new Node(key, value);
        keyToNode.put(key, node);
        pushFront(node);

        if (keyToNode.size() > capacity) {
            Node to_del = dummy.prev; //最近最少使用的是尾部（dummy.prev）
            remove(to_del);
            keyToNode.remove(to_del.key);
        }
    }
}
