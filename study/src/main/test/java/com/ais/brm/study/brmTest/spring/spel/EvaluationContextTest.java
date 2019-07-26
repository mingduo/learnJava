package com.ais.brm.study.brmTest.spring.spel;

import lombok.ToString;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/3/29
 */
public class EvaluationContextTest {
    ExpressionParser parser = new SpelExpressionParser();

    //Type Conversion
    @Test
    public void test1() {
        Simple simple = new Simple();
        simple.booleanList.add(true);
        simple.booleanList.add(true);


        SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        // "false" is passed in here as a String. SpEL and the conversion service
// will recognize that it needs to be a Boolean and convert it accordingly.
        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");

        // b is false
        Boolean b = simple.booleanList.get(0);
        System.out.println(simple);

    }

   /* @Test
    public void test2() {
        PersonRepositoryImpl personRepository = new PersonRepositoryImpl();
        Method add = MethodUtils.getAccessibleMethod(PersonRepositoryImpl.class, "add", String.class, String.class);

        ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

        CacheExpressionRootObject rootObject = new CacheExpressionRootObject(
                caches, method, args, target, targetClass);

        MethodBasedEvaluationContext basedEvaluationContext = new MethodBasedEvaluationContext
                (add, add, new Object[]{"1", "a"}, nameDiscoverer);
        String value = (String) parser.parseExpression("#root.args[0]").getValue(basedEvaluationContext);
        System.out.println(value);

    }*/

}

@ToString
class Simple {
    public List<Boolean> booleanList = new ArrayList<>();
}