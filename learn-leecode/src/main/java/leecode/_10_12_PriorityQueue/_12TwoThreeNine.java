package leecode._10_12_PriorityQueue;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 * 滑动窗口最大值
 */
public class _12TwoThreeNine {
    /**
     * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
     * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     * <p>
     * 返回滑动窗口中的最大值。
     * <p>
     * <p>
     *  *
     * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
     * 输出: [3,3,5,5,6,7]
     * 解释:
     * <p>
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     */
    public static Integer[] maxSlidingWindow(Integer[] nums, int k) {
        List<Integer> results = new ArrayList<>();
        Comparator<Integer> integerComparator = Integer::compareTo;
        PriorityQueue<Integer> q = new PriorityQueue<>(integerComparator.reversed());
        for (int i = 0; i < nums.length; i++) {
            Integer curVal = nums[i];
            if (q.size() < k) {
                q.offer(curVal);
            }
            if (q.size() >= k) {
                if (curVal > q.peek()) {
                    q.poll();
                }
                q.offer(curVal);
                results.add(q.peek());
            }
        }

        return results.toArray(new Integer[0]);
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        Deque<Integer> q = new ArrayDeque<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++){
            if(i >= k && q.getFirst() <= i - k) {
                q.removeFirst();
            }
            while(!q.isEmpty() && nums[q.getLast()] <= nums[i]) {
                q.removeLast();
            }
            q.add(i);
            if (i >= k - 1) {
                res[i - k + 1] = nums[q.getFirst()];
            }

        }
        return res;
    }

    public static void main(String[] args) {
        Integer[] nums = new Integer[]{1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));

        int[] values=new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(Arrays.toString(maxSlidingWindow(values, 3)));


    }
}