package AWeiDianDong.LRU.LRUTTL;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 基础操作测试
        LRUTTL cache = new LRUTTL(2, 3600);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // 输出 1

        cache.put(3, 3); // 淘汰键2
        System.out.println(cache.get(2)); // 输出 -1

        cache.put(4, 4); // 淘汰键1
        System.out.println(cache.get(1)); // 输出 -1
        System.out.println(cache.get(3)); // 输出 3
        System.out.println(cache.get(4)); // 输出 4

        // TTL 过期测试
        LRUTTL cache2 = new LRUTTL(2, 1); // TTL 1秒
        cache2.put(1, 1);
        System.out.println(cache2.get(1)); // 输出 1
        Thread.sleep(2000);
        System.out.println(cache2.get(1)); // 输出 -1（过期）

        // TTL 重置测试
        LRUTTL cache3 = new LRUTTL(2, 2); // TTL 2秒
        cache3.put(1, 1);
        Thread.sleep(1000);
        System.out.println(cache3.get(1)); // 输出 1（刷新 TTL）
        Thread.sleep(1000);
        System.out.println(cache3.get(1)); // 输出 1（仍有效）
        Thread.sleep(2000);
        System.out.println(cache3.get(1)); // 输出 -1（过期）
    }
}
