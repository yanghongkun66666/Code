package WrittenTest.RunKe1110.T2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 可以先删掉k个字符，然后实现字符串压缩，返回压缩过后字符串的最小长度，只有一个字符，1不用写，
 * 可以删除[0, k]个字符，达到压缩后字符串长度最小，字符串中只包含大小写字母a-z
 * "aabcccccaaa",0   输出7
 * "aabcccccaaa",1   输出6  删除一个b  a2c5a3
 *"aaabbbaaa",3    输出 2
 */
public class Main {

    private static int res = Integer.MAX_VALUE;
    private static Set<Integer> deleted = new HashSet<>();
    public static void main(String[] args) {
        String ori = "aaabbbaaa";
        int k = 3;
        System.out.println(compressString(ori, k));
    }

    public static int compressString (String s, int k) {
        // write code here
        char[] S = s.toCharArray();
        dfs(S, k, 0);
        return res;
    }

    //原问题：从下标 >= i的位置中，删除k个字符后的最短缩写结果
    private static void dfs(char[] s, int k, int i) {
        //边界条件
        if (k == 0 || i == s.length) {
            res = Math.min(res, getCompressedRes(s, deleted)); //直接获取当前直接压缩的字符串长度
            return;
        }

        for (int j = i; j < s.length; ++j) {
            //从每一个j开始决定是否开始删除k个字符
            //非边界条件,当前下标删还是不删
            if (k > 0) {
                deleted.add(j);
                dfs(s, k - 1, j + 1);
                deleted.remove(j);  //恢复现场
            }

            //走到这里说明当前下标如果删的所有可能情况已经搜索完毕了
            dfs(s, k, j + 1);  //当前下标不删除的所有可能情况
        }


    }

    private static int getCompressedRes(char[] s, Set<Integer> deleted) {
        //删除掉该删除的下标的字符 然后获取压缩后的字符串长度
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; ++i) {
            if (deleted.contains(i)) {
                continue;
            } else {
                sb.append(s[i]);
            }
        }


        char[] cur = sb.toString().toCharArray();
        return getLength(cur);
    }

    private static int getLength(char[] cur) {
        //获取到压缩后的字符串以及字符串长度
        int len = 0;

        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < cur.length) {
            char c = cur[i];
            sb.append(c);
            len = 1;
            ++i;
            while (i < cur.length && cur[i] == cur[i - 1]) {
                ++len;
                ++i;
            }
            //此时i要么越界，要么i指向一个新词
            if (len != 1)
                sb.append(len);

        }

        return sb.length();
    }
}
