import LeetCode.p143_reorderList.ListNode;

import javax.swing.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class tmp {

    public static void main(String[] args) {
        AlternatingPrintingThreeThreads ap = new AlternatingPrintingThreeThreads();

        Thread t1 = new Thread(()->{
            ap.printNumbers(0);
        });

        Thread t2 = new Thread(()->{
           ap.printNumbers(1);
        });

        Thread t3 = new Thread(()->{
            ap.printNumbers(2);
        });

        t1.start();
        t2.start();
        t3.start();
    }


}
