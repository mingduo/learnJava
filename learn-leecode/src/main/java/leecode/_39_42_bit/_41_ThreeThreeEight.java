package leecode._39_42_bit;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/counting-bits/
 */
public class _41_ThreeThreeEight {
    /**
     * 方法1：找规律，第i组（2^(i-1)-(2^i-1)）为前i-1组所有数字+1（首位1）
     * 数字	     \	\	\	\	\	\	\	\
     * 0								    0
     * 1								    1
     * 2 - 3							1	2
     * 4 - 7					1	2	2	3
     * 8 - 15	1	2	2	3	2	3	3	4
     *
     * @param num
     * @return
     */
    public static int[] countBits1(int num) {
        int[] results = new int[num + 1];
        for (int i = 0; i <= num; i++) {
            results[i] = bitCount(i);
        }
        return results;
    }

    private static int bitCount(int num) {
        int count = 0;
        while (num != 0) {
            num = num & (num - 1);
            count++;
        }
        return count;
    }

    /**
     * count[i]=count[i&(i-1)]+1
     *
     * @param num
     * @return
     */
    public static int[] countBits2(int num) {
        int[] results = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            results[i] = results[i & (i - 1)] + 1;
        }
        return results;
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(countBits1(5)));
        System.out.println(Arrays.toString(countBits2(5)));

    }
}