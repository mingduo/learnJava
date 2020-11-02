package leecode._20_23_recursion_divide;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/majority-element/
 */
public class _23_OneSixNine {
    public int majorityElement(int[] nums) {
        int half = nums.length/2;

        return   Arrays.stream(nums).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>half)
                .max(Map.Entry.comparingByKey())
                .orElseThrow(IllegalArgumentException::new).getKey();

    }
}