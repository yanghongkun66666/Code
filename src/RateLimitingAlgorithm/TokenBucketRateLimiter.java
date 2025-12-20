package Demos.RateLimitingAlgorithm;

public class TokenBucketRateLimiter {
    private double capacity = 10;      // 桶容量
    private double rate = 5;           // 每秒生成多少令牌
    private double tokens = 0;         // 当前令牌数
    private long lastRefillTime = System.currentTimeMillis();

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 1. 计算本次应补充的令牌
        double generated = (now - lastRefillTime) / 1000.0 * rate;//上次补充 到现在应该补充多少令牌

        // 2. 补充令牌并更新时间
        if (generated > 0) {
            tokens = Math.min(capacity, tokens + generated);
            lastRefillTime = now;
        }

        // 3. 尝试消费令牌
        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws InterruptedException {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter();

        System.out.println("\n===== 阶段 1：瞬间发送 20 个请求（突发流量）=====");
        System.out.println("预期：前 0～10 个取决于 tokens 初始值（初始化后补满）");
        for (int i = 1; i <= 20; i++) {
            System.out.println("当前令牌数为" + limiter.tokens);
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " -> " + allowed);
        }

        System.out.println("\n===== 阶段 2：等待 1 秒（预计补充 rate 个令牌）=====");
        Thread.sleep(1000);
        System.out.println("再次发送 12 个请求：");
        for (int i = 1; i <= 12; i++) {
            System.out.println("当前令牌数为" + limiter.tokens);
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求2-" + i + " -> " + allowed);
        }

        System.out.println("\n===== 阶段 3：连续快速请求（每 50ms 一次）=====");
        System.out.println("观察令牌桶如何被快速消耗并逐步恢复");
        for (int i = 1; i <= 20; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("当前令牌数为" + limiter.tokens);
            System.out.println("请求3-" + i + " -> " + allowed);
            Thread.sleep(50);
        }

        System.out.println("\n===== 阶段 4：等待 3 秒（令牌恢复至满桶）=====");
        Thread.sleep(3000);
        System.out.println("发送 12 个请求（全部应为 true）");
        for (int i = 1; i <= 12; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("当前令牌数为" + limiter.tokens);
            System.out.println("请求4-" + i + " -> " + allowed);
            Thread.sleep(50);
        }
    }
}
