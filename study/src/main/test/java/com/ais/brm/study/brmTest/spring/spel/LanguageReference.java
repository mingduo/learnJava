package com.ais.brm.study.brmTest.spring.spel;

import com.ais.brm.study.domain.Example;
import com.ais.brm.study.domain.Inventor;
import com.ais.brm.study.domain.PlaceOfBirth;
import com.ais.brm.study.domain.Society;
import com.ais.brm.study.utils.MiscUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author : weizc
 * @description: https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#expressions-language-ref
 * @since 2019/3/29
 */
public class LanguageReference {
    ExpressionParser parser = new SpelExpressionParser();

    private static EvaluationContext getEvaluationSocietyContext() {
        Society society = new Society();
        society.setName("audi");
        society.addMembers(new Example().getInventions());

        return new StandardEvaluationContext(society);
    }

    //Literal Expressions
    @Test
    public void test1() {

        // evals to "Hello World"
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();

        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();

        // evals to 2147483647
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();

        Object nullValue = parser.parseExpression("null").getValue();
    }

    // Properties, Arrays, Lists, Maps, and Indexers
    @Test
    public void test2() {

        GregorianCalendar cal = new GregorianCalendar();
        cal.set(1856, 7, 9);
        Inventor inventor = new Inventor("Nicolas Tesla", cal.getTime(), "serbian");

        System.out.println("inventor.Birthdate.Year=>" + inventor.getBirthdate().getYear());

        // 利用目标对象（root object）构造一个context。开销相对较高，建议仅用于不常改变的对象。
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        ((StandardEvaluationContext) evaluationContext).setRootObject(inventor);

        // evals to 1856
        int year = (Integer) parser.parseExpression("Birthdate.Year+1900").getValue(evaluationContext);
        System.out.println("year=" + year);


    }

    @Test
    public void test2_1() {
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        // Inventions Array
        Example examples = new Example();
        // evaluates to "Induction motor"
        Inventor invention = parser.parseExpression("inventions[3]").getValue(
                context, examples, Inventor.class);

        System.out.println("invention=>" + invention);

        System.out.println("examples before=>" + MiscUtils.toPrettyJson(examples));


        Inventor inventor = new Inventor("bezz", Calendar.getInstance().getTime(), "serbian");
        parser.parseExpression("inventions[0]")
                .setValue(context, examples, inventor);

        System.out.println("examples after=>" + MiscUtils.toPrettyJson(examples));


    }

    //Inline Lists
    @Test
    public void test3() {
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();


        List numbers = (List) parser.parseExpression("{1,2,3,4}").getValue(context);

        List listOfLists = (List) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue(context);

        System.out.println("numbers=>" + MiscUtils.toPrettyJson(numbers));

        System.out.println("listOfLists=>" + MiscUtils.toPrettyJson(listOfLists));


    }

    // Inline Maps
    @Test
    public void test4() {
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        // evaluates to a Java map containing the two entries
        Map inventorInfo = (Map) parser.parseExpression("{name:'Nikola',dob:'10-July-1856'}")
                .getValue(context);

        Map mapOfMaps = (Map) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}")
                .getValue(context);

