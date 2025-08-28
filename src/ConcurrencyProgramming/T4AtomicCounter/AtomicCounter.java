package ConcurrencyProgramming.T4AtomicCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // 创建一个固定大小为100的线程池
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // 提交100个任务，每个任务执行100次累加
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        counter.incrementAndGet(); // 原子操作，线程安全
                    }
                }
            });
        }

        // 关闭线程池，不再接受新的任务
        executor.shutdown();
        // 等待所有任务完成
        executor.awaitTermination(1, TimeUnit.HOURS);

        // 输出最终的计数值
        System.out.println("Final count: " + counter.get());
    }
}
