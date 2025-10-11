package WrittenTest.PXiecheng.T4;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
给定长度为n的数组 nums = {a1, a2, ...., an}
[l, r] 闭区间 满足 [1, l, r, n]
定义贡献为满足 l < i < r 同时 ai < al  || ai < ar的下标i的个数
计算所有区间贡献之和
1. 找出所有区间
2. 计算单个区间贡献和


优化手段 记忆化？ 记录下某个区间l和r 然后计算新区间基于老区间添加了哪些数据，就快速获取新区间贡献
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());

        while (T-- != 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int[] nums = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; ++i) {
                nums[i] = Integer.parseInt(st.nextToken());
            }

            System.out.println(solution(nums));
        }

    }

    private static int solution(int[] nums) {
        //至少要有三个数
        int n = nums.length;
        int totalRes = 0;
        for (int l = 0; l < n - 2; ++l) {
            int tmpRes = 0;
            for (int r = l + 2; r < n; ++r) {
                for (int i = l + 1; i < r; ++i) {
                    if (nums[i] < nums[l] || nums[i] < nums[r]) {
                        ++tmpRes;
                    }
                }
            }
            totalRes += tmpRes;
        }
        return totalRes;
    }
}
