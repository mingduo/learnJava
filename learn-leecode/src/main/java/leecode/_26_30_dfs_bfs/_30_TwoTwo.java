package leecode._26_30_dfs_bfs;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/generate-parentheses/
 */
public class _30_TwoTwo {
    /**
     * 输入：n = 3
     * 输出：[
     * "((()))",
     * "(()())",
     * "(())()",
     * "()(())",
     * "()()()"
     * ]
     *
     * @param n
     * @return
     */
    public static List<String> generateParenthesis(int n) {
        //动态规划  or 剪枝
        List<String> list = new ArrayList<>();
        generateParenthesisByDFS("", list, n, n);
        return list;
    }

    //剪枝
    public static List<String> generateParenthesisByTrim(int n) {
        //动态规划  or 剪枝
        List<String> combinations = new ArrayList<>();
        generateAll(new char[2 * n], 0, combinations);
        return combinations;
    }

    public static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current)) {
                result.add(new String(current));
            }
        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }

    }

    private static boolean valid(char[] current) {
        int isbalanced = 0;
        for (char c : current) {
            if (c == '(') {
                isbalanced++;
            } else {
                isbalanced--;
            }
            if (isbalanced < 0) {
                return false;
            }
        }
        return isbalanced == 0;

    }

    /**
     * 动态规划
     */
    private static void generateParenthesisByDFS(String subList, List<String> list, int left, int right) {
        if (left == 0 && right == 0) {
            list.add(subList);
            return;
        }

        if (left > 0) {
            generateParenthesisByDFS(subList + "(", list, left - 1, right);
        }
        if (right > left) {
            generateParenthesisByDFS(subList + ")", list, left, right - 1);
        }
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        generateN(3, 3, list, "");

        System.out.println(list);
    }

    private static void generateN(int left, int right, List<Integer> list, String value) {
        if (0 == left && right == 0) {
            list.add(Integer.parseInt(value));
            return;
        }
        if (left > 0) {
            generateN(left - 1, right, list, value + "1");
        }
        if (left<right ) {
            generateN(left, right - 1, list, value + "2");
        }
    }


}