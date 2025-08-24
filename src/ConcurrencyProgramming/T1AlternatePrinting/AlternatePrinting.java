package ConcurrencyProgramming.T1AlternatePrinting;

public class AlternatePrinting {
    private int currentNumber = 1; // 当前要打印的数字
    private final Object lock = new Object(); // 锁对象

    /**
     * 根据 isOdd 标志打印奇数或偶数
     * @param isOdd 如果为 true，打印奇数；如果为 false，打印偶数
     */
    public void printNumbers(boolean isOdd) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                while ((isOdd && currentNumber % 2 == 0) || 
                       (!isOdd && currentNumber % 2 != 0)) {
                    try {
                        lock.wait(); // 当前线程不该打印时，等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (currentNumber <= 100) {
                    System.out.println("Thread " + (isOdd ? "Odd" : "Even") 
                                       + " printed: " + currentNumber);
                    currentNumber++; // 打印后递增
                    lock.notifyAll(); // 唤醒其他线程
                }
            }
        }
    }
}
