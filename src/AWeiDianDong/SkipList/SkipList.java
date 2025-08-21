package AWeiDianDong.SkipList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SkipList {
    public static final int MAX_VALUE = 8;

    private final Node head; //可以将跳表想成一个链表，链表每个节点是一个node，node是由多层构成的。

    public SkipList() {
        //头结点使用最大层
        head = new Node(0, MAX_VALUE);
    }

    //概率晋升，约 1 / 4的概率晋升
    private int getRandomHeight() {
        int h = 1;
        while (h < MAX_VALUE && ThreadLocalRandom.current().nextInt(4) == 1) {
            ++h;
        }

        return h;
    }

    /**
     * 在跳表中找到第一个 >= target的节点
     * 如果数组非空，就在每一层记录该层前驱节点，供插入删除使用
     */

    private Node findGreaterOrEqual(int target, Node[] prev) {
        Node it = head;
        int level = MAX_VALUE - 1;

        while (true) {
            //一层一层向下找 当前层水平走不动了就垂直向下走
            Node nxt = it.next[level];

            if (nxt != null && nxt.value < target) {
                it = nxt;
            } else {
                //两种情况，当前it不为空，nxt为空，此时当前层要插入的前驱节点就是it节点
                //第二种：nxt不为空，但是nxt的value >= target了，找到了第一个大于等于target的元素，因此应当记录前驱节点
                if (prev != null) {
                    prev[level] = it;//那别的prev呢？
                }

                if (level == 0) {
                    return nxt;
                } else {
                    --level;
                }
            }
        }
    }

    public boolean search (int target) {
        Node it = findGreaterOrEqual(target, null);
        return it != null && it.value == target;
    }

    public void add(int num) {
        Node[] prev = new Node[MAX_VALUE];
        Node node = new Node(num, getRandomHeight());

        //找到各层待插入点前驱节点
        findGreaterOrEqual(num, prev);

        // 在各层插入（仅到 node.height-1 层）
        for(int i = 0; i < node.height; ++i) {
            node.next[i] = prev[i].next[i];
            prev[i].next[i] = node;
        }
    }

    public boolean erase(int num) {
        Node[] prev = new Node[MAX_VALUE];
        Node target = findGreaterOrEqual(num, prev);

        if (target == null || target.value != num) return false;

        for (int i = 0; i < target.height; ++i) {
            if (prev[i].next[i] == target) {
                //因为每一层下一个节点不一定是target，因此要加一个target跳表节点的判断
                prev[i].next[i] = target.next[i];
            }
        }
        return true;
    }

    /**
     * - 从第一个 >= start 的位置开始
     * - 先跳到其 level-0 后继，再判断 <= end 后收集
     */
    public List<Integer> rangeQuery(int start, int end) {
        List<Integer> res = new ArrayList<>();
        Node cur = findGreaterOrEqual(start, null);

        //这里查的是[start, end]
        while (cur != null && cur.next[0] != null && cur.next[0].value <= end) {
            res.add(cur.value);
            cur = cur.next[0];
        }

        return  res;

    }




}
