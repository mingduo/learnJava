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
 * <tr><th>@Description:指标的值大于或者等于某个值</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance106")
public class BetweenThresholdRule extends RuleObject {

    @Override
    public void checkRuleIndex(int indexValueType) throws Exception {
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw new Exception("risk_index_type_id must be number");
        }
    }

    /***
     * <table border="1">
     * <tr><th>@Description:指标的值大于或者等于某个值</th></tr>
     * <tr><td>@Date Created in 2018-3-15</td></tr>
     * <tr><td>@param [ruleMap]</td></tr>
     * <tr><td>@return java.util.List<java.lang.String></td></tr>
     * <tr><td>@author weizc</td></tr>
     * </table>
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String upper = ruleMap.get("the_upper_threshold");
        String lower = ruleMap.get("the_lower_threshold");
        if (checkValueExits(upper, lower)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();

        //忽略空值 p==null || (p!=1&&p)
        sb.append("p").append(LQ).append(upper).append(AND);
        sb.append("p").append(MQ).append(lower);

        setCondtionValue(curHis, upper, lower);

        return sb.toString();
    }

    private void setCondtionValue(RiskIndexResultHis curHis, String upper, String lower) {
        StringBuffer condtionValue = new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]介于值范围[");
        condtionValue.append(lower);
        condtionValue.append(",");
        condtionValue.append(upper);
        condtionValue.append("]之间");
        Optional.ofNullable(curHis).ifPresent(t -> {
            t.setConditionValue(condtionValue.toString());
        });
    }

    public static void main(String[] args) {
        BetweenThresholdRule w = new BetweenThresholdRule();
        Map<String, String> m = new HashMap<>();
        m.put("the_upper_threshold", "a");
        m.put("ignore_null", "false");
        System.out.println(w.parseRuleParam(m, null));
    }
}
