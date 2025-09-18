package LeetCode.p0806.hanota;

import java.util.List;

public class Task {
    //这个类是用栈来解决的时候的任务类
    int n; //代表要移动的盘子的个数
    List<Integer> src, middle, dest;
    public Task(int n, List<Integer> src, List<Integer> middle, List<Integer> dest) {
        this.n = n;
        this.src = src;
        this.middle = middle;
        this.dest = dest;
    }
}
