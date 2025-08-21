package AWeiDianDong.SkipList;

public class Main {
    public static void main(String[] args) {
        SkipList skiplist = new SkipList();

        // 测试添加
        skiplist.add(1);
        skiplist.add(3);
        skiplist.add(4);
        skiplist.add(7);
        skiplist.add(8);
        skiplist.add(9);

        // 测试搜索
        System.out.println("Search 3: " + (skiplist.search(3) ? 1 : 0)); // 1
        System.out.println("Search 5: " + (skiplist.search(5) ? 1 : 0)); // 0

        // 测试删除
        System.out.println("Erase 3: " + (skiplist.erase(3) ? 1 : 0));   // 1
        System.out.println("Search 3: " + (skiplist.search(3) ? 1 : 0)); // 0

        // 再次删除一个不存在的元素
        System.out.println("Erase 5: " + (skiplist.erase(5) ? 1 : 0));   // 0

        // 额外：区间查询
        System.out.println("rangeQuery(1, 8): " + skiplist.rangeQuery(1, 8));
    }
}
