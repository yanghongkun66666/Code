package WrittenTest.Prongyao_0925.T3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//用一个集合判断是否合法，如果合法就先通过set判断是否重复，不重复就加入到合法list中
//不合法就直接加入到不合法list中  因此要写一个辅助函数判断是否合法
//输出一下合法字符串，然后输出空格分割  完成后输出换行
//新的一行输出非法字符串，然后空格分割
//写一个辅助函数能够将字符串左移10位，对每个合法的都左移一下，然后加入到一个新的list中，然后移动完了就输出一下
//然后复制一个新list 基于前一个自定义排序，ascall码从小到大，排序完输出一下
//对于合法字符串进行ascll码排序就是自定义sort，对list进行排序 多个空格当成一个空格需要额外处理一下这个问题 "DEF\t"  "abc123     " 空格当作定界符，连续空字符串当成定界符
public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Set<String> s = new HashSet<>();
        List<String> validStr = new ArrayList<>();
        List<String> unvalidStr = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.length() == 0) {
                break;
            }
            if (isValidString(line) && s.contains(line) == false) {
                validStr.add(line);
                s.add(line);
            } else {
                unvalidStr.add(line);
            }

            st = new StringTokenizer(br.readLine());
        }

        for (int i = 0; i < validStr.size(); ++i) {
            System.out.print(validStr.get(i));
            if (i != validStr.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 0; i < unvalidStr.size(); ++i) {
            System.out.print(unvalidStr.get(i));
            if (i != unvalidStr.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
        //至此已经将合法的和不合法的按照规则输出了

        List<String> reorderedStr = new ArrayList<>();
        for (String str : validStr) {
            reorderedStr.add(reorder(str));
        }

        for (int i = 0; i < reorderedStr.size(); ++i) {
            System.out.print(reorderedStr.get(i));
            if (i != reorderedStr.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
        //输出了左移10位的字符串

        //字符串自定义排序
        List<String> sortedStr = reorderedStr;
        Collections.sort(sortedStr, (s1, s2)->{
            return s1.compareTo(s2);
        });
        for (int i = 0; i < sortedStr.size(); ++i) {
            System.out.print(sortedStr.get(i));
            if (i != sortedStr.size() - 1) {
                System.out.print(" ");
            }
        }


    }

    private static boolean isValidString(String str) {
        for (Character c : str.toCharArray()) {
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    private static String reorder(String str) {
        //这个题一定有更好的解法，话说这里一共创建了多少个字符串对象？
        int n = str.length();
        int change = 10 % n;
        String str2 = str.substring(0, change);
        String str1 = str.substring(change, n);
        return str1 + str2;
    }
}
