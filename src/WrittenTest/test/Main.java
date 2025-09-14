package WrittenTest.test;

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 4, 3 ,2 ,1};
        //输出每个元素比他小的元素个数 res = {0, 2, 4, 6, 6, 4, 2, 0}

        Solution solution = new Solution();
        int[] res = solution.lessNums(nums);
        for (int num : res) {
            System.out.print(num + " ");
        }

    }
}
