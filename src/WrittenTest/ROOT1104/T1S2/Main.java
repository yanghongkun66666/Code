package WrittenTest.ROOT1104.T1S2;
/**
 * n个元素，从中选择k个元素做&运算，求出所有可能方案中的最小&运算结果
 * 暴力搜索 + 剪枝
 * 剪枝1：剩下元素不够选，直接返回
 * 剪枝2：当前&的结果知已经比最终结果值小了，可以提前赋值给结果，如果&的过程中已经出现了0，此时可以直接返回了
 */
public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{7, 3, 11};
        int k = 2;  //从arr中选择k个数，最小的&值
        System.out.println("最小的按位与结果为: " + findMinAnd(arr, k));
    }

    private static int findMinAnd(int[] arr, int k) {
        int n = arr.length;
        int minAnd = Integer.MAX_VALUE;

        //遍历所有子集，mask表示选中了哪些元素的组合
        for (int mask = 0; mask < (1 << n); ++mask) {
            //判断当前子集选中了几个元素
            if (Integer.bitCount(mask) == k) {
                int andValue = -1;
                for (int i = 0; i < n; ++i) {
                    if ((mask & (1 << i)) != 0) {  //如果第i位 ！= 0，相当于选中了arr[i]   另外能加括号就加括号
                        if (andValue == -1) {
                            andValue = arr[i];
                        } else {
                            andValue &= arr[i];
                        }
                    }
                }

                minAnd = Math.min(minAnd, andValue);
                if (minAnd == 0) return 0; // ✅ 剪枝：不可能更小
            }
        }

        return minAnd;
    }
}
