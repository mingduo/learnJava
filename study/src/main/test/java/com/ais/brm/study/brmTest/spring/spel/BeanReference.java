package com.ais.brm.study.brmTest.spring.spel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.env.Environment;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : weizc
 * @description: https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#expressions-language-ref
 * @since 2019/3/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BeanReference implements BeanFactoryAware {

    private BeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }
    /**
     * . Bean References
     *
     * If the evaluation context has been configured with a bean resolver,
     * you can look up beans from an expression by using the @ symbol
     */
    @Test
    public void test12() {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(beanFactory));

// This will end up calling resolve(context,"something") on MyBeanResolver during evaluation
        Environment env = parser.parseExpression("@environment").getValue(context,Environment.class);
        System.out.println( "env="+env);
    }


}


