package WrittenTest.PXiecheng.T2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/*
s数组长度n =  2的k次方 表示n个队伍能力值
你位于下标1的队伍  两两组合对决，淘汰后进入下一轮 重复该流程，直到只剩下一支队伍  保证队伍能力两两不同
计算你的队伍获胜总场次，第一轮就被淘汰，获胜场次是0
n == 1的时候，你无序比赛，胜场为0

 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- != 0) {
            int n = sc.nextInt();
            sc.nextLine();
            String[] parts = sc.nextLine().split(" ");
            int[] nums = new int[n];
            for (int i = 0; i < n; ++i) {
                nums[i] = Integer.parseInt(parts[i]);
            }

            int res = competition(nums);
            System.out.println(res);
        }
    }

    private static int competition(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int win = 0;
        Deque<Integer> queue = new ArrayDeque<>(); //这个队列存放每一轮成功竞选的候选者下标
        for (int i = 0; i < nums.length; ++i) {
            queue.addLast(i);
        }

        while (queue.size() > 1) {
            Deque<Integer> round = new ArrayDeque<>(queue); //存放这一轮要进行竞选的候选者下标
            queue.clear();

            while (round.size() > 1) {
                int competitor1 = round.removeFirst();
                int competitor2 = round.removeFirst();

                if (nums[competitor1] > nums[competitor2]) {
                    queue.addLast(competitor1);
                    if (competitor1 == 0) {
                        ++win;
                    }
                } else {
                    queue.addLast(competitor2);
                    if (competitor1 == 0) {
                        //在某一轮你的队伍被淘汰了
                        break;
                    }
                }

            }

            if (round.size() != 0) {
                queue.addLast(round.removeFirst()); //把round中最后一个队伍加入到待竞赛队伍中
            }


        }

        return win;
    }
}
