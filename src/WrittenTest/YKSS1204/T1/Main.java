package WrittenTest.YKSS1204.T1;

public class Main {
    //9 .... 9999999999   求和
    public static void main(String[] args) {
        long res = 0;
        long cur = 0;
        for (int i = 0; i < 10; ++i) {
            cur = cur * 10 + 9;
            res += cur;
        }
        System.out.println(res);
    }
}