        System.out.println("inventorInfo=>" + MiscUtils.toPrettyJson(inventorInfo));
        System.out.println("mapOfMaps=>" + MiscUtils.toPrettyJson(mapOfMaps));

    }

    // Array Construction
    @Test
    public void test5() {

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue(context);

        // Array with initializer
        int[] numbers2 = parser.parseExpression("new int[]{1,2,3}").getValue(context, int[].class);

        // Multi dimensional array
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);

        System.out.println("numbers1=>" + MiscUtils.toPrettyJson(numbers1));
        System.out.println("numbers2=>" + MiscUtils.toPrettyJson(numbers2));
        System.out.println("numbers3=>" + MiscUtils.toPrettyJson(numbers3));

    }


    /*   Methods
       You can invoke methods by using typical Java programming syntax. You can also invoke methods on literals.
        Variable arguments are also supported. The following examples show how to invoke methods:
        */
    @Test
    public void test6() {

        EvaluationContext societyContext = getEvaluationSocietyContext();
        // string literal, evaluates to "bc"
        String bc = parser.parseExpression("'abc'.substring(1, 3)").getValue(String.class);

        // evaluates to true
        boolean isMember = parser.parseExpression("isMember('bmw')").getValue(
                societyContext, Boolean.class);

        System.out.println("bc=>" + MiscUtils.toPrettyJson(bc));
        System.out.println("isMember=>" + MiscUtils.toPrettyJson(isMember));

    }

    //
    //

    /**
     * Operators
     * The Spring Expression Language supports the following kinds of operators:
     * Relational Operators
     * <p>
     * Logical Operators
     * <p>
     * Mathematical Operators
     * <p>
     * The Assignment Operator
     */

    //Relational Operators
    @Test
    public void test7() {
        // evaluates to true
        boolean trueValue = parser.parseExpression("2 == 2").getValue(Boolean.class);

        // evaluates to false
        boolean falseValue = parser.parseExpression("2 < -5.0").getValue(Boolean.class);

        // evaluates to true
        boolean bb = parser.parseExpression("'black' < 'block'").getValue(Boolean.class);
        //awlays false
        boolean cc = parser.parseExpression("'black' < null").getValue(Boolean.class);

        System.out.println(cc);

        // evaluates to false
        falseValue = parser.parseExpression(
                "'xyz' instanceof T(Integer)").getValue(Boolean.class);

        // evaluates to true
        trueValue = parser.parseExpression(
                "'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);

        //evaluates to false
        falseValue = parser.parseExpression(
                "'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);
    }


    /**
     * Logical Operators
     * <p>
     * SpEL supports the following logical operators:
     * <p>
     * and
     * <p>
     * or
     * <p>
     * not
     */
    @Test
    public void test7_1() {


        EvaluationContext societyContext = getEvaluationSocietyContext();
        // -- AND --

        // evaluates to false
        boolean falseValue = parser.parseExpression("true and false").getValue(Boolean.class);

        // evaluates to true
        String expression = "isMember('Nikola Tesla') and isMember('Mihajlo Pupin')";
        boolean trueValue = parser.parseExpression(expression).getValue(societyContext, Boolean.class);

        // -- OR --

        // evaluates to true
        trueValue = parser.parseExpression("true or false").getValue(Boolean.class);

        // evaluates to true
        expression = "isMember('Nikola Tesla') or isMember('Albert Einstein')";
        trueValue = parser.parseExpression(expression).getValue(societyContext, Boolean.class);

        // -- NOT --

        // evaluates to false
        falseValue = parser.parseExpression("!true").getValue(Boolean.class);

        // -- AND and NOT --
        expression = "isMember('Nikola Tesla') and !isMember('Mihajlo Pupin')";
        falseValue = parser.parseExpression(expression).getValue(societyContext, Boolean.class);
    }

    //Mathematical Operators
    @Test
    public void test7_2() {
        // Addition
        int two = parser.parseExpression("1 + 1").getValue(Integer.class);  // 2

        String testString = parser.parseExpression(
                "'test' + ' ' + 'string'").getValue(String.class);  // 'test string'

        // Subtraction
        int four = parser.parseExpression("1 - -3").getValue(Integer.class);  // 4

        double d = parser.parseExpression("1000.00 - 1e4").getValue(Double.class);  // -9000

        // Multiplication
        int six = parser.parseExpression("-2 * -3").getValue(Integer.class);  // 6

        double twentyFour = parser.parseExpression("2.0 * 3e0 * 4").getValue(Double.class);  // 24.0

        // Division
        int minusTwo = parser.parseExpression("6 / -3").getValue(Integer.class);  // -2

        double one = parser.parseExpression("8.0 / 4e0 / 2").getValue(Double.class);  // 1.0

        // Modulus
        int three = parser.parseExpression("7 % 4").getValue(Integer.class);  // 3

        one = parser.parseExpression("8 / 5 % 2").getValue(Integer.class);  // 1

        // Operator precedence
        int minusTwentyOne = parser.parseExpression("1+2-3*8").getValue(Integer.class);  // -21
    }

    /**
     * The Assignment Operator
     * To setting a property, use the assignment operator (=).
     * This is typically done within a call to setValue but can also be done
     * inside a call to getValue. The following listing shows both ways to use the assignment operator:
     */

    @Test
    public void test7_3() {
        Inventor inventor = new Inventor();
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        parser.parseExpression("Name").setValue(context, inventor, "Aleksandar Seovic");

        // alternatively
        String aleks = parser.parseExpression(
                "Name = 'Aleksandar Seovic'").getValue(context, inventor, String.class);
        System.out.println("aleks:" + aleks);
    }

    /**
     * Types
     * <p>
     * You can use the special T operator to specify an instance of java.lang.Class (the type).
     * Static methods are invoked by using this operator as well.
     * The StandardEvaluationContext uses a TypeLocator to find types,
     * and the StandardTypeLocator (which can be replaced) is built with an understanding of the java.lang package.
     * This means that
     * T() references to types within java.lang do not need to be fully qualified,
     * but all other type references must be.
     */
    @Test
    public void test8() {
        Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);

        Class stringClass = parser.parseExpression("T(String)").getValue(Class.class);

        boolean trueValue = parser.parseExpression(
                "T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR")
                .getValue(Boolean.class);
    }

    /**
     * Constructors
     * <p>
     * You can invoke constructors by using the new operator.
     * You should use the fully qualified class name for all but the primitive types
     * (int, float, and so on) and String.
     */
    @Test
    public void test9() {
        EvaluationContext societyContext = getEvaluationSocietyContext();

        Inventor einstein = parser.parseExpression(
                "new com.ais.brm.study.domain.Inventor('Albert Einstein', 'German')")
                .getValue(Inventor.class);

        System.out.println("examples before add=>" + MiscUtils.toPrettyJson(societyContext.getRootObject().getValue()));

        //create new inventor instance within add method of List
        parser.parseExpression(
                "Members.add(new com.ais.brm.study.domain.Inventor( 'Albert Einstein', 'German'))")
                .getValue(societyContext);

        System.out.println("examples after add=>" + MiscUtils.toPrettyJson(societyContext.getRootObject().getValue()));


    }

    /**
     * Variables
     * <p>
     * You can reference variables in the expression by using the #variableName syntax.
     * Variables are set by using the setVariable method on EvaluationContext implementations.
     * <p>
     */
    @Test
    public void test10() {
        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
        //不能使用 forReadOnlyDataBinding
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        context.setVariable("newName", "Mike Tesla");

        parser.parseExpression("Name = #newName").getValue(context, tesla);
        // "Mike Tesla"
        System.out.println(tesla.getName());


    }

    /**
     * https://stackoverflow.com/questions/19550323/root-and-this-in-spel
     * <br/>
     * The #this and #root Variables
     * The #this variable is always defined and refers to the current evaluation object
     * (against which unqualified references are resolved).
     * The #root variable is always defined and refers to the root context object.
     * Although #this may vary as components of an expression are evaluated, #root always refers to the root.
     */
    @Test
    public void test10_1() {
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        // create an array of integers
        List<Integer> primes = new ArrayList<>();
        primes.addAll(Arrays.asList(2, 3, 5, 7, 11, 13, 17));

        // create parser and set variable 'primes' as the array of integers
        context.setVariable("primes", primes);

        // all prime numbers > 10 from the list (using selection ?{...})
        // evaluates to [11, 13, 17]
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#primes.?[#this>10]").getValue(context);

        System.out.println(primesGreaterThanTen);
    }


    @Test
    public void test10_2() {
        EvaluationContext context = new StandardEvaluationContext();
        // create an array of integers
        List<Integer> primes = new ArrayList<>();
        primes.addAll(Arrays.asList(2, 3, 5, 7, 11, 13, 17));
        ((StandardEvaluationContext) context).setRootObject(primes);


        // create parser and set variable 'primes' as the array of integers
        context.setVariable("primes", primes);

        // evaluates to [2, 3, 5, 7, 11, 13, 17]
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#root").getValue(context);

        System.out.println(primesGreaterThanTen);
    }

    /**
     * Functions
     * You can extend SpEL by registering user-defined functions that can be called within the expression string.
     * The function is registered through the EvaluationContext. The following example shows how to register a user-defined function:
     */
    @Test
    public void test11() {
        Method method = MethodUtils.getAccessibleMethod(StringUtils.class, "join", Iterable.class, String.class);

        // create an array of integers
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        List<Integer> primes = Arrays.asList(2, 3, 5, 7);

        context.setVariable("primes", primes);
        //register function

        context.setVariable("myFunction", method);

        String joins = parser.parseExpression(
                "#myFunction(#primes,'#')").getValue(context, String.class);

        System.out.println(joins);
    }


    /**
     * Ternary Operator (If-Then-Else)
     * You can use the ternary operator for performing if-then-else conditional logic inside the expression.
     */
    @Test
    public void test13() {
        EvaluationContext societyContext = getEvaluationSocietyContext();


        parser.parseExpression("Name").setValue(societyContext, "IEEE");
        societyContext.setVariable("queryName", "Nikola Tesla");

        String expression = "isMember(#queryName)? #queryName + ' is a member of the ' " +
                "+ Name + ' Society' : #queryName + ' is not a member of the ' + Name + ' Society'";

        String queryResultString = parser.parseExpression(expression)
                .getValue(societyContext, String.class);
        //Nikola Tesla is not a member of the IEEE Society
        System.out.println("queryResultString:" + queryResultString);
    }

    /**
     * The Elvis Operator
     * The Elvis operator is a shortening of the ternary operator syntax and is used in the Groovy language.
     * With the ternary operator syntax, you usually have to repeat a variable twice,
     */
    @Test
    public void test14() {
        String name = "Elvis Presley";
        String displayName = (name != null ? name : "Unknown");

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
        name = parser.parseExpression("Name?:'Elvis Presley'").getValue(context, tesla, String.class);
        System.out.println(name);  // Nikola Tesla

        tesla.setName(null);
        name = parser.parseExpression("Name?:'Elvis Presley'").getValue(context, tesla, String.class);
        System.out.println(name);  // Elvis Presley
    }

    /**
     * . Safe Navigation Operator
     * <p>
     * The safe navigation operator is used to avoid a NullPointerException and comes from the Groovy language.
     * Typically, when you have a reference to an object,
     * you might need to verify that it is not null before accessing methods or properties of the object.
     * To avoid this, the safe navigation operator returns null instead of throwing an exception
     */
    @Test
    public void test15() {
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
        tesla.setPlaceOfBirth(new PlaceOfBirth("Smiljan"));

        String city = parser.parseExpression("PlaceOfBirth?.City").getValue(context, tesla, String.class);
        System.out.println(city);  // Smiljan

        tesla.setPlaceOfBirth(null);
        city = parser.parseExpression("PlaceOfBirth?.City").getValue(context, tesla, String.class);
        System.out.println(city);  // null - does not throw NullPointerException!!!
    }

    /**
     * Collection Selection
     * <p>
     * Selection is a powerful expression language feature
     * that lets you transform a source collection into another collection by selecting from its entries.
     * <p>
     * Selection uses a syntax of .?[selectionExpression].
     * It filters the collection and returns a new collection that contain a subset of the original elements.
     */
    @Test
    public void test16() {
        EvaluationContext societyContext = getEvaluationSocietyContext();

        List<Inventor> list = (List<Inventor>) parser.parseExpression(
                "Members.?[name == 'bmw']").getValue(societyContext);

        System.out.println("list:" + list);

        /**
         * Selection is possible upon both lists and maps.
         * For a list, the selection criteria is evaluated against each individual list element.
         * Against a map, the selection criteria is evaluated against each map entry (objects of the Java type Map.Entry).
         * Each map entry has its key and value accessible as properties for use in the selection.
         */

        //:{a=15, a4=10}
        Map newMap = (Map) parser.parseExpression("officers.?[value<27]").getValue(societyContext);
        System.out.println("newMap:" + newMap);
        /**
         * In addition to returning all the selected elements,
         * you can retrieve only the first or the last value. To obtain the first entry matching the selection,
         * the syntax is .^[selectionExpression]. To obtain the last matching selection,
         * the syntax is .$[selectionExpression].
         */
        //{a=15}
        newMap = (Map) parser.parseExpression("officers.^[value<27]").getValue(societyContext);
        System.out.println("newMap:" + newMap);


    }

    /**
     * Collection Projection
     * <p>
     * Projection lets a collection drive the evaluation of a sub-expression,
     * and the result is a new collection. The syntax for projection is .![projectionExpression].
     * <p>
     * <p>
     * You can also use a map to drive projection and, in this case,
     * the projection expression is evaluated against each entry in the map
     * (represented as a Java Map.Entry). The result of a projection across a map is a list
     * that consists of the evaluation of the projection expression against each map ent
     */
    @Test
    public void test17() {

        EvaluationContext societyContext = getEvaluationSocietyContext();

        // returns ['Smiljan', 'Idvor' ]
        List placesOfBirth = (List) parser.parseExpression("Members.![name]")
                .getValue(societyContext);
        System.out.println("names=" + placesOfBirth);

        /**
         * You can also use a map to drive projection and, in this case,
         * the projection expression is evaluated against each entry in the map
         * (represented as a Java Map.Entry). The result of a projection across a map is a list
         * that consists of the evaluation of the projection expression against each map entry.
         */
        List mapKeys = (List) parser.parseExpression("officers.![key]").getValue(societyContext);
        System.out.println("mapKeys=" + mapKeys);

    }


    /**
     * Expression templating
     * <p>
     * Expression templates allow mixing literal text with one or more evaluation blocks.
     * Each evaluation block is delimited with prefix
     * and suffix characters that you can define. A common choice is to use #{ } as the delimiters,
     */
    @Test
    public void test18() {
        String randomPhrase = parser.parseExpression(
                "random number is #{T(java.lang.Math).random()}",
                new TemplateParserContext()).getValue(String.class);

        // evaluates to "random number is 0.7038186818312008"
        System.out.println(randomPhrase);
    }

}


