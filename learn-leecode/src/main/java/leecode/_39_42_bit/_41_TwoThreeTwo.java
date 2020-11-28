package leecode._39_42_bit;

/**
 * https://leetcode-cn.com/problems/power-of-two/
 */
public class _41_TwoThreeTwo {

    public static boolean isPowerOfTwo(int n) {
        if(n<=0){
            return false;
        }
        return (n & (n - 1)) == 0;

    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(2));
        System.out.println(isPowerOfTwo(4));
        System.out.println(isPowerOfTwo(218));

    }
}