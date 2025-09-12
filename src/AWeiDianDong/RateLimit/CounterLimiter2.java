package AWeiDianDong.RateLimit;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterLimiter2 {

    //初始时间
    private static long startTime = System.currentTimeMillis();

    //初始计数值
    private static final AtomicInteger ZERO = new AtomicInteger(0);

    //时间窗口限制
    private static final int interval = 10000;

    //限制通过请求
    private static int limit = 100;

    //请求计数
    private AtomicInteger requestCount = ZERO;

    //获取限流
    public boolean tryAcquire() {

        long now = System.currentTimeMillis();
        //在时间窗口内
        if (now < startTime + interval) {
            //判断是否超过最大请求
            if (requestCount.get() < limit) {
                requestCount.incrementAndGet();
                return true;
            }
            return false;
        } else {
            //超时重置
            requestCount = ZERO;
            startTime = now;
            return true;
        }
    }
}