package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.enumdomain.IndexValType;
import com.ais.brm.common.ruleobject.RuleObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance107")
public class NotBetweenThresholdRule extends RuleObject {
    @Override
    public void checkRuleIndex(int indexValueType) throws Exception {
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw new Exception("risk_index_type_id must be number");
        }
    }

    //指标的值大于或者等于某个值
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String upper = ruleMap.get("the_upper_threshold");
        String lower = ruleMap.get("the_lower_threshold");
        if (checkValueExits(upper, lower)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //忽略空值 p==null || (p!=1&&p) 20 ,10
        sb.append("p").append(MORE).
                append(upper).append(OR);
        sb.append("p").append(LESS)
                .append(lower);

        StringBuffer condtionValue = new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]不介于值范围[");
        condtionValue.append(lower);
        condtionValue.append(",");
        condtionValue.append(upper);
        condtionValue.append("]之间");

        Optional.ofNullable(curHis).ifPresent(t -> {
            t.setConditionValue(condtionValue.toString());
        });
        return sb.toString();
    }

    public static void main(String[] args) {
        NotBetweenThresholdRule w = new NotBetweenThresholdRule();
        Map<String, String> m = new HashMap<>();
        m.put("the_upper_threshold", "a");
        m.put("the_lower_threshold", "1");
        System.out.println(w.parseRuleParam(m, null));
    }
}
