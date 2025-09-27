package WrittenTest.Pjingdong0927.T1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 数组长度为n   1....n  每个下标代表能放一个不同的数字
 * m个不同的数字   1....m个不同的数字
 *第x个数字要求在所有检查区间出现至少出现bx次 >= 0
 * 找出长度最短的[l,r]使得该区间 1...m数字都满足检查要求，比如[l, r]区间要求所有检查中3出现3次，满足了
 *为什么，精确freq匹配到的不是最短的吗
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); //货架长度
        int m = Integer.parseInt(st.nextToken());  //待摆放物品类别个数
        st = new StringTokenizer(br.readLine());
        int[] nums = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int[] freq = new int[n + 1]; //表示1...m商品最低出现次数要求
        st = new StringTokenizer(br.readLine());
        int sumCnt = 0;
        for (int i = 1; i <= m; ++i) {
            freq[i] = Integer.parseInt(st.nextToken());
            if (freq[i] != 0) sumCnt++;
        }
        //滑动窗口
        int[] cur = new int[n + 1];
        int left = 1;
        int res = Integer.MAX_VALUE;
        int curCount = 0;
        for (int right = 1; right <= n; ++right) {
            //满足freq才可以收缩
//            if (freq[nums[right]] == 0) {
//                continue;
//                //没必要统计
//            }
            cur[nums[right]]++;
            //统计满足条件个数cnt
            if (cur[nums[right]] == freq[nums[right]]) ++curCount; //如果对应位置的数满足了frequ要求，此时这个curCount代表有一个数满足了
            while (curCount == sumCnt) { //只有所有freq都满足才能进入到这里
                //此时可以更新答案然后收缩了
                res = Math.min(res, right - left + 1);
//                if (freq[nums[left]] == 0) {
//                    ++left;
//                } else {
//                    cur[nums[left]]--;
//                    --curCount;
//                }
                if (freq[nums[left]] != 0) {
                    --curCount;
                }
                cur[nums[left]]--;
                ++left;


            }
        }

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);

    }
}
