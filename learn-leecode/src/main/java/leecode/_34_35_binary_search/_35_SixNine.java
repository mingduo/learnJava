package leecode._34_35_binary_search;

/**
 * https://leetcode-cn.com/problems/sqrtx/
 */
public class _35_SixNine {
    public static int mySqrt(int x) {

        long left = 1;
        long right = x;
        long mid;
        while (left <= right) {
            mid = (left + right) / 2;
            long target = mid * mid;
            if (target == x) {
                return (int) mid;
            } else if (target < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return Math.toIntExact(Math.min(left, right));
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));
        System.out.println(mySqrt(7));
        System.out.println(mySqrt(3));

    }


}