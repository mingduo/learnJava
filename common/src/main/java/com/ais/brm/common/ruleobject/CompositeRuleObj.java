package com.ais.brm.common.ruleobject;

import org.jeasy.rules.core.CompositeRule;

/**
 * <table border="1">
 * <tr><th>@Description: 用于多指标规则匹配</th></tr>
 * <tr><td>@Date:Created in 2018-4-2</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class CompositeRuleObj extends CompositeRule {

    public CompositeRuleObj(Object... rules) {
        for (Object rule : rules) {
            addRule(rule);
        }
    }
}
