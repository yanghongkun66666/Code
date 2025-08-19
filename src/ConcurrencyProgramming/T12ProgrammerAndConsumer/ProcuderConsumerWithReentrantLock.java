package ConcurrencyProgramming.T12ProgrammerAndConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock代替synchronized关键字实现锁机制
 * 引入两个Condition，notFull用于生产者等待缓冲区不满时，如果没满就可以放，满了就等待在notFull上
 * notEmpty用于表示消费者等待缓冲区不空时，不空就可以取，空了就等待在notEmpty上，等他不空.
 * 生产者在缓冲区满时，调用notFull.await()  消费者在缓冲区为空时，调用notEmpty.await()
 * 生产生产后，调用notEmpty.signal()来唤醒消费者线程，生产者生产后队列已经不空了，于是唤醒等待在notEmpty的消费者线程
 * 消费者消费后，调用notFull.signal来唤醒生产者线程，消费者消费后队列已经不空了，于是唤醒等待在notFull的生产者线程。
 */
public class ProcuderConsumerWithReentrantLock {
    //缓冲区大小
    private static final int BUFFER_SIZE = 10;

    //当前缓冲区的元素的数量
    private static int count = 0;

    //缓冲区数组
    private static Object[] buffer = new Object[BUFFER_SIZE];

    //下一个要放入缓冲区的索引位置
    private static int in = 0;

    //下一个要取出缓冲区的索引位置
    private static int out = 0;

    //可重入锁
    private static final ReentrantLock lock = new ReentrantLock();

    //用于生产者等待缓冲区不满时
    private static final Condition notFull = lock.newCondition();

    //用于消费者等待缓冲区不空时
    private static final Condition notEmpty = lock.newCondition();

    //生产者线程类
    static class Producer extends Thread {
        @Override
        public void run() {
            for (int num = 0; num < 100; num++) {
                lock.lock();
                try {
                    while (count == BUFFER_SIZE) {
                        //满了，生产者等待在notFull上
                        System.out.println("队列已满，生产者等待=============");
                        notFull.await(); //等待缓冲区不满，当前生产者线程在抢到的锁范围内
                    }

                    //生产数据
                    buffer[in] = num;
                    in = (in + 1) % BUFFER_SIZE;
                    count++;
                    System.out.println("生产数据：" + num + "缓冲区元素数量：" + count);
                    notEmpty.signal(); //生产一个就唤醒等待的消费者，队列不为空了
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock(); //释放锁
                }
            }
        }
    }


    static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();//试图获取锁
                try {
                    while (count == 0) {
                        System.out.println("当前队列为空，消费者等待=========");
                        notEmpty.await(); //等他不空
                    }

                    //消费数据
                    Object data = buffer[out];
                    out = (out + 1) % BUFFER_SIZE;
                    --count;

                    System.out.println("消费数据：" + data + "缓冲区元素数量" + count);
                    notFull.signal(); //唤醒生产者，消费完了就不再是满的了
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
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
