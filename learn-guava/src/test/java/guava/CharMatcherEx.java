package guava;

import com.google.common.base.CharMatcher;
import org.junit.Test;

/**
 * <table border="1">
 * <tr><th>@Description: </th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 * https://github.com/google/guava/wiki/ServiceExplained
 *
 * @author :    weizc
 */
public class CharMatcherEx {
    @Test
    public void test() {

        String input = "Peter17";

        CharMatcher matcher = CharMatcher.inRange('0', '9');
        String result = matcher.retainFrom(input);

        System.out.println(result);

        result = matcher.removeFrom(input);

        System.out.println(result);

        result = matcher.replaceFrom(input, "*");

        System.out.println(result);


        result = CharMatcher.inRange('0', '9').retainFrom(input);

        System.out.println(result);

    }

    @Test
    public void test2() {
        String input = "Beautiful sunny day";

        int n1 = CharMatcher.is('n').countIn(input);
        System.out.format("Number of n characters: %d%n", n1);

        int n2 = CharMatcher.is('i').countIn(input);
        System.out.format("Number of i characters: %d", n2);
    }

    @Test
    public void test3() {

        String input = "   yogurt \t";

        String result = CharMatcher.whitespace().trimFrom(input);

        System.out.println(input + " and bread");
        System.out.println(result + " and bread");
    }

    @Test
    public void charMatch() {
        String string = "\taBc123 abc 2";

        System.out.println("oriString=>" + string);
        // remove control characters
        String noControl = CharMatcher.javaIsoControl()
                .removeFrom(string);
        System.out.println("noControl=>" + noControl);

        // only the digits
        String theDigits = CharMatcher.digit().retainFrom(string);
        System.out.println("theDigits=>" + theDigits);

        // trim whitespace at ends, and replace/collapse whitespace into single spaces
        String spaced = CharMatcher.whitespace()
                .trimAndCollapseFrom(string, '$');

        System.out.println("spaced=>" + spaced);

        // star out all digits
        String noDigits = CharMatcher.javaDigit()
                .replaceFrom(string, "*");

        System.out.println("noDigits=>" + noDigits);

        // eliminate all characters that aren't digits or lowercase

        String lowerAndDigit = CharMatcher.javaDigit()
                .or(CharMatcher.javaLowerCase())
                .retainFrom(string);

        System.out.println("lowerAndDigit=>" + lowerAndDigit);

        String inRange = CharMatcher.inRange('a', 'b')
                .retainFrom(string);

        System.out.println("inRange=>" + inRange);

        String anyOf = CharMatcher.anyOf("abc")
                .retainFrom(string);

        System.out.println("anyOf=>" + anyOf);

        String isNot = CharMatcher.isNot('b')
                .retainFrom(string);

        System.out.println("isNot=>" + isNot);
    }


}
