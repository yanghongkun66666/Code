package WrittenTest.PM.T2;

import java.util.Scanner;

/*
定义一个好的数，仅仅包含一个非零有效位  写一个函数判断一下是不是好的
长度为n的正整数数组 对数组元素执行若干次以下三种操作中的一种
选择一个索引，将对应值 + 1
选择一个索引，如果对应值 > 1，则对应值 - 1
选择一个索引，如果对应值是10的倍数，就除10，否则不做任何操作

如果当前数不是好数，就看是+还是-能让最低位变成0最快，然后操作完成判断是不是好数，
如果还不是，直接除10  然后再判断是不是好数，再看是+还是-能让最低位变成0最快 然后判断是不是好数

 */
public class Main {
    private static int totalRes = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
            totalRes += minToGood(nums[i]);
        }
        System.out.println(totalRes);
    }

    private static int minToGood(int x) {
        int steps = 0;
        if (x == 0) return 1;
        if (x < 0) {
            steps = Fu(x);
            return steps;
        }
        //此时x > 0
        while (isGood(x) == false) {
            int stepsToTen = stepsToUseTen(x);
            steps += stepsToTen;
            if (stepsToTen == 5) {
                x -= stepsToTen;
            } else if (x + stepsToTen % 10 == 0) {
                x += stepsToTen;
            } else {
                x -= stepsToTen;
            }
            if (isGood(x)) break;
            steps++;
            x /= 10;
        }

        return steps;
    }

    private static int Fu(int x) {
        int steps = 0;
        while (x != 0) {
            steps += stepsToUseTen(x); //此时变成10的倍数，但是还是负数，除10更快接近0
            steps++;
            x /= 10;
        }

        return steps + 1;  //最后x == 0  x + 1 作为最终结果
    }

    private static int stepsToUseTen (int x) {
        //如果是好数就不需要进入这个函数
        //这是看走到最近的十的倍数需要几步
        int tmp = Math.abs(x) % 10;
        if (tmp > 5) {
            return 10 - tmp;
        }

        return tmp;
    }


    private static boolean isGood(int x) {
        if (x < 0) return false;
        if (x == 0) return false;
        int cnt = 0;
        while (x != 0) {
            int tmp = x % 10;
            if (tmp != 0) {
                ++cnt;
                if (cnt > 1) {
                    return false;
                }
            }

            x /= 10;
        }

        return cnt == 1;
    }
}
