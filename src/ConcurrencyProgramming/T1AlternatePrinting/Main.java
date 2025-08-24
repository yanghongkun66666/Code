package ConcurrencyProgramming.T1AlternatePrinting;

public class Main {
    public static void main(String[] args) {
        AlternatePrinting ap = new AlternatePrinting();

        // 启动打印奇数的线程
        Thread oddPrinter = new Thread(() -> ap.printNumbers(true));
        oddPrinter.start();

        // 启动打印偶数的线程
        Thread evenPrinter = new Thread(() -> ap.printNumbers(false));
        evenPrinter.start();
    }
}
