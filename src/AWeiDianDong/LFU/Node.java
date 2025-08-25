package AWeiDianDong.LFU;

public class Node {
    int key, val, freq = 1;
    Node prev, next;
    Node() {}
    Node(int k, int v) { this.key = k; this.val = v; }
}
