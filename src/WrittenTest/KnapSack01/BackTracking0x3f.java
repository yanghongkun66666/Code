package WrittenTest.KnapSack01;

import java.util.Arrays;
import java.util.List;

/**
 * 回溯开始思考，第i个物品，不选，容量不变，选，容量变少  确定了递归函数中的i和c
 *
 * 回溯三问：
 *
 * - 原问题：从前i个物品中得到的最大价值和 （以此来定义dfs函数）
 * - 当前操作，枚举第i个物品选还是不选
 * - 子问题：
 *   - 剩余容量为c时候，从前i - 1个物品中得到的最大价值和
 *   - 剩余容量为c - w[i]的时候，从前i - 1个物品中得到的最大价值和
 */
public class BackTracking0x3f {
    //从后往前递归
    private final int n, capacity;  //物品个数，背包容量
    private final int[] w, v; //每个物品的重量，每个物品的价值

    private final int[][] memo;  //备忘录 memo[i][c] = dfs(i, c)


    public BackTracking0x3f(int[] weights, int[] values, int capacity) {
        this.n = weights.length;
        this.capacity = capacity;
        this.w = weights.clone();
        this.v = values.clone();
        this.memo = new int[n + 1][capacity + 1];

        for (int i = 0; i <= n; ++i) {
            Arrays.fill(memo[i], -1); //标记为未被计算过
        }

    }

    //核心 dfs(i, c) 表示从前i个物品中，容量为c的情况下能取得的最大价值
    //    //dfs(i, c)：从 前 i 个物品里（也就是物品下标 [0..i-1]），在容量为 c 的情况下能取得的最大价值。
    private int dfs(int i, int c) {
        if (i == 0 || c == 0) {
            //边界条件，没有物品，背包没有容量，最大价值就是0
            return 0;
        }
        if (memo[i][c] != -1) return memo[i][c]; //计算过前i个物品，容量为c的最大价值 不重复计算

        //非边界条件 不选第i个
        int notTake = dfs(i - 1, c);
        // 选第 i 个（仅当放得下时；使用 i-1 访问 w/v）
        int take = notTake; // 先赋个默认值
        if (c >= w[i - 1]) {
            take = Math.max(take, dfs(i - 1, c - w[i - 1]) + v[i - 1]);
        }

        return memo[i][c] = Math.max(notTake, take);
    }

    public int solve() {
        return dfs(n, capacity);
    }
}
