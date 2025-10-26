package WrittenTest.sqrtBinary;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double n = sc.nextDouble();  // 输入一个正数
        double ans = sqrt(n);
        System.out.printf("%.5f\n", ans);  // 保留5位小数
    }

    public static double sqrt(double x) {
        if (x < 0) throw new IllegalArgumentException("负数没有实数平方根");
        if (x == 0 || x == 1) return x;

        double left = 0, right = x;
        if (x < 1) right = 1;  // 处理小于1的情况

        double mid;
        while (right - left > 1e-6) { // 控制精度
            mid = (left + right) / 2;
            if (mid * mid > x) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left; // 或 (left + right) / 2，差别极小
    }
}
