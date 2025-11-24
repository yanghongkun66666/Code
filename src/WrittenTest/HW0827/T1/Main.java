package WrittenTest.HW0827.T1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 6
 * 16 18 20 25 23 20
 *
 * [18, 24] max - min <= 4
 * 1 2
 * 4 5
 *
 * 只过了 8/10 因为没考虑到max - min 这个条件可能在当前滑动窗口情况下丢失一些最长窗口
 */
public class Main {
    private static List<List<Integer>> res = new ArrayList<>();
    private static Integer maxLen = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] nums = new int[N];
        String[] parts = br.readLine().split("\\s+");

        for (int i = 0; i < N; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        solve(nums);

        Collections.sort(res, (a, b)->{
           return a.get(0) - b.get(0);  //按照第一个元素升序
        });

        for (List<Integer> row : res) {
            System.out.println(row.get(0) + " " + row.get(1));
        }



    }

    private static void solve(int[] nums) {
        int n = nums.length;
        int i = 0;
        while (i < n) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            int left = i;
            int right = left;
            if (eligible(nums[left])) {  //找到一个符合条件的开头

                while (right < n) {
                    min = Math.min(min, nums[right]);
                    max = Math.max(max, nums[right]);
                    if ((max - min <= 4) && eligible(nums[right])) {
                        //代表当前[left, right]满足条件
                        ++right;
                    } else {
                        //当前right不满足条件了，尝试更新一下结果
                        if (maxLen == 0) {
                            //代表没有结果
                            maxLen = right - left;
                            List<Integer> tmp = new ArrayList<>();
                            tmp.add(left);
                            tmp.add(right - 1);
                            res.add(tmp);
                        } else {
                            if (right - left > maxLen) {
                                maxLen = right - left;
                                res.clear();  //已经把更短的结果清除了才对啊
                                List<Integer> tmp = new ArrayList<>();
                                tmp.add(left);
                                tmp.add(right - 1);
                                res.add(tmp);
                            } else if (right - left == maxLen) {
                                //当前res中有多个list了
                                List<Integer> tmp = new ArrayList<>();
                                tmp.add(left);
                                tmp.add(right - 1);
                                res.add(tmp);
                            }
                        }

                        //不满足条件，直接退出
                        break;
                    }
                }

                if (right == n) {
                    //代表right走到结尾，[left, right - 1]都满足
                    if (right - left > maxLen) {
                        res.clear();
                        maxLen = right - left;
                    }
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(left);
                    tmp.add(right - 1);
                    res.add(tmp);
                }
            }

            i = right + 1; //好像没这么简单，因为可能max - min条件的存在，第一个元素可能是max，如果第一个元素过大，那可能影响后面更长的序列
            //这才是重点!!!

        }

    }

    private static boolean eligible(int num) {
        if (num >= 18 && num <= 24) {
            return true;
        }

        return false;
    }


}
