package ConcurrencyProgramming.T2AlternatePrintingThreeThreads;

public class Main {
    public static void main(String[] args) {
        AlternatePrintingThreeThreads ap = new AlternatePrintingThreeThreads();

        // 创建并启动 3 个打印线程
        Thread printer1 = new Thread(() -> ap.printNumbers(0));
        Thread printer2 = new Thread(() -> ap.printNumbers(1));
        Thread printer3 = new Thread(() -> ap.printNumbers(2));

        printer1.start();
        printer2.start();
        printer3.start();
    }
}
