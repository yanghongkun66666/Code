package LeetCode.p1_twoSum.self0924;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 控制台输入格式
 * 4  代表元素个数
 * 2 7 11 15   代表nums
 * 9 代表target
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
        }

        int target = sc.nextInt();

        Map<Integer, Integer> m = new HashMap<>(); //第一个是元素值，value是下标
        for (int i = 0; i < n; ++i) {
            if (m.containsKey(target - nums[i])) {
                System.out.println(m.get(target - nums[i]) + " " + i);
                return;
            } else {
                m.put(nums[i], i);
            }
        }
    }
}


