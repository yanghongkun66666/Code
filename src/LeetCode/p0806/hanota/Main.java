package LeetCode.p0806.hanota;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        List<Integer> A = new ArrayList<>(), B = new ArrayList<>(), C = new ArrayList<>();
//        A = List.of(2, 1, 0); 这样创建出来的A是个不可变列表，没用到 new ArrayList<>()
        List<Integer> A = new ArrayList<>(List.of(2, 1, 0)), B = new ArrayList<>(), C = new ArrayList<>();

//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        solution.hanota(A, B, C);
        for (int num : C) {
            System.out.print(num + " ");
        }
    }
}
