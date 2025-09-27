package WrittenTest.Pjingdong0927.T2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 数组a有n个整数，相邻三个位置看成一个三元组，一共有n - 2个三元组  如果两个三元组恰好只有一个元素不同，就复合要求
 * 找出所有三元组中，符合的三元组对数量
 */
public class Main {

    static class ThreeNode{
        int a;
        int b;
        int c;
        public ThreeNode(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    private static long res;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken()); //测试用例个数
        while (t-- != 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());  //数据总数
            int[] nums = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; ++i) {
                nums[i] = Integer.parseInt(st.nextToken());
            }

            //此处获取了所有的数据  开始找这组数据中符合条件的三元组数量
            List<ThreeNode> nodes = new ArrayList<>();

            for (int i = 0; i < n - 2; ++i) {
                int[] tmp = new int[3];
                for (int j = i; j < i + 3; ++j) {
                    tmp[j - i] = nums[j];
                }

//                Arrays.sort(tmp);

                nodes.add(new ThreeNode(tmp[0], tmp[1], tmp[2]));
            }

            //至此获取所有三元组node  排序后可能有问题，先不管了，可能三元组对有重复的呢我擦了



            for (int i = 0; i < nodes.size() - 1; ++i) {
                for (int j = i + 1; j < nodes.size(); ++j) {
                    if (isValid(nodes.get(i), nodes.get(j))) {
                        ++res;
                    }
                }
            }
            System.out.println();

            System.out.println(res);
            res = 0;
            nodes.clear();

        }
    }

    private static boolean isValid(ThreeNode node1, ThreeNode node2) {
        if (node1.a != node2.a) {
            if (node1.b == node2.b && node1.c == node2.c) {
                return true;
            } else {
                return false;
            }

        } else if (node1.b != node2.b) {
            if (node1.c == node2.c) {
                return true;
            } else {
                return false;
            }
        } else {
            if (node1.c != node2.c) {
                return true;
            } else {
                return false;
            }
        }
    }

//    private static void dfs(int i, List<ThreeNode> nodes) {
//
//        if (path.size() == 2) {
//            //加入到path的都代表已经是有效的三元组对了
//            ++res;
//            return;
//        }
//
//        //非边界条件
//        for (int j = i; j < nodes.size() - 1; ++j) {
//            //j < nodes.size() - 1代表还有最后一对？
//
//        }
//    }
}
