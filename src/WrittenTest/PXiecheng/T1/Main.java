package WrittenTest.PXiecheng.T1;

import java.util.Scanner;

/*
这个题目是可以允许修改一个字符比如大写换成小写，小写换成大写，
不需要修改就是好串，需要修改一次就是还行，修改多于一次就是差串，好串的定义是大小写交替
一个字符就是好且不错
满足两个相邻字符一个大写一个小写就是好
满足只反转一次就变成好串的字符串称之为不错
5
1
a
2
Aa
2
AA
3
abc
4
aBCd

2 4
看还有什么别的解决的可能吗，通过了10%
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int good = 0;
        int as_well = 0;
        while (T-- != 0) {
            int n = sc.nextInt();
            sc.nextLine();
            String to_judge = sc.nextLine();
            if (judge(to_judge) == 0) {
                //代表好串
                good++;
                as_well++;
            } else if (judge(to_judge) == 1) {
                as_well++;
            }
        }

        System.out.println(good + " " + as_well);
    }

    private static int judge(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n == 1) {
            return 0; //代表是好串
        }

        int crossNum = 0;

        for (int i = 1; i < n; ++i) {
            if (s[i] >= 'a' && s[i] <= 'z') {
                if (s[i - 1] >= 'A' && s[i - 1] <= 'Z') {
                    //合法不用管往后跳
                    continue;
                } else {
                    //不合法看前后 前后一致 +1  前后不一致 +2
                    //前一个也是小写
                    //如果后一个不存在或者后一个存在且后一个也是小写，就+1
                    if ((i + 1 >= n) || (i + 1 < n && s[i + 1] >= 'a' && s[i + 1] <= 'z')) {
                        ++crossNum;
                        ++i;  //下一个元素没必要判断了，改一个则区间长度为3是满足条件的
                    } else {
                        crossNum += 2;
                        ++i;
                    }

                }
            } else { //第一个是大写，看前一个是不是小写
                if (s[i - 1] >= 'a' && s[i - 1] <= 'z') {
                    //合法不用管往后跳
                    continue;
                } else {
                    //不合法看前后 前后一致 +1  前后不一致 +2
                    //前一个也是小写
                    //如果后一个不存在或者后一个存在且后一个也是小写，就+1

                    //前一个是大写，当前也是大写  如果后一个存在也是大写，则可以只修改一次
                    if ((i + 1 >= n) || (i + 1 < n && s[i + 1] >= 'A' && s[i + 1] <= 'Z')) {
                        ++crossNum;
                        ++i;  //下一个元素没必要判断了，改一个则区间长度为3是满足条件的
                    } else {
                        crossNum += 2;
                        ++i;
                    }

                }
            }

            if (crossNum > 1) break;
        }

        if (crossNum == 0) {
            return 0;
        } else if (crossNum == 1) {
            return 1;
        }

        return -1;
    }
}
