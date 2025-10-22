package ConcurrencyProgramming.T12ProgrammerAndConsumer.T1017Sync;

import java.util.Random;

public class ProducerConsumerRunnableLambda {
    private static final int CAPACITY = 10;
    private static int curcap = 0;
    private static final Object[] nums = new Object[CAPACITY];
    private static int in = 0;
    private static int out = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // ✅ 使用 Lambda 表达式创建生产者线程
        Runnable producer = () -> {
            Random random = new Random();
            while (true) {
                synchronized (lock) {
                    while (curcap == CAPACITY) {
                        try {
                            lock.wait(); // 等待队列非满
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    int product = random.nextInt(1000);
                    nums[in] = product;
                    in = (in + 1) % CAPACITY;
                    curcap++;
                    System.out.println(Thread.currentThread().getName() + " 生产了: " + product);
                    lock.notifyAll(); // 唤醒消费者
                }
            }
        };

        // ✅ 使用 Lambda 表达式创建消费者线程
        Runnable consumer = () -> {
            while (true) {
                synchronized (lock) {
                    while (curcap == 0) {
                        try {
                            lock.wait(); // 等待队列非空
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    int consumeNum = (int) nums[out];
                    out = (out + 1) % CAPACITY;
                    curcap--;
                    System.out.println(Thread.currentThread().getName() + " 消费了: " + consumeNum);
                    lock.notifyAll(); // 唤醒生产者
                }
            }
        };

        // ✅ 启动线程（仍使用 Thread 类，但传入 Lambda）
        new Thread(producer, "生产者线程").start();
        new Thread(consumer, "消费者线程").start();
    }
}
