package ConcurrencyProgramming.T3Sequentialprinting;

public class SequentialPrinting {

    private int count = 0; // 用来跟踪当前的打印轮次
    private final Object lock = new Object(); // 用于同步的锁对象

    public static void main(String[] args) {
        SequentialPrinting printer = new SequentialPrinting();

        // 创建并启动三个打印线程
        Thread threadA = new Thread(() -> printer.printNumber(1), "A");
        Thread threadB = new Thread(() -> printer.printNumber(2), "B");
        Thread threadC = new Thread(() -> printer.printNumber(3), "C");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    private void printNumber(int numberToPrint) {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                while (count % 3 != numberToPrint - 1) {
                    try {
                        lock.wait(); // 如果不是该线程打印，则等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (count < 30) { // 确保只打印 10 轮
                    System.out.println("Thread " + Thread.currentThread().getName() 
                                       + " 打印 " + numberToPrint);
                    count++; // 增加计数器，进入下一轮打印
                    lock.notifyAll(); // 唤醒其他等待的线程
                }
            }
        }
    }
}
