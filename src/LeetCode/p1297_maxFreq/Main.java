package LeetCode.p1297_maxFreq;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //public int maxFreq(String S, int maxLetters, int minSize, int maxSize) {
        Scanner sc = new Scanner(System.in);
        String S = sc.nextLine();
        int maxLetters = sc.nextInt(), minSize = sc.nextInt(), maxSize = sc.nextInt();

        Solution solution = new Solution();
        System.out.println(solution.maxFreq(S, maxLetters, minSize, maxSize));
    }
}
