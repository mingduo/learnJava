package leecode._39_42_bit;

/**
 * https://leetcode-cn.com/problems/number-of-1-bits/submissions/
 */
public class _40_OneNineOne {
    /**
     * 一位一位的算，直接枚举
     *
     * @param n
     * @return
     */
    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if((n&1)==1) {
                count++;
            }

            n = n >> 1;
        }
        return count;
    }

    public static int _hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n = (n & (n - 1));
            count++;
        }
        return count;
    }


    public static void main(String[] args) {
        int n = 5;
        System.out.println("value:" + Integer.toBinaryString(n) +
                ",count:" + hammingWeight(n));


        System.out.println("value:" + Integer.toBinaryString(n) +
                ",count:" +     _hammingWeight(n));


    }

}