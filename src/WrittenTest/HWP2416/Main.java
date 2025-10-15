package WrittenTest.HWP2416;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;

/*
x y z > 0
每个物品可以选择放到每个等级中，
原问题：从下标 >= i的物品中找到分配方案
当前操作：当前i下标放进等级1或者2或者3
子问题：从下标 >= i + 1的物品中找到分配方案
 */
public class Main {
    private static int[] nums;
    private static int n;

    private static  int minRes = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        String[] parts = (br.readLine()).split("\\s+");
        nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        dfs(0, 0, 0, 0);

        System.out.println(minRes == Integer.MAX_VALUE ? 0 : minRes);;

    }

    private static void dfs(int i, int one, int two, int three) {
        //边界条件
        if (i >= n) {
            if (one > two && two > three && three > 0) {
                minRes = Math.min(minRes, one - three);
            }
            return;
        }

        //当前操作i下标物品加入到各个等级
        one += nums[i];
        dfs(i + 1, one, two, three); //子问题，从下标 >= i + 1的物品中构建合适的方案
        one -= nums[i]; //走到这里说明第i个物品加入到第一个等级的所有可能已经搜索完毕了，开始尝试第i个物品加入别的等级

        two += nums[i];
        dfs(i + 1, one, two, three);
        two -= nums[i];

        three += nums[i];
        dfs(i + 1, one, two, three);
        three -= nums[i];

    }


}
