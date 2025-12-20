package Demos.RateLimitingAlgorithm;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowLogRateLimiter {
    private int limit = 10;

    private long windowSize = 1000;

    private Deque<Long> timestamps = new ArrayDeque<>(); //记录每个请求的时间戳

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        //1.移除过期时间戳
        while (!timestamps.isEmpty() && now - timestamps.getFirst() > windowSize) {
            timestamps.removeFirst();
        }


        //2.判断当前滑动窗口是否已满
        if (timestamps.size() >= limit) {
            return false;
        }


        timestamps.addLast(now);
        return true;
    }


    public static void main(String[] args) throws InterruptedException {

        SlidingWindowLogRateLimiter limiter = new SlidingWindowLogRateLimiter();

        System.out.println("=== 【阶段 1】快速发送 12 个请求（80ms 间隔）===");
        System.out.println("目的：触发限流，让窗口达到最大容量");
        for (int i = 1; i <= 12; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " -> " + allowed);
//            Thread.sleep(80);
        }

        System.out.println("\n=== 【阶段 2】等待 500ms（不到 1 秒） ==");
        System.out.println("目的：观察部分旧请求过期，滑动窗口开始“滑动释放额度”");
        Thread.sleep(500);

        System.out.println("\n=== 【阶段 3】再发送 6 个请求（间隔 100ms）===");
        System.out.println("目的：滑动窗口不断移动，此时可能部分恢复为 true");
        for (int i = 1; i <= 6; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("阶段3 请求 " + i + " -> " + allowed);
            Thread.sleep(100);
        }

        System.out.println("\n=== 【阶段 4】等待 1 秒（让整个窗口清空）===");
        Thread.sleep(1000);

        System.out.println("\n=== 【阶段 5】再发送 6 个请求 == (全部应该是 true) ===");
        for (int i = 1; i <= 6; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("阶段5 请求 " + i + " -> " + allowed);
            Thread.sleep(80);
        }
    }



}
