package WrittenTest.Pdidi0927.T2;


import java.util.Scanner;

public class Main {

//    static class UnionFind{
//        int n;
//        int[] father;
//
//        public UnionFind(int n) {
//            this.n = n;
//            father = new int[n + 1];
//
//            for (int i = 1; i <= n; ++i) {
//                father[i] = i;
//            }
//        }
//
//        public int find (int u) {
//            if (father[u] == u) {
//                return u;
//            } else {
//                return find(father[u]);
//            }
//        }
//
//        public void join(int u, int v) {
//
//        }
//    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- != 0) {
            int n = sc.nextInt();
            sc.nextInt();
            String line = sc.nextLine();
            int cur = 0;
            int time = 0;
            while (cur < n) {
                ++time;
                cur = cur * 2 + 1;
            }
            System.out.println(time);

        }
    }
}
