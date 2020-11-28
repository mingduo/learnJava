package leecode._43_51_dynamic_programming;

/**
 * 递归+记忆化--->递推
 */
public class FIb {

    /**
     * 递归
     * 最原始的算法，复杂度2^n
     * 因为重复计算了好多数字，可以用一个数字记录，之后直接取出就行了
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        return n > 1 ? fib(n - 1) + fib(n - 2) : n;
    }

    /**
     * 递归 + 记忆化
     *
     * @param n
     * @param memo
     * @return
     */
    public static int fib(int n, int[] memo) {
        if (n < 2) {
            return n;
        }
        memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
        return memo[n];
    }

    /**
     * 递归 + 记忆化
     * 上面是递归，但是可以从下往上，迭代->递推
     *
     * @param n
     * @param cache
     * @return
     */
    public static int foreachFib(int n, int[] cache) {
        cache[1] = 1;
        for (int i = 2; i < n; i++) {
            //状态转移方程
            cache[i] = cache[i - 1] + cache[i - 2];
        }
        return cache[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(fib(2));
        System.out.println(fib(2, new int[2]));


        System.out.println(foreachFib(2, new int[2]));

    }
}