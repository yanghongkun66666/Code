package WrittenTest.Prongyao_0925.T2;

import java.util.*;

//这也才只过了一半

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split(" ");

        int n = parts.length;

        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        //dp[i]代表以nums[i]为结尾的最长递减子序列长度
        //递归公式 遍历i之前的每个j，  [0, j] < i 同时统计dp[i] = Math.max(dp[i], dp[j] + 1) 如果nums[j] > nums[i]的话
        //初始化 dp[0] = 1

        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] > nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int resIndex = 0;
        int curMax = 0;
        for (int i = 0; i < n; ++i) {
            if (dp[i] > curMax) {
                curMax = dp[i];
                resIndex = i;
            }
        }

        List<Integer> res = new ArrayList<>(); //用来存放从i到0 的逆序递增序列
        res.add(nums[resIndex]);
        for (int j = resIndex - 1; j >= 0; --j) {
            if (nums[j] > res.get(res.size() - 1)) {
                //把递增的放进结果数组
                res.add(nums[j]);
            }
        }

        Collections.reverse(res);
        for (int i = 0; i < res.size(); ++i) {
            System.out.print(res.get(i));
            if (i != res.size() - 1) {
                System.out.print(" ");
            }

        }
    }
}
