package LeetCode.p0806.hanota;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Solution2 {
    //用栈来实现，原地移动？
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        Deque<Task> stack = new ArrayDeque<>();
        stack.addLast(new Task(A.size(), A, B, C));

        while (stack.isEmpty() == false) {
            Task tmptask = stack.removeLast(); //取出要做的任务

            if (tmptask.n == 1) {
                tmptask.dest.add(tmptask.src.remove(tmptask.src.size() - 1));
                //如果只有一个盘子，直接操作，把源移动到目标
            } else {
                //此时盘子多于一个，要逐个处理
                //分解任务，按照递归操作顺序逆序压栈
                stack.addLast(new Task(tmptask.n - 1, tmptask.middle, tmptask.src, tmptask.dest));
                //把n - 1个盘子从中间借助源移动到目标位置

                stack.addLast(new Task(1, tmptask.src, tmptask.middle, tmptask.dest));
                //把源的一个盘子直接移动到desc，只不过具体移动在出栈的时候处理

                stack.addLast(new Task(tmptask.n - 1, tmptask.src, tmptask.dest, tmptask.middle));
                //把n - 1个盘子从源借助desk移动到middle，这是模拟的递归第一步，最后压栈，第一个执行
            }
        }
    }
}
