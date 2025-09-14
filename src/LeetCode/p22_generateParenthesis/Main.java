package LeetCode.p22_generateParenthesis;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Solution solution = new Solution();
        List<String> res =  solution.generateParenthesis(n);

        for (String str : res) {
            System.out.println(str);
        }
    }

}
