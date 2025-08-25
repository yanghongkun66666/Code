package AWeiDianDong.LFU;

import java.util.HashMap;
import java.util.Map;

public class LFU {

    private final int capacity;
    private int minFreq = 1;
    //保存当前缓存里最小的访问频次。
    //当需要淘汰时，就去 minFreq 对应链表的尾部找“最久未使用”的节点。
    private final Map<Integer, Node> keyToNode = new HashMap<>(); //key → Node（保存 val 和 freq） 用来快速找到某个 key 对应的节点
    private final Map<Integer, Node> freqToList = new HashMap<>(); // freq -> dummy head
    // freq → 一个双向链表
    //每个频次都有一个链表，链表中放该频次的所有节点。
    //为什么用链表？因为要快速做 插入/删除/淘汰最后一个节点。

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

    /**
     * 初始化一个双向循环链表的哨兵节点（dummy head）
     * 每个 freq 对应一个链表；链表用 dummy 作为头，方便操作。
     * 为什么是循环链表？
     * 因为 dummy 的 prev 指向自己、next 也指向自己，所以即使链表为空，也不会出现 null，插入/删除时逻辑统一，不需要额外判断边界情况。
     * newList() 是为了生成某个频次对应的链表的起点（dummy head）。
     * @return
     */

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

    /**
     * get(key)
     * 先通过 keyToNode 找到节点。
     * 把节点从原 freq 链表中删除。
     * 如果原链表为空，删除该链表，并更新 minFreq。
     * freq++，把节点放到新 freq 的链表前端。
     * 返回节点的值。
     * @param key
     * @return
     */
    public int get(int key) {
        Node node = getNode(key);
        return node == null ? -1 : node.val;
    }

    /**
     * put(key, value)
     * 如果 key 已存在，更新 value 并执行一次 getNode（freq++）。
     * 如果不存在：
     * 如果容量已满，去 minFreq 链表的尾部淘汰一个节点。
     * 新建一个 freq=1 的节点，插入 freq=1 链表前端。
     * minFreq 重置为 1。
     * @param key
     * @param value
     */
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
