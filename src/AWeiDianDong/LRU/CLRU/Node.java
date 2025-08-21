package AWeiDianDong.LRU.CLRU;

public class Node {
    final String key;
    Object value;

    Node prev, next;
    public Node(String k, Object v) {
        key = k;
        value = v;
    }
}
