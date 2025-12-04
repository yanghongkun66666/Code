package WrittenTest.YKSS1204.T2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
    /*
    我先把题目抽象成「交错字符串」问题：s3 是否能由 s1 和 s2 按原相对顺序交错组成。

    第一步先做长度剪枝，如果长度不等直接返回 false。
    然后我尝试过用双指针贪心去做，但是很快发现一个问题：当 s1 和 s2 当前字符都等于 s3 的当前字符时，贪心的选择可能导致后续无法匹配，而另一个分支其实是可行的，这说明这是一个「存在分支选择且需要回溯」的问题。

    基于这个观察，我把问题转为前缀状态：
    定义 dp[i][j] 表示 s1 的前 i [0, i - 1]个字符和 s2 的前 j [0, j - 1]个字符，能否构成 s3 的前 i+j [0, i + j - 1]个字符。
    从最后一位往前看，它要么来自 s1 的第 i (下标i - 1)个，要么来自 s2 的第 j (下标j - 1)个，因此有两个转移来源：

    如果 s1[i-1] == s3[i+j-1]，可以从 dp[i-1][j] 转来  如果s1 [0, i - 2]  和s2 [0, j - 1]能组成s3[0, i - 1 + j - 1]

    如果 s2[j-1] == s3[i+j-1]，可以从 dp[i][j-1] 转来
    综合就是一个布尔 or。

    最后答案看 dp[n][m] 即可。  能否构成前m + n个字符   [0, m + n - 1] 构成完整字符串
     */
    //line3是line1和line2的组合 同时line1和line2在line3中的相对顺序不变 12  AB   1AB2   YES
//    public static void main(String[] args) {
//    //个人解法是贪心解法，不过一旦两个队头字符相同，选错一个后续结果就错了，所以贪心无法解决这个问题
    //s1 = "A"
    //s2 = "AB"
    //s3 = "ABA"  贪心反例
//        Scanner sc = new Scanner(System.in);
//        String line1 = sc.nextLine();
//        String line2 = sc.nextLine();
//        String line3 = sc.nextLine();
//
//        Deque<Character> dq1 = new ArrayDeque<>();
//        Deque<Character> dq2 = new ArrayDeque<>();
//
//        for (Character c : line1.toCharArray()) {
//            dq1.addLast(c);
//        }
//
//        for (Character c : line2.toCharArray()) {
//            dq2.addLast(c);
//        }
//
//        for (Character c : line3.toCharArray()) {
//            if (c == dq1.peekFirst()) {
//                dq1.removeFirst();
//            } else if (c == dq2.peekFirst()) {
//                dq2.removeFirst();
//            } else {
//                System.out.println("NO");
//                break;
//            }
//        }
//
//        System.out.println("YES");
//    }


    //思路
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String line3 = sc.nextLine();

        int m = line1.length();
        int n = line2.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        //dp[i][j]代表 s1前i个字符[0,i - 1] 和s2前j个字符[0, j - 1]能否组成s3前i + j个字符

        dp[0][0] = true;

        for (int i = 1; i <= m; ++i) {
            dp[i][0] = line1.charAt(i - 1) == line3.charAt(i - 1);
        }

        for (int j = 1; j <= n; ++j) {
            dp[0][j] = line2.charAt(j - 1) == line3.charAt(j - 1);
        }

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                int k = i + j;
                dp[i][j] = ((line1.charAt(i - 1) == line3.charAt(k - 1)) && dp[i - 1][j])
                        || ((line2.charAt(j - 1) == line3.charAt(k - 1)) && dp[i][j - 1]);
                //前面 i-1 和 j 已经能组成 s3 的前 i+j-1 → `dp[i-1][j] == true`
            }
        }

        System.out.println(dp[m][n]);
    }
}
