package WrittenTest.YKSS1204.T3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    //拿到长度为n的数组，k次机会 + 1或者 - 1,使得数组出现次数最多的元素出现次数尽可能多,求最大次数
    //5 3
    //6 3 20 8 1   n 10^5  k 10^12
    //ai  10^9   结果是2

    private static int maxSameLenAfterOps(int[] nums, int k) {

//        1. 排序
        Arrays.sort(nums);

        int n = nums.length;

//        2. 前缀和
        int[] s = new int[n + 1];
        for (int i = 1; i < n + 1; ++i) {
            s[i] = s[i - 1] + nums[i - 1];
        } //s[i] = sum[0, i - 1]

//        3. 二分答案
        int left = 1, right = n;
        int ans = 0, len = 0;
        while (left <= right) {
            len = left + (right - left) / 2;

            if (canMakeLen(nums, s, len, k)) {
                ans = len;
                left += 1;
            } else {
                right = len - 1;
            }
        }

        return ans;

    }

    private static boolean canMakeLen(int[] nums, int[] s, int len, int k) {
        int n = nums.length;
        for (int l = 0; l + len - 1 < n; ++l) {
            //遍历长度为len的所有可能连续子数组
            int r = l + len - 1;

            int mid = l + (r - l) / 2;
            int median = nums[mid];

            //[l, mid - 1] 变成midian
            int cntLeft = mid - l;
            int sumLeft = s[mid] - s[l];
            int leftCost = median * cntLeft - sumLeft;

            //[mid + 1, r] 变成median
            int cntRight = r - mid;
            int sumRight = s[r + 1] - s[mid + 1];
            int rightCost = sumRight - median * cntRight;

            int totalCost = leftCost + rightCost;

            if (totalCost <= k) {
                return true;
            }




        }

        return false;
    }
    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        int n = Integer.parseInt(st.nextToken());
//        int k = Integer.parseInt(st.nextToken());
//
//        String[] parts = br.readLine().split("\\s+");
//
//        int[] nums = new int[n];
//
//        for (int i = 0; i < n; ++i) {
//            nums[i] = Integer.parseInt(parts[i]);
//        }
//
//        int len = maxSameLenAfterOps(nums, k);
//
//        System.out.println(len);

        // 用例 1：题目示例
        {
            int[] a = {6, 3, 20, 8, 1};
            int k = 3;
            System.out.println("Case1 = " + maxSameLenAfterOps(a, k)); // 预期：2
        }

        // 用例 2：k = 0，不能改任何数
        {
            int[] a = {1, 2, 3, 4, 5};
            int k = 0;
            System.out.println("Case2 = " + maxSameLenAfterOps(a, k)); // 预期：1
        }

        // 用例 3：适中 k，可以让 3 个数相同
        {
            int[] a = {1, 4, 7, 10};
            int k = 10;
            // 最优：把 [4,7,10] 变成 7 或者 [1,4,7] 变成 4，成本都是 6，长度=3
            System.out.println("Case3 = " + maxSameLenAfterOps(a, k)); // 预期：3
        }

        // 用例 4：已经有很多相同的，k 也不够把其它拉过来
        {
            int[] a = {1, 1, 1, 10, 10, 10};
            int k = 5;
            // 操作不够把一个 10 变成 1（代价 9），所以最多还是 3 个 1
            System.out.println("Case4 = " + maxSameLenAfterOps(a, k)); // 预期：3
        }




    }
}
