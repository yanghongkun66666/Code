package WrittenTest.PXiecheng.T1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/*
一个字符就是好且不错
满足两个相邻字符一个大写一个小写就是好
满足只反转一次就变成好串的字符串称之为不错
5
1
a
2
Aa
2
AA
3
abc
4
aBCd

main2为什么是对的，main1为什么不对呢？
2 4
看还有什么别的解决的可能吗，通过了10%
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int good = 0;
        int as_well = 0;
        while (T-- != 0) {
            int n = sc.nextInt();
            sc.nextLine();
            String to_judge = sc.nextLine();
            if (judge(to_judge) == 0) {
                //代表好串
                good++;
                as_well++;
            } else if (judge(to_judge) == 1) {
                as_well++;
            }
        }

        System.out.println(good + " " + as_well);
    }

    private static int judge(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n == 1) {
            return 0; //代表是好串
        }

        int crossNum = 0;

        Deque<Character> stack = new ArrayDeque<>();

        stack.addFirst(s[0]);
        for (int i = 1; i < n; ++i) {
            if (stack.isEmpty() == true) {
                stack.addFirst(s[i]);
            } else {
                if (s[i] >= 'A' && s[i] <= 'Z' && stack.getFirst() >= 'A' && stack.getFirst() <= 'Z') {
                    stack.removeFirst();
                    ++crossNum;
                    if (crossNum > 1) {
                        return -1;
                    }
                } else if (s[i] >= 'a' && s[i] <= 'z' && stack.getFirst() >= 'a' && stack.getFirst() <= 'z') {
                    stack.removeFirst();
                    ++crossNum;
                    if (crossNum > 1) {
                        return -1;
                    }
                } else {
                    //符号不同
                    stack.addFirst(s[i]);
                }
            }
        }





        return crossNum;
    }
}
