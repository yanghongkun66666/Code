package LeetCode.p240_searchMatrix;

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
// 怕不是糊涂了，这样写
//        for (int i = 0; i < m; ++i) {
//            for (int j = n - 1; j >= 0; --j) {
//                if (matrix[i][j] > target) {
//                    //那么j列所在列都大于target
//                    --j;
//                } else if (matrix[i][j] < target) {
//                    ++i;
//                } else {
//                    return true;
//                }
//            }
//        }

        int i = 0, j = n - 1;
        while (i <= m - 1 && j >= 0) {
            if (matrix[i][j] > target) {
                --j;
            } else if (matrix[i][j] < target) {
                ++i;
            } else {
                return true;
            }
        }

        return false;
    }
}
