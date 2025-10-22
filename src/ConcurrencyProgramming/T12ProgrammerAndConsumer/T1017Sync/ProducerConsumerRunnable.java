package ConcurrencyProgramming.T12ProgrammerAndConsumer.T1017Sync;

import java.util.Random;

public class ProducerConsumerRunnable {
    private static final int CAPACITY = 10;
    private static int curcap = 0;
    private static Object[] nums = new Object[CAPACITY];
    private static int in = 0;
    private static int out = 0;
    private static final Object lock = new Object();

    // 生产者
    static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (curcap == CAPACITY) {
                        try {
                            lock.wait(); // 等待队列非满
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // 生产
                    int product = new Random().nextInt(1000);
                    nums[in] = product;
                    in = (in + 1) % CAPACITY;
                    curcap++;
                    System.out.println(Thread.currentThread().getName() + " 生产了: " + product);
                    lock.notifyAll(); // 通知消费者
                }
            }
        }
    }

    // 消费者
    static class Consumer implements Runnable {
        @Override
        public void run() {
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
                    lock.notifyAll(); // 通知生产者
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(new Producer(), "生产者线程");
        Thread consumerThread = new Thread(new Consumer(), "消费者线程");

        producerThread.start();
        consumerThread.start();
    }
}
