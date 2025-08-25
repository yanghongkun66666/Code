package AWeiDianDong.LFU;

import java.util.HashMap;
import java.util.Map;

public class LFU {

    private final int capacity;
    private int minFreq = 1;
    private final Map<Integer, Node> keyToNode = new HashMap<>();
    private final Map<Integer, Node> freqToList = new HashMap<>(); // freq -> dummy head

    public LFU(int capacity) {
        this.capacity = capacity;
    }

    /* 双链表操作 */
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void pushFront(int freq, Node node) {
        Node dummy = freqToList.computeIfAbsent(freq, f -> newList());
        node.prev = dummy;
        node.next = dummy.next;
        node.prev.next = node;
        node.next.prev = node;
    }

    private Node newList() {
        Node dummy = new Node();
        dummy.prev = dummy;
        dummy.next = dummy;
        return dummy;
    }

    /* 访问 key，对应结点频次 +1，并移动到新频次链表前端；返回结点或 null */
    private Node getNode(int key) {
        Node node = keyToNode.get(key);
        if (node == null) return null;

        // 从原频次链表删除
        remove(node);
        Node dummy = freqToList.get(node.freq);
        // 如果该频次链表空了，删除它；若正好是 minFreq，minFreq++
        if (dummy.prev == dummy) {
            freqToList.remove(node.freq);
            if (node.freq == minFreq) minFreq++;
        }

        // 频次 +1 并插入新频次链
        node.freq++;
        pushFront(node.freq, node);
        return node;
    }

    //对外API
    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        Node node = getNode(key); // 如果存在，会顺便把 freq+1
        if (node != null) {
            node.val = value;
            return;
        }

        // 容量满：淘汰 minFreq 链表的尾结点（最久未使用的那个）
        if (keyToNode.size() == capacity) {
            Node dummy = freqToList.get(minFreq);
            Node last = dummy.prev;
            remove(last);
            keyToNode.remove(last.key);
            if (dummy.prev == dummy) {
                freqToList.remove(minFreq);
            }
        }

        // 新结点（freq = 1）
        Node n = new Node(key, value);
        keyToNode.put(key, n);
        minFreq = 1;
        pushFront(1, n);
    }
}
