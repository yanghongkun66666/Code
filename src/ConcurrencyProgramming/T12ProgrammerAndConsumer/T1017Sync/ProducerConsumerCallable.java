package ConcurrencyProgramming.T12ProgrammerAndConsumer.T1017Sync;

import java.io.ObjectStreamException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ProducerConsumerCallable {
    private static final int CAPACITY = 10;
    private static int curcap = 0;
    private static Object[] nums = new Object[CAPACITY];
    private static int in = 0;
    private static int out = 0;
    private static Object lock = new Object();

    static class Producer implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            synchronized(lock) {
                while (curcap == CAPACITY) {
                    lock.wait();
                }

                int product = new Random().nextInt(1000);
                nums[in] = product;
                in = (in + 1) % CAPACITY;
                curcap++;
                System.out.println(Thread.currentThread().getName() + " 生产了: " + product);
                lock.notifyAll();
                return product; // 返回生产的结果
            }
        }


    }

    // 消费者 Callable，可以返回消费的产品编号
    static class Consumer implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            synchronized (lock) {
                while (curcap == 0) {
                    lock.wait();
                }
                int consumeNum = (int) nums[out];
                out = (out + 1) % CAPACITY;
                curcap--;
                System.out.println(Thread.currentThread().getName() + " 消费了: " + consumeNum);
                lock.notifyAll();
                return consumeNum;
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> producerTask = new FutureTask<>(new Producer());
        FutureTask<Integer> consumerTask = new FutureTask<>(new Consumer());

        Thread producerThread = new Thread(producerTask);
        Thread consumerThread = new Thread(consumerTask);

        producerThread.start();
        consumerThread.start();

        // 获取返回值
        Integer product = producerTask.get();
        Integer consumed = consumerTask.get();

        System.out.println("主线程得到：生产了 " + product + "，消费了 " + consumed);


    }



}
