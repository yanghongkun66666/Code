package AWeiDianDong.LFU;

public class Main {
    public static void main(String[] args) {
        LFU cache = new LFU(2);

        System.out.println("Test Case 1:");
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("Get 1: " + cache.get(1) + " (Expected: 1)");
        cache.put(3, 3); // 淘汰 key=2（freq 最小且最久未用）
        System.out.println("Get 2: " + cache.get(2) + " (Expected: -1)");
        cache.put(4, 4); // 淘汰 key=3（freq=1，最久未用）
        System.out.println("Get 1: " + cache.get(1) + " (Expected: 1)");
        System.out.println("Get 3: " + cache.get(3) + " (Expected: -1)");
        System.out.println("Get 4: " + cache.get(4) + " (Expected: 4)");

        System.out.println("Test Case 2:");
        cache.put(5, 5); // 淘汰 key=4
        System.out.println("Get 3: " + cache.get(3) + " (Expected: -1)");
        System.out.println("Get 4: " + cache.get(4) + " (Expected: -1)");
        System.out.println("Get 5: " + cache.get(5) + " (Expected: 5)");
    }
}
