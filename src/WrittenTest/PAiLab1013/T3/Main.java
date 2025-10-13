package WrittenTest.PAiLab1013.T3;

import java.util.Scanner;

import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        //第一次到达才获得糖果，记录是否到达过
        Set<Integer> set = new HashSet<>();

        int n = sc.nextInt(), k = sc.nextInt();
        sc.nextLine();

        String instructions = sc.nextLine();

        int cur = 0;
        long res = 0;
        char[] s = instructions.toCharArray();

        int cntL = 0;
        int cntR = 0;
        int maxL = 0;
        int maxR = 0;
            for (char c : s) {
                if (c == 'R') {
                    ++cur;
                    maxR = Math.max(maxR, cur);

                    ++cntR;
                } else {
                    --cur;
                    maxL = Math.min(maxL, cur);
                    ++cntL;
                }

                if (set.contains(cur)) {
                    continue;
                } else {
                    res += cur;
                    set.add(cur);
                }
                //第一轮算完结果后，每次都是基于能涉及的范围多向左或者向右多走R - L步

        }

            //[maxL, maxR]

        if (cntL > cntR) {
            //每次多往左走几步
            int stepL = cntL - cntR;
            for (int i = 0; i < k - 1; ++i) {
                cur = maxL;
                for (int j = 0;j < stepL; ++j) {
                    --cur;
                    res += Math.abs(cur);
                }
            }
        } else if (cntR > cntL){
            int stepR = cntR - cntL;
            for (int i = 0; i < k - 1; ++i) {
                cur = maxR;
                for (int j = 0;j < stepR; ++j) {
                    ++cur;
                    res += Math.abs(cur);
                }
            }
        }  else {
            System.out.println(res);
            return;
        }







        System.out.println(res);
    }
}