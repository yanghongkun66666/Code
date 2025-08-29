package WrittenTest.KnapSack01;

public class Main {
    public static void main(String[] args) {
        // ===== 测试用例 1 =====
        {
            int[] w = {1, 3, 4, 5};
            int[] v = {1, 4, 5, 7};
            int capacity = 7;

//            BackTracking0x3f solver = new BackTracking0x3f(w, v, capacity);
            BackTrackingDMSXL solver = new BackTrackingDMSXL(w, v, capacity);
            System.out.println("TestCase 1: " + solver.solve());
            System.out.println("Expected: 9");
            System.out.println("--------------");
        }

        // ===== 测试用例 2 =====
        {
            int[] w = {2, 2, 6, 5, 4};
            int[] v = {6, 3, 5, 4, 6};
            int capacity = 10;

//            BackTracking0x3f solver = new BackTracking0x3f(w, v, capacity);
            BackTrackingDMSXL solver = new BackTrackingDMSXL(w, v, capacity);
            System.out.println("TestCase 2: " + solver.solve());
            System.out.println("Expected: 15");
            System.out.println("--------------");
        }

        // ===== 测试用例 3 =====
        {
            int[] w = {4, 5, 6};
            int[] v = {10, 10, 12};
            int capacity = 3;

//            BackTracking0x3f solver = new BackTracking0x3f(w, v, capacity);
            BackTrackingDMSXL solver = new BackTrackingDMSXL(w, v, capacity);
            System.out.println("TestCase 3: " + solver.solve());
            System.out.println("Expected: 0");
            System.out.println("--------------");
        }
    }
}
