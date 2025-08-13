package AWeiDianDong.Binary_Search;


public class BinarySearch {

    //左闭右开区间
    public static int binarySearch1(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            //最后l == r已经是空区间  满足l < r区间就不为空 只剩下l这个元素
            int mid = l + ((r - l) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    //闭区间
    public static int binarySearch2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            // l > r空区间 满足l <= r 区间就不为空
            int mid = l + ((r - l) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return -1;
    }

    //开区间
    public static int binarySearch3(int[] nums, int target) {
        int l = -1, r = nums.length;
        while (l + 1 < r) {
            //(l, r)   最后l + 1 == r就是空区间了 满足这个条件区间就不为空
            int mid = l + ((r - l) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {3, 24, 57, 59, 67, 68, 83, 90, 92, 95};

        System.out.println(binarySearch1(nums, 67));
        System.out.println(binarySearch2(nums, 67));
        System.out.println(binarySearch3(nums, 67));

        System.out.println(binarySearch1(nums, 66));
        System.out.println(binarySearch2(nums, 66));
        System.out.println(binarySearch3(nums, 66));
    }
}
