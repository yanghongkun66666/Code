package WrittenTest.Pdidi0927.T1;

import java.util.Scanner;

/**
 * 输入 ab代表考虑的进制区间
 * lr表示考虑的十进制数字区间
 * k表示要求有几个进制是波浪数才满足要求，记录答案
 * 输出从小到大，然后每一行一个数字
 * ab [2, 32]  32进制
 * 10进制 0 1 2 3 4 5 6 7 8 9
 * 2进制 0 1
 * 3进制 0 1 2
 * 11禁止0 1 2 3 4 5 6 7 8 9 A
 * 12进制0 1 2 3 4 5 6 7 8 9 A B
 * 13
 * 14
 * 15
 * 16进制0 1 2 3 4 5 6 7 8 9 A B C D E F
 * 32进制0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K L M N O P Q R S T U V
 * 10进制转2进制  10
 * 1010    101
 * 直接在转为x进制过程中判断是否是波浪数就好 波浪数 121212两个数交替出现
 */
public class Main {

//    private static char[] allJin = {'0','1',2, 3, 4, 5, 6, 7, 8, 9 ,A, B C D E F G H I J K L M N O P Q R S T U V};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] parts = sc.nextLine().split(" ");
        int a, b, l, r, k;
        a = Integer.parseInt(parts[0]);
        b = Integer.parseInt(parts[1]);
        l = Integer.parseInt(parts[2]);
        r = Integer.parseInt(parts[3]);
        k = Integer.parseInt(parts[4]);

        for (int num = l; num <= r; ++num) {
            //最外层遍历数字，每个数字有个cnt来判断有几个进制区间是波浪数
            int cnt = 0;

            if (num == 1) {
                System.out.println(1); //特别规定1是波浪数
                continue;
            }
            for (int jin = a; jin <= b; ++jin) {  //循环遍历进制区间
                if (isValidWave(num, jin)) {
                    ++cnt;
                }
            }

            if (cnt == k) {
                System.out.println(num);
            }
        }


    }

    private static boolean isValidWave(int num, int jin) {
        //判断num在这个进制下是否是波浪数
        StringBuilder sb = new StringBuilder();
//        char flag1 = '$', flag2 = '$';
        while (num != 0) {
            int to_append_num = num % jin;
            char to_append;
            if (to_append_num >= 10) {
                to_append = (char) (to_append_num - 10 + 'A');
            } else {
                to_append = (char)(num % jin);
            }

            sb.append(to_append);
            num /= jin;
        }

        if (sb.length() > 2) {
            char flag1 = sb.charAt(0);
            char flag2 = sb.charAt(1);
            char[] to_judge = sb.toString().toCharArray();
            for (int i = 2; i < to_judge.length; ++i) {
                if (to_judge[i] != flag2 && to_judge[i] != flag1) {
                    return false;
                } else if (to_judge[i] == flag2 && to_judge[i - 1] != flag1){
                    return false;
                } else if (to_judge[i] == flag1 && to_judge[i - 1] != flag2) {
                    return false;
                }
            }
            //for循环完了没返回false就是波浪数
        } else {
            //1个字符或者两个字符就是波浪数
            return true;
        }

        return true;
    }


    private static boolean isValidWave2(int num, int jin) {
        //判断num在这个进制下是否是波浪数
        StringBuilder sb = new StringBuilder();
        char flag1 = '$', flag2 = '$';
        while (num != 0) {
            int to_append_num = num % jin;
            char prev = '$';
            if (to_append_num >= 10) {
                if (flag1 == '$') {
                    flag1 = (char) (to_append_num - 10 + 'A');
                } else if (flag2 == '$') {
                    flag2 = (char) (to_append_num - 10 + 'A');
                }
                if (to_append_num == flag1) {
                    if (prev != '$' && prev == to_append_num) {
                        return false;
                    }
                    prev = flag1;
                } else if (to_append_num == flag2) {
                    if (prev != '$' && prev == to_append_num) {
                        return false;
                    }
                    prev = flag2;
                }

            } else {
                if (flag1 == '$') {
                    flag1 = (char)(num % jin);
                } else if (flag2 == '$') {
                    flag2 = (char)(num % jin);
                }

                if (num == flag1) {
                    if (prev != '$' && prev == num) {
                        return false;
                    }
                    prev = flag1;
                } else if (num == flag2) {
                    if (prev != '$' && prev == num) {
                        return false;
                    }
                    prev = flag2;
                }

            }

        }



        return true;
    }

}
