package leecode.string;

/**
 * @author : weizc
 * @since 2020/8/31
 */
public class BalancedStringSplitDemo {

    /**
     * 1221. 分割平衡字符串
     * 在一个「平衡字符串」中，'L' 和 'R' 字符的数量是相同的。
     * <p>
     * 给出一个平衡字符串 s，请你将它分割成尽可能多的平衡字符串。
     * <p>
     * 返回可以通过分割得到的平衡字符串的最大数量。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：s = "RLRRLLRLRL"
     * 输出：4
     * 解释：s 可以分割为 "RL", "RRLL", "RL", "RL", 每个子字符串中都包含相同数量的 'L' 和 'R'。
     * 示例 2：
     * <p>
     * 输入：s = "RLLLLRRRLR"
     * 输出：3
     * 解释：s 可以分割为 "RL", "LLLRRR", "LR", 每个子字符串中都包含相同数量的 'L' 和 'R'。
     * 示例 3：
     * <p>
     * 输入：s = "LLLLRRRR"
     * 输出：1
     * 解释：s 只能保持原样 "LLLLRRRR".
     *
     * @param args
     */
    public static void main(String[] args) {

        //4
        System.out.println(balancedStringSplit("RLRRLLRLRL"));
        //1
        System.out.println(balancedStringSplit("LLLLRRRR"));
        //3
        System.out.println(balancedStringSplit("RLLLLRRRLR"));
    }

    public static int balancedStringSplit(String s) {
        int count = 0;

        int lastIndex = 0;
        for (int i = 0; i <= s.length(); i = i + 2) {
            int search = charListValid(lastIndex, i, s);
            if (search > 0) {
                lastIndex = search;
                count++;
            }
        }

        return count;
    }

    private static int charListValid(Integer lastIndex, Integer end, String s) {

        if (end > lastIndex) {
            String value = s.substring(lastIndex, end);
            if (valid(value)) {
                return end;
            }
        }
        return -1;
    }

    private static boolean valid(String value) {

        return count(value, 'L') == count(value, 'R');

    }

    private static long count(String value, char l) {
        int i = 0;
        for (char c : value.toCharArray()) {
            if (c == l) {
                i++;
            }
        }
        return i;
    }


}
