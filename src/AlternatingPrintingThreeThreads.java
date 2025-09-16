public class AlternatingPrintingThreeThreads {
    private int currentNumber = 1; //下一个要打印的数字

    private final Object lock = new Object(); //锁

    private int turn; //控制线程打印的变量  turn % 3 == 0第一个线程  == 1第二个线程   ==2第三个线程

    public void printNumbers(int offset) {
        while (currentNumber <= 1000) {
            synchronized (lock) {
                while (currentNumber <= 1000 && (turn % 3) != offset) {
                    try {
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (currentNumber <= 1000) {
                    System.out.println(Thread.currentThread().getName() + " print:" + currentNumber);
                    ++currentNumber;
                    ++turn;
                    lock.notifyAll();
                }
            }
        }
    }
}
