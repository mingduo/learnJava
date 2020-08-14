package cpu.check;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 168
 */
public class RegexLoad {

    /**
     *
     * https://github.com/oldratlee/useful-scripts.git
     * 执行 show-busy-java-threads
     * 1] Busy(98.1%) thread(5048/0x13b8) stack of java process(5047) under user(program):
     * "main" #1 prio=5 os_prio=0 tid=0x00007f1fac009000 nid=0x13b8 runnable [0x00007f1fb339d000]
     *    java.lang.Thread.State: RUNNABLE
     *         at java.util.regex.Pattern$GroupHead.match(Pattern.java:4660)
     *         at java.util.regex.Pattern$Loop.match(Pattern.java:4787)
     *         at java.util.regex.Pattern$GroupTail.match(Pattern.java:4719)
     * @param args
     */

    public static void main(String[] args) {
        String[] patternMatch = {"([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)",
                "([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)"};
        List<String> patternList = new ArrayList<String>();

        patternList.add("Avg Volume Units product A + Volume Units product A");
        patternList.add("Avg Volume Units /  Volume Units product A");
        patternList.add("Avg retailer On Hand / Volume Units Plan / Store Count");
        patternList.add("Avg Hand Volume Units Plan Store Count");
        patternList.add("1 - Avg merchant Volume Units");
        patternList.add("Total retailer shipment Count");

        for (String s :patternList ){

            for(int i=0;i<patternMatch.length;i++){
                Pattern pattern = Pattern.compile(patternMatch[i]);

                Matcher matcher = pattern.matcher(s);
                System.out.println(s);
                if (matcher.matches()) {

                    System.out.println("Passed");
                }else {
                    System.out.println("Failed;");
                }
            }

        }
    }
}