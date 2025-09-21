package LeetCode.p131_partition;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //s = "aab"
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        List<List<String>> res = solution.partition(str);
        for (List<String> inner : res) {
            for (String s : inner) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}
