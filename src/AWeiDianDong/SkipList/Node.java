package AWeiDianDong.SkipList;

public class Node {
    int value;
    int height; //实际层高 (1...SkipList.MAX_VALUE)
    Node[] next; //固定长度MAX_VALUE 未使用层为null

    Node(int v, int h) {
        value = v;
        height = h;
        next = new Node[SkipList.MAX_VALUE];
    }


}
