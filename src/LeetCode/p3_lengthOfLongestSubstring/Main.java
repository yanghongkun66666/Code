package LeetCode.p3_lengthOfLongestSubstring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String line = br.readLine();

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        Solution solution = new Solution();
        System.out.println(solution.lengthOfLongestSubstring(line));
    }
}
