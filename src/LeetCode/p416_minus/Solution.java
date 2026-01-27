package LeetCode.p416_minus;

public class Solution {

    //自定义compare函数
    // 返回 1: a>b, 0: a==b, -1: a<b
    private int compare(String s1, String s2) {


        if (s1.length() != s2.length()) {
            return s1.length() > s2.length() ? 1 : -1;
        }

        int r = s1.compareTo(s2);

        return r == 0 ? 0 : (r > 0 ? 1 : -1);  //r > 0代表compareto s1 大于 s2
    }
    public String minus(String str1, String str2) {
        //甚至不需要自定义compare函数
        if (str1.equals(str2) == true) {
            return "0";
        }

        char sign = '+';
        if (compare(str1, str2) == -1) {
            sign = '-';
            String tmp = str1;
            str1 = str2;
            str2 = tmp;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int i = s1.length - 1;
        int j = s2.length - 1;

        int borrow = 0;
        StringBuilder sb = new StringBuilder();

        while (i >= 0 || j >= 0) {
            int x = (i >= 0) ? s1[i] - '0' : 0;
            int y = (j >= 0) ? s2[j] - '0' : 0;

            x -= borrow;

            if (x < y) {
                //向高位借一位
                x += 10;
                borrow = 1;
            } else {
                borrow = 0;  //无需借位
            }

            int digit = x - y;

            sb.append((char) (digit + '0'));

            --i;
            --j;
        }

        sb.reverse();


        //去除前导零
        int idx = 0;

        while (idx < sb.length()  && sb.charAt(idx) == '0') {
            ++idx;
        }

        String res = sb.substring(idx);





        return sign == '-' ? '-' + res : res;
    }
}
