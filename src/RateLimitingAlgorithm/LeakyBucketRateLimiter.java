package RateLimitingAlgorithm;

public class LeakyBucketRateLimiter {
    private double capacity = 10;    // 桶容量
    private double rate = 0.1;       // 每毫秒漏出0.1请求  每10ms漏1个请求
    private double water = 0;        // 当前水量
    private long lastLeakTime = System.currentTimeMillis();

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 计算漏掉的水
        double leaked = (now - lastLeakTime) * rate;

        if (leaked > 0) {
            water = Math.max(0, water - leaked);
            lastLeakTime = now;
        }

        // 添加请求
        if (water + 1 <= capacity) {
            water++;
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws InterruptedException {
        LeakyBucketRateLimiter limiter = new LeakyBucketRateLimiter();

        System.out.println("===== 阶段 1：瞬间发送 20 个请求（突发流量）=====");
        System.out.println("预期：前 10 个 true，之后 false（桶已满）");
        for (int i = 1; i <= 20; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " -> " + allowed);
        }

        System.out.println("\n===== 阶段 2：等待 50ms（漏掉 5 req）=====");
        Thread.sleep(50); // 50ms * 0.1req/ms = 漏 5 个

        System.out.println("尝试再请求 10 次（前 5 次 true）");
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求(阶段2) " + i + " -> " + allowed);
            Thread.sleep(5);
        }

        System.out.println("\n===== 阶段 3：持续请求（5ms 间隔）=====");
        for (int i = 1; i <= 20; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求(阶段3) " + i + " -> " + allowed);
            Thread.sleep(5); // 比 10ms 快，观察漏桶是否限流
        }
    }
}
