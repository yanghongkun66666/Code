package LeetCode.p0806.hanota;

import java.util.List;

public class Solution {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        move(A.size(), A, B, C);
    }

    private void move(int n, List<Integer> A, List<Integer> B, List<Integer> C) {
        if (n == 1) {
            C.add(A.remove(A.size() - 1));
            return;
        }

        move(n - 1, A, C, B);  //把n - 1个从A借助C移动到B
        C.add(A.remove(A.size() - 1));
        move(n - 1, B, A, C);
    }
}
