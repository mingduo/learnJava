package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.ruleobject.RuleObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <table border="1">
 * <tr><th>@Description: 只要指标的值和上次比较有变化就报警</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance103")
public class ChangeRule extends RuleObject {
    @Override
    public void checkRuleIndex(int indexValueType) {
    }

    /**只要指标的值和上次比较有变化就报警
     *
     * @param ruleMap
     * @param curHis
     * @return
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String ignore = ruleMap.get("ignore_null");
        if (checkValueExits(ignore)) {
            return null;
        }
        Boolean ignore_null = Boolean.parseBoolean(ignore);

        StringBuffer sb = new StringBuffer();
            String index_value = ruleMap.get("index_value" );
            String index_value_last = ruleMap.get("index_value_last");
            //忽略空值 index_value ==null 不报警
            if (StringUtils.isBlank(index_value) && ignore_null) {
                sb = sb.append(index_value).append(NOT_EQ).append(EMPTY);
            } else {
                sb = sb.append(index_value).append(NOT_EQ).
                        append(index_value_last);
            }
        setConditionValue(curHis, index_value, index_value_last);
        return sb.toString();
    }

    private void setConditionValue(RiskIndexResultHis curHis, String index_value, String index_value_last) {
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(index_value);
        condtionValue.append("]和上次[");
        condtionValue.append(index_value_last);
        condtionValue.append("]有变化");

        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });
    }

    public static void main(String[] args) {
        ChangeRule w = new ChangeRule();
        Map<String, String> m = new HashMap<>();
        m.put("white_list", "a c");
        m.put("ignore_null", "true");
        System.out.println(w.parseRuleParam(m, null));
    }
}
