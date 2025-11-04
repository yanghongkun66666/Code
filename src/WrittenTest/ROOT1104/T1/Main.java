package WrittenTest.ROOT1104.T1;

/**
 * n个元素，从中选择k个元素做&运算，求出所有可能方案中的最小&运算结果
 * 暴力搜索 + 剪枝
 * 剪枝1：剩下元素不够选，直接返回
 * 剪枝2：当前&的结果知已经比最终结果值小了，可以提前赋值给结果，如果&的过程中已经出现了0，此时可以直接返回了
 */
public class Main {

    private static int minAnd = Integer.MAX_VALUE;
    public static void main(String[] args) {
        int[] arr = new int[]{7, 3, 11};
        int k = 2;  //从arr中选择k个数，最小的&值
        System.out.println("最小的按位与结果为: " + findMinAnd(arr, k));
    }

    private static int findMinAnd(int[] arr, int k) {
        dfs(arr, k, 0, 0, -1);
        return minAnd;
    }

    //原问题：从当前arr中找出最小and值
    private static void dfs(int[] arr, int k, int count, int index, int curAnd) {
        if (count == k) {
            minAnd = Math.min(curAnd, minAnd);
            return; //边界条件，找到了k个合适的元素
        }

        //剩下元素不够凑齐k个元素，因此可以提前退出，不用计算下去
        if (arr.length - index < k - count) {
            return;
        }

        //非边界条件
        for (int i = index; i < arr.length; ++i) {
            //从j >= i中选一个加入到结果集中
            int oldAnd = curAnd;
            if (count == 0) curAnd = arr[i];
            curAnd &= arr[i];
            ++count;

            //剪枝二：如果当前and的和已经足够小
            if (curAnd < minAnd) {
                minAnd = curAnd;
                if (curAnd == 0) return;  //0已经是最小的and和了  当前分支搜索到这里就算可以了，返回开始搜索别的分支，返回之后回到上一层dfs后自会恢复现场
            }

            dfs(arr, k, count, i + 1, curAnd);  //子问题：从 >= index位置开始构造最终结果
            --count;
            curAnd = oldAnd; //恢复现场，下面有另一个写法，传递给dfs的是nextAnd，当前计算用的都是curAnd
            /*
            for (int i = index; i < arr.length; ++i) {
                int nextAnd = (count == 0) ? arr[i] : (curAnd & arr[i]);
                dfs(arr, k, count + 1, i + 1, nextAnd);
            }
             */


        }
    }
}
