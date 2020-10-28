package leecode._13_16_hash;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

/**
 * https://leetcode-cn.com/problems/valid-anagram/
 * 242. 有效的字母异位词
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 *
 * 示例 1:
 *
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 *
 * 输入: s = "rat", t = "car"
 * 输出: false
 */
public class _14_TwoFourTwo {

    /**
     * 排序后比较 时间复杂度更大
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {
       if(s.length()!=t.length()){
           return false;
       }
        char[] chars = s.toCharArray();
        char[] chart = t.toCharArray();
        Arrays.sort(chars);
        Arrays.sort(chart);
        for (int i = 0; i < s.length(); i++){
            if(chars[i] != chart[i]) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<String> strconvertList(String s) {
        ArrayList<String> tmpA = Lists.newArrayList(s.split(""));
        tmpA.sort(Comparator.comparing(Function.identity()));
        return tmpA;
    }

    public static void main(String[] args) {

        System.out.printf("anagram vs nagaram : %s\n",isAnagram("anagram","nagaram"));
        System.out.printf("rat vs car : %s\n",isAnagram("rat","car"));

    }
}