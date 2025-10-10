import LeetCode.p143_reorderList.ListNode;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class tmp {

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 2, 3};
        Arrays.sort(arr, (a, b)-> {
            return b - a;
        });

        for (int num : arr) {
            System.out.println(num);
        }
    }


}
