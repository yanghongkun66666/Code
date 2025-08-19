package AWeiDianDong.LRU.LRU;

public class Node {
    int key, value;
    Node prev, next;

    Node() {
    }

    Node(int k, int v) {
        key = k;
        value = v;
    }
}
