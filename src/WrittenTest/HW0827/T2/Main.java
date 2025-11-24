package WrittenTest.HW0827.T2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * [L, H] 樱桃直径
 * 分成m个等级，要求是每个等级内的樱桃数目标准差最小
 * 我的想法是首先得知道标准差怎么计算
 * 然后就是如何分组问题，DFS暴力搜索所有可能的分组情况，顺序分组，1一定属于第一组， 每一组至少一个元素
 * 第一个元素只能放在第一组，第二个元素可以放在第一组或者第二组，第三个元素可以放在第一组第二组或者第三组
 * 假设有10个元素，分三组，第10个元素只能是第三组  第9个元素是第二组或者第三组  第8个元素可以是第一组或者第二组或者第三组，这怎么暴力搜索组合呢
 *10 4
 * 16 40 37 20 18 30 18 60 50 37
 * 10个元素分成四组
 * 输出是3 3 2 2
 **/
public class Main {
    private static int m, n;
    private static int[] nums;

    private static List<Integer> bestPar = new ArrayList<>();
    private static Double bestStd = Double.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        String[] parts = br.readLine().split("\\s+");

        nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        dfs(0, 0, new ArrayList<>(), new ArrayList<>());

        for (int num : bestPar) {
            System.out.print(num + " ");
        }

    }

    private static void dfs(int i, int k, List<Integer> Sum, List<Integer> Par) {
        //从[i, n - 1] 划分m个子组  k记录尝试了几次划分了  sum存放已经划分的子组的总和，par存放已经划分的子组的长度
        //[i, n - 1]是还没进行划分的区间

        //边界条件
        if (k == m - 1) {
            int sum = 0;
            for (int j = i; j < n; ++j) {
                sum += nums[j];
            }

            Sum.add(sum); //这里求和可以用前缀和进行优化
            Par.add(n - i);//此时已经把最后一段sum和区间长度加入到中间list中了

            //求平均值stream()
            double avg = Sum.stream().mapToDouble(x -> x).sum() / m;

            double var = 0;
            for (int x : Sum) {
                var += (x - avg) * (x - avg);
            }

            var /= m;

            double std = Math.sqrt(var);  //获得标准差

            if (std < bestStd) {
                bestStd = std;
                bestPar = new ArrayList<>(Par);
            }

            Sum.remove(Sum.size() - 1); //最后一个分组数据在尝试挑战完结果后要删除掉，否则污染别的分组
            Par.remove(Par.size() - 1);

            return;  //找到一个最佳分割组合
        }

        //如果此时不是最后一次分割
        for (int j = i; j < n; ++j) {
            //尝试在j后面分割一下
            ++k;
            Par.add(j - i + 1);
            int sum = 0;
            for (int idx = i; idx <= j; ++idx) {
                sum += nums[idx];
            }
            Sum.add(sum);
            dfs(j + 1, k, Sum, Par);
            Sum.remove(Sum.size() - 1);
            Par.remove(Par.size() - 1);
            --k;
            //回到这里说明分割完毕，放弃这次分割，意思是选中这次分割后所有可能的结果要么已经保存要么没搜到合适的结果
        }
    }
}
