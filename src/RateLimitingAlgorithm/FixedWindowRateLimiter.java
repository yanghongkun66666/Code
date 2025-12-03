package RateLimitingAlgorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter {
    private Integer limit = 10; //限流额度

    private Long windowSize = 1000L;  // 固定窗口大小为1s

    private AtomicInteger cnt = new AtomicInteger(0);  //计数器

    private Long windowStart = System.currentTimeMillis();  //当前窗口的开始时间戳

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis(); //获取当前时间戳

        if (currentTime - windowStart >= windowSize) {
            //进入新的窗口
            cnt.set(0);
            windowStart = currentTime;
        }

        //还在同一个时间窗口内
        if (cnt.incrementAndGet() > limit) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowRateLimiter limiter = new FixedWindowRateLimiter();

        System.out.println("---- 第一秒（窗口内只允许 10 次通过）----");

        for (int i = 1; i <= 15; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " 是否通过: " + allowed);
            // 模拟每个请求间隔 80ms
//            Thread.sleep(80);
        }

        System.out.println("---- 等待窗口重置 ----");
        Thread.sleep(1200); //等待超过1秒，进入新的时间窗口

        System.out.println("---- 第二秒（应该重新允许 10 次通过）----");

        for (int i = 1; i <= 12; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("请求 " + i + " 是否通过: " + allowed);
//            Thread.sleep(80);
        }
    }


}
