package ConcurrencyProgramming.T2AlternatePrintingThreeThreads;

/**
 * 使用三个线程交替打印 1~100 的数字
 * 线程1 打印 (3n+1) 的数
 * 线程2 打印 (3n+2) 的数
 * 线程3 打印 (3n) 的数
 * wait() 必须在持有 lock 的同步块里调用；调用后自动释放锁并挂起。
 *
 * 被 notifyAll() 唤醒后，线程先重新抢锁，再用 while 重新检查条件（防止“虚假唤醒”或被唤醒但还没轮到它）。
 *
 * 检查条件 + 打印 + 修改 currentNumber/turn + notifyAll 必须在同一个 synchronized 块里做，保证原子性。
 */
public class AlternatePrintingThreeThreads {

    // 当前要打印的数字
    private int currentNumber = 1;

    // 用于同步的锁对象
    private final Object lock = new Object();

    // 控制哪个线程应该打印的标志
    // 0 表示该线程打印 3n+1，1 表示该线程打印 3n+2，2 表示该线程打印 3n
    private int turn = 0;



    /**
     * 打印数字的方法
     * @param offset 线程偏移量（0 表示线程1，1 表示线程2，2 表示线程3）
     */
    public void printNumbers(int offset) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                // 如果当前线程不应该打印，则等待
                while ((turn % 3) != offset && currentNumber <= 100) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 再次检查，避免最后超出 100 时仍打印
                if (currentNumber <= 100 && (currentNumber - 1) % 3 == offset) {
                    System.out.println("Thread " + (offset + 1) + " printed: " + currentNumber);
                    currentNumber++;              // 数字自增
                    turn = (turn + 1) % 3;        // 更新标志，轮到下一个线程
                    lock.notifyAll();             // 唤醒等待的线程
                }
            }
        }
    }
}
