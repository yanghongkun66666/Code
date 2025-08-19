package AWeiDianDong.LRU.LRU;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        LRU cache = new LRU(2);

        // Test Case 1
        System.out.println("Test Case 1:");
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("Get 1: " + cache.get(1) + " (Expected: 1)");
        cache.put(3, 3);
        System.out.println("Get 2: " + cache.get(2) + " (Expected: -1)");
        cache.put(4, 4);
        System.out.println("Get 1: " + cache.get(1) + " (Expected: -1)");
        System.out.println("Get 3: " + cache.get(3) + " (Expected: 3)");
        System.out.println("Get 4: " + cache.get(4) + " (Expected: 4)");

        // Test Case 2
        System.out.println("Test Case 2:");
        cache.put(5, 5);
        System.out.println("Get 3: " + cache.get(3) + " (Expected: -1)");
        System.out.println("Get 4: " + cache.get(4) + " (Expected: 4)");
        System.out.println("Get 5: " + cache.get(5) + " (Expected: 5)");
    }
}

