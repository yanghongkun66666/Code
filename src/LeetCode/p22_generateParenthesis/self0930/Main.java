package LeetCode.p22_generateParenthesis.self0930;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        Solution solution = new Solution();
        List<String> res = solution.generateParenthesis(n);

        for (String str : res) {
            System.out.println(str);
        }
    }
}
