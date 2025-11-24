package WrittenTest.HW0827.T1.ans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 6
 * 16 18 20 25 23 20
 *
 * [18, 24] max - min <= 4
 * 1 2
 * 4 5
 *
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        String[] parts = br.readLine().split("\\s+");
        int[] nums = new int[n];

        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        Deque<Integer> incr = new ArrayDeque<>(); //单增队列 O1获得有效区间最小值
        Deque<Integer> decr = new ArrayDeque<>(); //单减队列 O1获得有效区间最大值

        List<int[]> res = new ArrayList<>();
        int maxLen = 0;
        int l = 0, r = 0;

        for (r = 0; r < n; ++r) {
            if (nums[r] < 18 || nums[r] > 24) {
                //重置两个单调队列，重新构建有效区间[l, r]
                incr.clear();
                decr.clear();
                l = r + 1;
                continue;
            }

            //维护好两个单调区间
            while (!incr.isEmpty() && nums[incr.getLast()] > nums[r]) {
                incr.removeLast();
            }
            incr.addLast(r);

            while (!decr.isEmpty() && nums[decr.getLast()] < nums[r]) {
                decr.removeLast();
            }
            decr.addLast(r);


            //收缩的时候要维持单调队列区间的有效性
            while (!incr.isEmpty() && !decr.isEmpty()
            && nums[decr.getFirst()] - nums[incr.getFirst()] > 4) {
                //此时当前区间不满足第二个条件  收缩l，同时维护单调队列
                if (incr.getFirst() == l) incr.removeFirst();
                if (decr.getFirst() == l) decr.removeFirst();
                ++l;
            }

            //此时[l, r]是满足[18, 24] 区间的，同时满足第二个条件
            int tmpLen = r - l + 1;
            if (tmpLen > maxLen) {
                res.clear();
                res.add(new int[]{l, r});
                maxLen = tmpLen;
            } else if (tmpLen == maxLen) {
                res.add(new int[]{l, r});
            }
            //此时保存好了结果

            //如果[l, r1]不满足max - min的条件   那么[l, r1,  r2] 一定不满足条件  因此移动l收缩窗口来找到有效窗口



        }

        for (int[] row : res) {
            System.out.println(row[0] + " " + row[1]);
        }

    }
}
