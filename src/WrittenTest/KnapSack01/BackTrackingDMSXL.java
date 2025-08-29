package WrittenTest.KnapSack01;

//从前往后递归
public class BackTrackingDMSXL {
    private final int n, capacity;  //物品数量和背包容量
    private final int[] w, v;  //每个物品的重量和价值

    private int bestValue;


    public BackTrackingDMSXL(int[] w, int[] v, int capacity) {
        this.n = w.length;
        this.w = w.clone();
        this.v = v.clone();
        this.capacity = capacity;
    }


    //dfs代表从index下标（包括index）向后的物品中能装满curW背包的最大价值
    private void dfs(int index, int curW, int curV) {
        //终止条件
        if (index == n) {
            if (curV > bestValue) {
                bestValue = curV;
            }
            return;
        }

        //非边界条件
        //不选第i件
        dfs(index + 1, curW, curV);

        //选第i件，走到这里说明不选第index件的后续所有可能已经走完了，同时该记录的结果也记录过了
        if (curW + w[index] <= capacity) {
            dfs(index + 1, curW + w[index], curV + v[index]);
        }

    }

    public int solve() {
        dfs(0, 0, 0);
        return bestValue;
    }
}
