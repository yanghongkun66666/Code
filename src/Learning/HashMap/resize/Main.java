package Learning.HashMap.resize;

public class Main {
    //(hash & oldCap) == 0  -> 留在原地
    //(hash & oldCap) != 0  -> 拆分到新链表

    Node[] table;
    int size;
    int threshold;
    float loadFactor = 0.75f;


    static class Node {
        int key;
        int val;
        int hash;
        Node next;

        Node(int key, int val, Node next) {
            this.key = key;
            this.val = val;
            this.hash = mixHash(key); // 这里 key 就当 hash 输入  加了扰动
            this.next = next;
        }
    }

    static int mixHash(int x) {
        return x ^ (x >>> 16);
    }

    //获取桶下标
    int index(int hash, int n) {
        return (n - 1) & hash;
    }

    //扩容 + 拆分链表
    void resize() {
        Node[] oldTab = table;
        int oldCap = oldTab.length;
        int newCap = oldCap << 1;

        Node[] newTab = new Node[newCap];

        for (int i = 0; i < oldCap; i++) {
            Node e = oldTab[i];
            if (e == null) continue;

            Node loHead = null, loTail = null;
            Node hiHead = null, hiTail = null;

            while (e != null) {
                Node next = e.next;
                e.next = null;

                // 关键判断：看 hash 在 oldCap 这一位是 0 还是 1
                if ((e.hash & oldCap) == 0) {
                    // stay at i
                    if (loTail == null) loHead = e;
                    else loTail.next = e;
                    loTail = e;
                } else {
                    // 移动到 i + 旧容量
                    if (hiTail == null) hiHead = e;
                    else hiTail.next = e;
                    hiTail = e;
                }

                e = next;
            }

            newTab[i] = loHead;
            newTab[i + oldCap] = hiHead;
        }

        table = newTab;
        threshold = (int) (newCap * loadFactor);
    }




}
