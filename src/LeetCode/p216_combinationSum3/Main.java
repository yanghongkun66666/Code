package LeetCode.p216_combinationSum3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        List<List<Integer>> res = solution.combinationSum3(k, n);

        for (List<Integer> nums : res) {
            for (Integer num : nums) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
