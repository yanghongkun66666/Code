package ConcurrencyProgramming.T12ProgrammerAndConsumer.T1017Sync;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用 Callable 和 FutureTask 实现生产者-消费者模型的示例程序。
 * <p>
 * 程序通过共享缓冲区（数组）模拟生产者生产数据、消费者消费数据的过程，
 * 并利用 synchronized 关键字保证线程安全。
 * </p>
 */
public class ProducerConsumerCallableLambda {
    /** 缓冲区的最大容量 */
    private static final int CAPACITY = 10;

    /** 当前缓冲区中的元素个数 */
    private static int curcap = 0;

    /** 共享缓冲区，用于存放生产的对象 */
    private static final Object[] nums = new Object[CAPACITY];

    /** 生产者的写入位置索引 */
    private static int in = 0;

    /** 消费者的读取位置索引 */
    private static int out = 0;

    /** 用于同步访问共享资源的锁对象 */
    private static final Object lock = new Object();

    /**
     * 主方法，启动并执行生产者和消费者任务。
     *
     * @param args 命令行参数（未使用）
     * @throws ExecutionException   如果计算过程中抛出异常
     * @throws InterruptedException 如果等待时被中断
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
         * 定义生产者 Callable 任务：
         * - 在缓冲区未满的情况下生产一个随机整数放入缓冲区；
         * - 若缓冲区已满则阻塞等待；
         * - 成功生产后唤醒其他等待线程，并返回所生产的数据。
         */
        Callable<Integer> producer = () -> {
            synchronized (lock) {
                while (curcap == CAPACITY) {
                    lock.wait();
                }
                int product = new Random().nextInt(1000);
                nums[in] = product;
                in = (in + 1) % CAPACITY;
                curcap++;
                System.out.println(Thread.currentThread().getName() + " 生产了: " + product);
                lock.notifyAll();
                return product; // 返回生产结果
            }
        };

        /*
         * 定义消费者 Callable 任务：
         * - 在缓冲区非空的情况下取出一个元素进行消费；
         * - 若缓冲区为空则阻塞等待；
         * - 成功消费后唤醒其他等待线程，并返回所消费的数据。
         */
        Callable<Integer> consumer = () -> {
            synchronized (lock) {
                while (curcap == 0) {
                    lock.wait();
                }
                int consumeNum = (int) nums[out];
                out = (out + 1) % CAPACITY;
                curcap--;
                System.out.println(Thread.currentThread().getName() + " 消费了: " + consumeNum);
                lock.notifyAll();
                return consumeNum; // 返回消费结果
            }
        };

        // 使用 FutureTask 包装 Callable 任务以便获取其执行结果
        FutureTask<Integer> producerTask = new FutureTask<>(producer);
        FutureTask<Integer> consumerTask = new FutureTask<>(consumer);

        // 创建并启动两个线程分别运行生产者和消费者任务
        new Thread(producerTask, "生产者线程").start();
        new Thread(consumerTask, "消费者线程").start();

        // 获取两个任务的执行结果并在主线程中打印输出
        Integer product = producerTask.get();
        Integer consumed = consumerTask.get();
        System.out.println("主线程得到：生产了 " + product + "，消费了 " + consumed);
    }
}
