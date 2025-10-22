package WrittenTest.PFunPlus1019.T1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 给定的5张牌是否是顺子
     * @param nums int整型ArrayList 扑克牌对应的数字集合
     *             最长连续子数组
     * @return bool布尔型
     */
    public boolean isStraight (ArrayList<Integer> nums) {
        //数组取值[0, 13]   1 2 3 4 5是顺子   10 11 12 13 1也是顺子
        // write code here
        int biggest = -1;
        Set<Integer> s = new HashSet<>();
        int cntZero = 0, cntOne = 0;
        for (int num : nums) {
            if (num == 0) {
                ++cntZero;
                if (cntZero > 2) return false;
            } else if (num == 1) {
                ++cntOne; //当最长是4  //可能有多个Ace吗？  有多个一定是错误的，这个可以考虑一下
                if (cntOne > 1) return false;
            } else {
                if (s.contains(num)) return false; //有的话就重复了，一定不是顺子
                s.add(num);
            }
        }

        if (s.contains(10) || s.contains(11) || s.contains(12) || s.contains(13)) {
            if (cntOne == 1) {
                s.add(14);
            }
        }

        if (s.contains(2) || s.contains(3) || s.contains(4) || s.contains(5)) {
            if (cntOne == 1) {
                s.add(1);
            }
        }
        //这是处理Ace的策略

        int longest = getLongestSubArray(s);
        if (longest == 5) return true; //没有大小王，那么需要5个连续
        if (longest == 4 && cntZero == 1) return true;//有一个大小王，有4个连续
        if (longest == 3 && cntZero == 2) return true;//有两个大小王，有3个连续
        if (longest < 5 && cntZero == 0) return false;
        //考虑有大小王，同时不连续的场景
        //暴利枚举看两个任取所有可能，能否将我们现有的集合变成长度为5的，直接暴力
        if (cntZero == 1) {
            for (int i = 1; i <= 14; ++i) {
                s.add(i);
                if (getLongestSubArray(s) == 5) {
                    return true;
                }
            }
        } else if (cntZero == 2) {
            for (int i = 1; i <= 14; ++i) {
                for (int j = 1; j <= 14; ++j) {
                    s.add(i);
                    s.add(j);
                    if (getLongestSubArray(s) == 5) {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    private int getLongestSubArray(Set<Integer> s) {
        int res = 0;
        for (int x : s) {
            if (s.contains(x - 1)) continue;
            int y = x + 1;
            while (s.contains(y) == true) {
                ++y;
            }
            //此时y不存在于集合中了
            res = Math.max(res, y - x);
        }

        return res;
    }


}
