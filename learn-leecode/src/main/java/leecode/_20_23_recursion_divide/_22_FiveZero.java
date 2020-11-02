package leecode._20_23_recursion_divide;

/**
 * https://leetcode-cn.com/problems/powx-n/
 */
public class _22_FiveZero {
    /**
     * 递归
     * @param x
     * @param n
     * @return
     */
    public static double myPow(double x, int n) {
        if(n==0){
            return 1;
        }
        if(n<0){
            return 1/myPow(x,-n);
        }
        //偶数
        if(n%2==0){
            return myPow(x*x,n/2);
        }else {
            return x*myPow(x,n-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(myPow(2,10));
        System.out.println(myPow(2.1,3));
        System.out.println(myPow(2,-2));

    }
}