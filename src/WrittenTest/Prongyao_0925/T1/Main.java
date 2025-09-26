package WrittenTest.Prongyao_0925.T1;

import java.util.Scanner;

/*
长N米的跑道，每5MIN跑X米，到达终点前每跑X米返回Y米，按照这个规则跑几分钟到达终点
6,3,1  N,X,Y
输出14
5min跑3米   3 / 5   m / min  这是单位速度
然后当curSum = b curSum -= c
0 < X < Y < N  N<=100
跑步到终点花费的时间，结果向上取整  速度要精确到秒吗 一会回来试一下 curSecond % 300 == 0就curDis回退Y  这个只过了80试一下秒是否可行
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.split(",");

        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }
        double N = nums[0], X = nums[1], Y = nums[2];

        double curDis = 0;
        int curMin = 0;
        double v = (double) X / 5; //获取了速度
        while (curDis < N) {
            ++curMin; //要多花一分钟
            curDis += v; //多跑一分钟的距离  强制转换成double了
            if (curDis < N && curMin % 5 == 0) {
                //每跑5min，也就是每跑X米，那么就退后Y米
                curDis -= Y;
            }
        }
        System.out.println(curMin);
    }
}
