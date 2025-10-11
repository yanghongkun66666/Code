package WrittenTest.PHengsheng1010.T2;

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //前缀积
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
        }

        long[] multi = new long[n + 1];
        multi[0] = 1;
        for (int i = 1; i <= n; ++i) {
            multi[i] = multi[i - 1] * nums[i - 1]; //[0, i - 1) 前缀乘积
        }

        int res = 0;

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j <= n; ++j) {
                long to_judge = multi[j] / multi[i];
                if (to_judge == 1) continue;
//                if (j - i > res && isValid(to_judge) == true) {
//                    res = Math.max(res, j - i);
//                }
                double mul = Math.sqrt((double)(to_judge));
                if (mul * mul == (double) (to_judge)) {
                    if (j - i > res) {
                        res = Math.max(res, j - i);
                    }
                }

            }
        }

        if (res == 0) {
            System.out.println(-1);
        } else {
            System.out.println(res);
        }
    }

    private static boolean isValid(long to_judge) {
        for (long i = 1; i < to_judge / 2; ++i) {
            if (i * i == to_judge) {
                return true;
            }
        }

        return false;
    }
}
