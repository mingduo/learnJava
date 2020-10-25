package leecode._07_09_stack_queue;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * <p>
 * 输入: "{[]}"
 * 输出: true
 * <p>
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 */
public class _08TwoZero {

   static Map<String, String> mappings = new HashMap<String, String>(4) {
        {
            //('，')'，'{'，'}'，'['，']' 
            put("(", ")");
            put("{", "}");
            put("[", "]");
        }
    };
    public static boolean isValid(String s) {

        String[] values = s.split("");
        boolean isContinued = false;
        if(values.length%2==1){
            return false;
        }
        for (int i = 0; i < values.length/2;  i ++) {
            String key = values[i];
            String value = values[values.length - i -1];
            if (mappings.containsKey(key)
                    && value.equals(mappings.get(key))) {
                isContinued = true;
                continue;
            }
            return false;
        }

        return isContinued;
    }

    public static void main(String[] args) {
        System.out.println(isValid("{[]}"));
        System.out.println(isValid("()"));
        System.out.println(isValid("([)]"));



    }
}