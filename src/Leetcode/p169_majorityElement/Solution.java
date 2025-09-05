package Leetcode.p169_majorityElement;

public class Solution {
    public int majorityElement(int[] nums) {
        //擂台赛
        int res = nums[0];
        int hp = 0;
        for (int x : nums) {
            if (hp == 0) { //x是初始擂主，生命值为1
                res = x;
                hp = 1;
            } else { //比武，同门加血，否则扣血
                hp += res == x ? 1 : -1;
            }
        }

        return res;
    }

}
