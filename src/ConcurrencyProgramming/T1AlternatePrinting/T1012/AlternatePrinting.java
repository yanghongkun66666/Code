package ConcurrencyProgramming.T1AlternatePrinting.T1012;

import java.time.chrono.ThaiBuddhistEra;

public class AlternatePrinting {
    private int curNum = 1;
    private Object lock = new Object();

    public static void main(String[] args) {
        AlternatePrinting ap = new AlternatePrinting();
        Thread odd = new Thread(()->ap.PrintNums(true)); //这是什么意思，哪一种创建线程方式？这是runnable创建方式
        /*
        Thread odd = new Thread(new Runnable() {
            @Override
            public void run() {
                ap.PrintNums(true);
            }
        });
         */
        Thread even = new Thread(()->ap.PrintNums(false)); //这是什么意思，哪一种创建线程方式？
        odd.start();
        even.start();

    }

    private void PrintNums(boolean isOdd) {
        while (curNum <= 100) {
            synchronized (lock) {
                while ((isOdd == true && curNum % 2 == 0) || (isOdd == false && curNum % 2 == 1)) {
                    //此时不该当前线程打印  用while唤醒之后看是否需要打印，避免虚假唤醒
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (curNum <= 100) {
                    System.out.println("Thread " + (isOdd ? "Odd" : "Even") + " printed: "+ curNum);
                    curNum++;
                    lock.notifyAll();
                }
            }
        }
    }
}
