package ConcurrencyProgramming.T12ProgrammerAndConsumer.T1017Sync;

import java.util.Random;

/**
 * 继承Thread实现
 */
public class ProducerConsumerThread {
    //需要一个缓冲区，因此指定缓冲区大小
    private static final int CAPACITY = 10;
    //需要一个当前元素数量
    private static int curcap = 0;
    //有一个缓冲区队列
    private static Object[] nums = new Object[CAPACITY];
    //生产者放入元素的下标
    private static int in = 0;
    //消费者消费元素的下标
    private static int out = 0;
    //控制访问队列的互斥锁
    private static Object lock = new Object();

    //默认修饰符是default，默认包内可访问
    static class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (curcap == CAPACITY) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    //此时队列不满了
                    Random random = new Random();
                    int product = random.nextInt();
                    nums[in] = product;
                    in = (in + 1) % CAPACITY;
                    curcap++;
                    System.out.println("生产了: " + product);
                    lock.notifyAll(); //唤醒所有消费者，此时队列中有元素了，不空了，可以消费了
                }
            }
        }

        static class Consumer extends Thread {
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        while (curcap == 0) {
                            try {
                                lock.wait(); //当前消费者线程等待队列非空
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        //此时队列非空了
                        int consumeNum = (int)(nums[out]);
                        out = (out + 1) % CAPACITY;
                        curcap--;
                        System.out.println("消费了: " + consumeNum);
                        lock.notifyAll(); //唤醒所有生产者，此时队列中有空位，不满了，可以生产了
                    }
                }
            }
        }


        public static void main(String[] args) {
            Producer producer = new Producer();
            Consumer consumer = new Consumer();
            producer.start();
            consumer.start();
        }

    }
}
