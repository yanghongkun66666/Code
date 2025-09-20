package WrittenTest.P360_0920.T2;

import java.util.Scanner;

public class Main {
    /**
     * 先获取除去某个元素的前缀最长递增子序列长度和后缀最长递增子序列长度列表
     * 然后开头结尾元素特判，看结合后缀最长递增子序列长度/前缀最长自增子序列长度能否继续增长
     * 中间元素 下标从1....n - 2的元素  需要结合pre 和 after数组结合判断，如果当前下标后一个位置元素 - 当前下标前一个位置元素 > 1此时
     * 盘古就可以发力连接起两个递增链接
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- != 0) {
            int res = 0;
            int n = sc.nextInt(); //这是当前组元素个数
            if (n == 1) {
                System.out.println(1);
                continue;
            }
            int[] nums = new int[n];
            for (int i = 0; i < n; ++i) {
                nums[i] = sc.nextInt();
            }

            int[] pre = new int[n];
            int[] after = new int[n];  //这两个代表除去当前元素的前缀递增序列最长与后缀递增序列最长
            for (int i = 0; i < n; ++i) {
                if (i == 0) {
                    pre[0] = 0;
                    int left = i + 1;
                    int right;
                    for (right = left + 1; right < n; ++right) {
                        if (nums[right] <= nums[right - 1]) {
                            break;
                        }
                    }
                    after[0] = right - left;
                    //此时这个right已经不满足了，我们要的区间是[left, right - 1]  区间长度是right - 1 - left + 1
                    //也就是right - left
                } else if (i == n - 1) {
                    after[n - 1] = 0;
                    int right = i - 1;
                    int left;
                    for (left = right - 1; left >= 0; --left) {
                        if (nums[left] >= nums[left + 1]) {
                            break;
                        }
                    }
                    pre[n - 1] = right - left;
                    //此时这个right已经不满足了，我们要的区间是[left + 1, right]  区间长度是right - (left + 1) + 1
                    //也就是right - left
                } else {
                    //此时就是[1, n -2]的逻辑了 除去某个值的前后缀最大值分别计算
                    // 先计算前缀递减序列长度
                    int right = i - 1;
                    int left;
                    for (left = right - 1; left >= 0; --left) {
                        if (nums[left] >= nums[left + 1]) { //前一个必须严格小于后一个
                            break;
                        }
                    }
                    pre[i] = right - left;

                    left = i + 1;
                    for (right = left + 1; right < n; ++right) {
                        if (nums[right] <= nums[right - 1]) { //后一个必须严格大于前一个
                            break;
                        }
                    }
                    after[i] = right - left;
                }
            }

            //此时已经获取到前后缀最长连续序列长度

            //特判开头结尾  结尾好像不用特判
            if (nums[1] > 0) {
                res = Math.max(res, after[0] + 1);
            }

            res = Math.max(pre[n - 1] + 1, res);  //这就是对结尾的特判

            //对中间元素的处理
            for (int i = 1; i <= n - 2; ++i) {
                if (nums[i + 1] - nums[i - 1] > 1) {
                    //此时可以发力把前后缀最长连续自增子序列接起来
                    res = Math.max(res, pre[i] + after[i] + 1);
                } else if (nums[i + 1] > 0) {
                    res = Math.max(res, pre[i] + 1);
                    res = Math.max(res, after[i] + 1);
                }
            }

            System.out.println(res);



        }
    }
}
