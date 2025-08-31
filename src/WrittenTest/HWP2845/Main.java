package WrittenTest.HWP2845;

import java.util.Scanner;

public class Main {
    static int m, n;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        //测试用例掩码
        int[] test = new int[m];  //m个测试用例，n个模块

        //将每个测试用例转化成位掩码表示
        for (int i = 0; i < m; ++i) {
            int mask = 0;
            for (int j = 0; j < n; ++j) {
                int x = sc.nextInt();
                if (x == 1) {
                    mask |= (1 << j);
                }
            }
            test[i] = mask;
        }

        //目标掩码，所有模块都被覆盖
        int target = (1 << n) - 1;
        int res = Integer.MAX_VALUE;

        //二进制枚举
        for (int s = 0; s < (1 << m); ++s) {
            int unionMask = 0;
            int cnt = 0;

            //遍历s选中的子集中每个测试用例，将其覆盖情况合并
            for (int i = 0; i < m; ++i) {
                if ((s & (1 << i)) != 0) {
                    //此时假设i是0  1 << i 还是1  选中了第一个测试用例 test[i]
                    unionMask |= test[i];
                    cnt++;
                }
            }

            if (target == unionMask) {
                res = Math.min(res, cnt);
            }
        }

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }
}
