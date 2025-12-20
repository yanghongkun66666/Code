package Demos.RateLimitingAlgorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class SlidingWindowCounterRateLimiter {
    //把一秒分成10个100ms
    private final int limit = 10;
    private final int windowSize = 1000;
    private final int bucketNum = 10;
    private final int bucketSize = windowSize / bucketNum;

    private final AtomicInteger[] buckets = new AtomicInteger[bucketNum]; //代表这么多个小块

    private volatile long lastResetTime = System.currentTimeMillis(); //记录“最近一次有效请求的时间”，用于判断整个窗口是否已经过期

    public SlidingWindowCounterRateLimiter() {
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = new AtomicInteger(0);
        }
    }


    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        int index = (int) ((now / bucketSize) % bucketNum); //获取到当前时间戳所在的桶

        // 如果离上次重置时间超过一个窗口大小，就清空所有 bucket
        if (now - lastResetTime >= windowSize) {
            resetAll();
        }

        // 当前 bucket 计数 + 其他 bucket 总和
        int total = 0;
        for (AtomicInteger bucket : buckets) {
            total += bucket.get();
        }

        if (total < limit) {
            buckets[index].incrementAndGet();
            lastResetTime = now;
            return true;
        }
        return false;
    }

    private void resetAll() {
        for (AtomicInteger bucket : buckets) {
            bucket.set(0);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        SlidingWindowCounterRateLimiter limiter = new SlidingWindowCounterRateLimiter();

        System.out.println("===== 阶段 1：快速发送 12 个请求（80ms 间隔）=====");
        System.out.println("目的：在 1000ms 内触发限流");
        for (int i = 1; i <= 12; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " -> " + allowed);
            Thread.sleep(80); // 使请求集中在一个窗口内
        }

        System.out.println("\n===== 阶段 2：等待 400ms（部分 bucket 过期）=====");
        System.out.println("目的：滑动窗口自然释放配额，但不会全部清空");
        Thread.sleep(400);

        System.out.println("\n===== 阶段 3：再发 6 个请求（100ms 间隔）=====");
        System.out.println("目的：观察滑动窗口逐渐恢复，可能会返回 true");
        for (int i = 1; i <= 6; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("阶段3 请求 " + i + " -> " + allowed);
            Thread.sleep(100);
        }

        System.out.println("\n===== 阶段 4：等待 1200ms（超过 1 秒，无请求）=====");
        System.out.println("目的：现在应该触发 resetAll()，窗口完全清空");
        Thread.sleep(1200);

        System.out.println("\n===== 阶段 5：再次发送 6 个请求（全部应为 true）=====");
        for (int i = 1; i <= 6; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("阶段5 请求 " + i + " -> " + allowed);
            Thread.sleep(80);
        }
    }

}
