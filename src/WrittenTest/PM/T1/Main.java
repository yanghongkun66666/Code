package WrittenTest.PM.T1;

import java.util.Scanner;

/*
长度n的，大小写字符串s， i从0开始
计数器x，x == 1初始化
依次对下标i执行操作，
如果i + x <= n - 1  si + x == 大写字母 修改为小写字母 x++ 不满足以上条件不修改
同时如果si 是小写，则si改成大写

 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String line = sc.nextLine();

        char[] s = line.toCharArray();

        String res = operate(s);
        System.out.println(res);
    }

    private static String operate(char[] s) {
        int n = s.length;
        int x = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i + x < n) {
                if (s[i + x] >= 'A' && s[i + x] <= 'Z') {
                    s[i + x] = Character.toLowerCase(s[i + x]);
                    if (Character.isLowerCase(s[i])) {
                        s[i] = Character.toUpperCase(s[i]);
                    }
                    ++x;
                }
            }
            sb.append(s[i]);
        }



        return sb.toString();
    }
}
