package WrittenTest.PAiLab1013.T2;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isYin = true;

        int n = sc.nextInt();
        sc.nextLine();

        String line = sc.nextLine();

        char[] origin = line.toCharArray();
        char[] s = new char[origin.length + 1];

        for (int i = 1; i <= n; ++i) {
            s[i] = origin[i - 1];
        }

        //abcDEF
        //123456

        for (int i = 1; i < s.length; ++i) {
            if (i % 2 == 1) {
                int p = n / 2 - i / 2;
                if (p >= 1 && p <= n) {
                    if (isYin == true && Character.isLowerCase(s[p])) {
                        //阴且为小写字母
                        s[p] = Character.toUpperCase(s[p]);
                        isYin = false;
                    } else if (isYin == false && Character.isUpperCase(s[p])) {
                        //此时阴阳盘为阳
                        s[p] = Character.toLowerCase(s[p]);
                        isYin = true;
                    }
                }
            } else {
                //此时i是偶数
                int p = n / 2 + i / 2;
                if (p >= 1 && p <= n) {
                    if (isYin == true && Character.isLowerCase(s[p])) {
                        //阴且为小写字母
                        s[p] = Character.toUpperCase(s[p]);
                        isYin = false;
                    } else if (isYin == false && Character.isUpperCase(s[p])) {
                        //此时阴阳盘为阳
                        s[p] = Character.toLowerCase(s[p]);
                        isYin = true;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; ++i) {
            sb.append(s[i]);
        }

        System.out.println(sb.toString());
    }
}