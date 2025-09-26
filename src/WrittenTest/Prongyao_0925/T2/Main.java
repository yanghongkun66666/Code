package WrittenTest.Prongyao_0925.T2;

import java.util.Scanner;

/*
输出最长递减子列表   9 5 4 3 2 5 4 3 2
如果只有一个元素直接返回这个元素
输出 9 5 4 3 2
resL  resR 记录我们找到的目前最长的递减子序列的左右边界下标，闭区间
双指针，初始化left为 left指向第一个递减序列第一个值，right = left + 1 向右遍历，只要right当前元素比前一个元素小，那就一直++
如果right位置元素比前一个元素大，此时开始尝试挑战结果 竟然只过了25  难道是 9 6 5 4 3 2 5 4 3 2 1  会输出9 6 5 4 3 2 1
可以试一下动态规划思路？ dp[i]代表以nums[i]结尾的的最长递减子序列  如果变长了同时记录下当前right 找到最大的dp 然后从那个位置单调队列往回走，
倒着向前找，只有比队尾大的元素才能入队，作为结果中的一员，一定能找到结果，只不过得reverse一下队列
单调队列？5 4 3 2 9 6 5 4 3单调队列好像不行
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split(" ");

        int n = parts.length;

        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        int resL = 0, resR = 0, maxLength = 1;

        int left = 0;
        for (int right = left + 1; right < n; ++right) {
            if (nums[right] < nums[right - 1]) {
                continue;
            }

            //此时nums[right] >= nums[right - 1] 应该开始统计答案了，此时left到right - 1为递减子序列
            if (right - 1 - left + 1 >= maxLength) {
                //如果长度一致就保留最后一个递减子序列
                maxLength = right - 1 - left + 1;
                resL = left;
                resR = right - 1;
            }
            left = right; //把left放在这个比较大的元素位置，代表下一个递减子序列的开头
        }

        for (int i = resL; i <= resR; ++i) {
            System.out.print(nums[i]);
            if (i != resR) {
                System.out.print(" ");
            }
        }
    }
}
