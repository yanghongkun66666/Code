//package WrittenTest.PXiecheng.T3;
//
//import java.util.Scanner;
//
///*
//二进制表示中，1和0的个数相等 则称之为完美数
//给一个x 找出 >= x的最小完美数
//过了 20%
// */
//public class Main2 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int T = sc.nextInt();
//        while (T-- != 0) {
//            int x = sc.nextInt();
//            System.out.println(perfect(x));
//        }
//    }
//
//    private static int perfect(int x) {
//        //可以获取到二进制串，然后如果0比1多，就倒序依次把0改成1，然后转回十进制，如果1比0多  不知道怎么优化，暴力查找吧
//        //二进制 [低， 高] 比如4的二进制  001 sb.append
//        int[] cnt = getCnt(x);
//        String binary = getBinary(x); // 4  -> 001
//        return res;
//    }
//
//    private static String getBinary(int num) {
//        StringBuilder sb = new StringBuilder();
//        while (num != 0) {
//            if (num % 2 == 0) {
//                sb.append(0);
//            } else {
//                sb.append(1);
//            }
//
//            num /= 2;
//        }
//        return sb.toString();
//    }
//
//    private static int[] getCnt(int num) {
//        int cnt0 = 0;
//        int cnt1 = 0;
//        while (num != 0) {
//            if (num % 2 == 0) {
//                cnt0++;
//            } else {
//                cnt1++;
//            }
//
//            num /= 2;
//        }
//
//        return new int[]{cnt0, cnt1};
//    }
//}
