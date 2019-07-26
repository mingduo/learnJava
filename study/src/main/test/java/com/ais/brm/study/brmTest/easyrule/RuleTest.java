package com.ais.brm.study.brmTest.easyrule;

import com.ais.brm.common.domain2.ruleegine.IndexExpression;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.support.ActivationRuleGroup;
import org.jeasy.rules.support.CompositeRule;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.optimizers.OptimizerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RuleTest {

    @Test
    public void testRules() throws Exception {
        Rule weatherRule = new MVELRule()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when("p > 10 ").then("");

        // define facts
        Facts facts = new Facts();
        facts.put("p", "14.0" );
      //  facts.put("cloudy",true);

        // define rules
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
     /*   Map<Rule, Boolean> check = rulesEngine.check(rules, facts);
        System.out.println(check);*/
    }

    @Test
    public void testRules2() throws Exception {
       IndexExpression idex1=new IndexExpression("IDX_1",null);
        IndexExpression idex2=new IndexExpression("IDX_2","20");
        Facts facts = new Facts();

        facts.put(idex1.getName(), idex1);
        facts.put(idex2.getName(), idex2);

        String expression ="IDX_1.value < 10 || IDX_2.value<100";

        Rule weatherRule = new MVELRule()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when(expression)
                .then("System.out.println(\"It rains and cloudy, take an umbrella!\");");

        OptimizerFactory.setDefaultOptimizer("ASM");

        System.out.println("result2: " + MVEL.executeExpression((MVEL.compileExpression(expression)), facts.asMap(), Boolean.class));
        System.out.println("result1: " + MVEL.eval(expression, facts.asMap()));
        System.out.println("result3: " +weatherRule.evaluate(facts) );

        // define facts
        //  facts.put("cloudy",true);
        // define rules
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

    }

    @Test
    public void testRules3() throws Exception {


        //OptimizerFactory.setDefaultOptimizer("ASM");
        IndexExpression idex1=new IndexExpression("IDX_1","20");
        IndexExpression idex2=new IndexExpression("IDX_2","20");

        Map<String, Object> vars = new HashMap<>();
        vars.put(idex1.getName(), idex1 );
        vars.put(idex2.getName(), idex2);

        Serializable compileExpression = MVEL.compileExpression("IDX_1.value>0 && IDX_2<100");
        ((Boolean)MVEL.executeExpression(compileExpression,vars)).booleanValue();


    }


    @Test
    public void  testRule4(){

        Rule weatherRule = new RuleBuilder()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when(facts -> facts.get("rain").equals(true))
                .then(facts -> System.out.println("It rains, take an umbrella!"))
                .build();
        Rule shopRule = new RuleBuilder()
                .name("shop rule")
                .when(facts -> facts.get("shop").equals(false))
                .then(facts -> System.out.println("get shop ok!"))
                .build();

       CompositeRule related=new ActivationRuleGroup("ActivationRuleGroup", "Activation of myRule1 or myRule2");
    //    CompositeRule related=new UnitRuleGroup("UnitRuleGroup", "unit of myRule1 and myRule2");
        related.addRule(weatherRule);
        related.addRule(shopRule);

        Facts facts = new Facts();
        facts.put("rain", false);
        facts.put("shop",false);
        // define rules
        Rules rules = new Rules();
        rules.register(related);

        System.out.println("result => "+related.evaluate(facts) );
        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

    }


}
