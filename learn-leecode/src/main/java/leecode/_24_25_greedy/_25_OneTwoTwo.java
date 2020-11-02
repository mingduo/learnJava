package leecode._24_25_greedy;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 */
public class _25_OneTwoTwo {
    /**
     * 还有一种算法是可以dfs，即递归每天买入或者卖出
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int maxValue = 0;
        int sum=0;
        for (int i = 1; i < prices.length; i++) {
            sum= prices[i] - prices[i - 1];
            if(sum>0){
                maxValue+=sum;
            }
        }
        return maxValue;

    }

    public static void main(String[] args) {

        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));

        System.out.println(maxProfit(new int[]{7,6,4,3,1}));

        System.out.println(maxProfit(new int[]{1,2,3,4,5}));

    }
}