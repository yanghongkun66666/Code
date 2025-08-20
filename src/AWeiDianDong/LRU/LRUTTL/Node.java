package AWeiDianDong.LRU.LRUTTL;

public class Node {
    int key, value;
    Node prev, next;
    long tsNanos; //最近一次访问写入时间戳 纳秒
    Node() {}
    Node(int k, int v, long ts) {key = k; value = v; tsNanos = ts;}
}
