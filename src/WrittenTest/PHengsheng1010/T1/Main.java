package WrittenTest.PHengsheng1010.T1;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int ans = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();

        int lastGuess = -1;
        int countAsk = 0;
        int countGuess = 0;
        sc.nextLine(); //清楚换行
        for (int i = 0; i < n; ++i) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if ("?".equals(parts[0]) == true) {
                ++countAsk;
            } else {
                ++countGuess;
                 lastGuess = Integer.parseInt(parts[1]);
            }
        }

        if (countAsk > a || countGuess > b || lastGuess != ans) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }
}