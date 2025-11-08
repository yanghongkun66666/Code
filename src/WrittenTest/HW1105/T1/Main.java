package WrittenTest.HW1105.T1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static class Pair{
        int floor;
        int number;

        public Pair(int floor, int number) {
            this.floor = floor;
            this.number = number;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int F = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        Map<Integer, Integer> building = new HashMap<>();
        for (int i = 1; i <= F; ++i) {
            building.put(i, M); //每一层都有M个会议室
        }

        List<Pair> req = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int number = Integer.parseInt(st.nextToken());
            req.add(new Pair(f, number));

        }

//        for (Pair p : req) {
//            System.out.println(p.floor + " " + p.number);
//        }

        req.sort((a, b)->{
            if (a.floor == b.floor) {
                return b.number - a.number;  //同一楼层从大到小
            }
            return a.floor - b.floor; //按楼层从小到大
        });
//                for (Pair p : req) {
//            System.out.println(p.floor + " " + p.number);
//        }

        int res = 0;
        for (Pair p : req) {
            if (building.get(p.floor) > 0) {
                res += p.number * 2;
                building.put(p.floor, building.get(p.floor) - 1); //成功安排第一个
            } else {
                //当前楼层没有足够的会议室，开始尝试楼下会议室
                int steps = 0;
                while (true) {
                    int nextFloor = p.floor - 1;

                    if (building.containsKey(nextFloor) == false) {
                        //没有下一层，不够安排会议了
                        System.out.println(-1);
                        return;
                    } else {
                        //有下一层，看下一层是否能够安排
                        ++steps;
                        if (building.get(nextFloor) > 0) { //如果下一层有足够的房间安排
                            res += p.number * (2 + steps); //因为这些人都移动了n层
                            building.put(nextFloor, building.get(nextFloor) - 1); //安排会议的那一层减少一个会议室
                            break;  //这一个会议安排好了，就退出死循环
                        }
                        //走到这里说明这一层也没有足够的会议室，继续循环到下一层
                    }
                }
            }
        }

        System.out.println(res);

    }
}
