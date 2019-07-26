package com.ais.brm.study.brmTest.spring.spel;

import com.ais.brm.study.domain.Inventor;
import lombok.ToString;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/2/2
 */
public class EvaluationTest {

    /**
     * literal string expression
     */
    @Test
    public void test() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);
    }

    /**
     * calling methods, accessing properties, and calling constructors
     */
    @Test
    public void test1() {
        //	The value of message is now 'Hello World!'.
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        System.out.print("The value of message is now 'Hello World!'.");
        System.out.println(message);

        // invokes 'getBytes()'

        exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        System.out.print("This line converts the literal to a byte array. .");

        System.out.println(bytes);


        // invokes 'getBytes().length'
        exp = parser.parseExpression("'Hello World'.bytes.length");
        int length = (Integer) exp.getValue();
        System.out.print("Hello World'.bytes.length gives the length of the literal.. .");

        System.out.println(length);

        //Construct a new String from the literal and make it be upper case
        exp = parser.parseExpression("new String('hello world').toUpperCase()");
        message = exp.getValue(String.class);
        System.out.print("Construct a new String from the literal and make it be upper case. .");
        System.out.println(message);


    }


    // shows how to retrieve the name property from an instance of the Inventor class or create a boolean condition:
    @Test
    public void test3() {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();


        Expression exp = parser.parseExpression("name");
        String name = (String) exp.getValue(tesla);
        // name == "Nikola Tesla"
        System.out.println(name);

        exp = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = exp.getValue(tesla, Boolean.class);
        // result == true

        exp = parser.parseExpression("name");
        // 也起作用了
        tesla.setName("Newton");

        String value =exp.getValue(tesla,String.class);
        System.out.println(value);
    }


    /**
     * SimpleEvaluationContext
     * Type Conversion
     */


    @Test
    public void test4() {
        class Simple {
            public List<Boolean> booleanList = new ArrayList<>();
        }
        Simple simple = new Simple();
        simple.booleanList.add(true);
        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        // "false" is passed in here as a String. SpEL and the conversion service
        // will recognize that it needs to be a Boolean and convert it accordingly.
        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");

        // b is false
        Boolean b = simple.booleanList.get(0);
    }

    /**
     * automatically grow the list:
     */


    @Test
    public void test5() {
        @ToString
        class Demo {
            public List<String> list;
        }
        // Turn on:
// - auto null reference initialization
// - auto collection growing
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);

        ExpressionParser parser = new SpelExpressionParser(config);

        Expression expression = parser.parseExpression("list[3]");

        Demo demo = new Demo();

        Object o = expression.getValue(demo);
        System.out.println("o=>"+o);

        System.out.println(demo);

        // demo.list will now be a real collection of 4 entries
        // Each entry is a new empty String
    }
}
