package ConcurrencyProgramming.T5CrossPrint;

public class CrossPrint {
    private static final Object lock = new Object();
    private static boolean isNumberTurn = true;

    public static void main(String[] args) {
        Thread printNumberThread = new Thread(()->{
            for (int i = 1; i <= 52; i += 2) {
                synchronized (lock) {
                    while (isNumberTurn == false) {
                        try {
                            lock.wait();
                        }  catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(i);
                    System.out.println(i + 1);
                    isNumberTurn = false;  //切换打印标志
                    lock.notifyAll(); //唤醒打印字母的线程
                }
            }
        });

        Thread printLetterThread = new Thread(()->{
            for (char c = 'A'; c <= 'Z'; ++c) {
                synchronized (lock) {
                    while (isNumberTurn == true) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(c);
                    isNumberTurn = true; //切换打印标志
                    lock.notifyAll(); //唤醒打印数字的线程
                }
            }
        });


        printNumberThread.start();
        printLetterThread.start();
    }
}
