package WrittenTest.HWP2336;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
枚举所有可能的乘客组合数，只要某个站人数超过m就跳过这个组合，枚举每个状态维护一个对应状态的结果，挑战一下最终结果
暴力模拟所有情况，使用二进制枚举，0和1代表乘客不乘坐和乘坐两种情况，用一个数字代表所有乘客是否乘坐，枚举这个数字的二进制位
尝试每一种可能的乘客组合
对于每个乘客组合，记录每个站有多少乘客上车
某个站乘客数量超过m，则此组合不符合条件，直接跳过
否则计算该组合座位利用数（统计完所有站点才知道是不是超过了m人）才知道当前组合是否合适 也就是所有被选择乘客下车站点-上车站点的总和
对每一种符合条件的组合，更新最大座位利用数

 */


public class Main {
    private static class Pair{
        int x, y; //代表当前乘客上下车点
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int finalRes = 0;

        StringTokenizer st = new StringTokenizer(line);
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        List<Pair> memo = new ArrayList<>();

        for (int i = 0; i < x; ++i) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            memo.add(new Pair(from, to));
        }

        for (int i = 0; i < (1 << x); ++i) {
            //遍历所有可能的组合，i == 0代表没有顾客上车  i == 2^x - 1代表所有顾客上车
            int[] cnt = new int[n]; //一共有n站，看当前组合每一站上车人数是否大于m
            boolean isvalid = true;
            int res = 0; // 当前组合的最大座位利用数

            //如何进行二进制枚举呢？
//            for (int j = 0; j < )  遍历所有乘客，看当前组合这位乘客坐还是不坐
            for (int j = 0; isvalid && j < x; ++j) {
                if (((i >> j) & 1) == 1) {
                    //说明第j个乘客上车了
                    for (int k = memo.get(j).x; isvalid && k < memo.get(j).y; ++k) {
                        //把第j位在车上的乘客的乘车区间记录到cnt里，方便后续统计每个站是否超过了座位限制
                        cnt[k]++;
                    }

                    res += memo.get(j).y - memo.get(j).x;
                }
            }

            for (int j = 0; j < n; ++j) {
                if (cnt[j] > m) {
                    isvalid = false;
                }
            }

            if (isvalid == true) {
                finalRes = Math.max(res, finalRes);
            }
        }
        System.out.println(finalRes);
    }
}
