package WrittenTest.P360_0920.T1;

import javax.print.DocFlavor;
import java.util.*;

public class Main {
    /**
     * 一个数组划分为k个连续序列，求最大的权重，权重计算是基于每个序列中不重复元素的和
     * 回溯枚举每个位置划分或者不划分
     * 5 2
     * 1 2 2 3 1
     * [1 2]  [2 3 1] = 9
     * @param args
     */
    private static int res = 0;
    private static int k = 0;

    private static List<Integer> path = new ArrayList<>(); //存放分割点
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(); //元素数目
        k = sc.nextInt(); //分段个数

        int[] nums = new int[m];
        for (int i = 0; i < m; ++i) {
            nums[i] = sc.nextInt();
        }

        dfs(nums, 0, k - 1); //从0号下标开始分割，剩余分割次数k - 1次

        System.out.println(res);
    }

    private static void dfs(int[] nums, int i, int times) {
        //剩余分割次数是0次，那么此时就挑战一下结果
        if (times == 0) {
            path.add(nums.length - 1); //方便我们循环统计结果
            //(]path代表的区间
            //挑战一下res  根据path来计算总权重值
            int tmpRes = 0;
            Set<Integer> tmpSet = new HashSet<>();
            for (int j = 0; j < path.size(); ++j) {
                tmpSet.clear();
                if (j == 0) {
                    for (int k = 0; k <= path.get(j); ++k) {
                        tmpSet.add(nums[k]);
                    }

                    for (int num : tmpSet) {
                        tmpRes += num;
                    }//获取到这个区间的权重

                } else {
                    for (int k = path.get(j - 1) + 1; k <= path.get(j); ++k) {
                        tmpSet.add(nums[k]);
                    }

                    for (int num : tmpSet) {
                        tmpRes += num;
                    }
                }

                if (tmpRes > res) {
                    res = tmpRes;
                }
            }

            path.remove(path.size() - 1);
            return;
        }

        //非边界条件 尝试在每个位置进行分割 只要还有分割次数
        for (int j = i; j < nums.length; ++j) {
            if (times > 0) {
                path.add(j);
                --times;
                dfs(nums, j + 1, times);
                ++times;
                path.remove(path.size() - 1);
            }
        }

    }
}
