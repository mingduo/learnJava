package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.enumdomain.IndexValType;
import com.ais.brm.common.ruleobject.RuleObject;
import org.springframework.stereotype.Component;

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
@Component("RuleInstance104")
public class UpperThresholdRule extends RuleObject {
    @Override
    public void checkRuleIndex( int indexValueType) throws Exception {
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw  new Exception("risk_index_type_id must be number");
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
        String value = ruleMap.get("the_upper_threshold");
        if (checkValueExits(value)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //忽略空值 p==null || (p!=1&&p)
        sb.append("p").append(MQ)
            .append(value);

        setConditonValue(curHis, value);
        return sb.toString();
    }
    private void setConditonValue(RiskIndexResultHis curHis, String value) {
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]高于阀值[");
        condtionValue.append(value);
        condtionValue.append("]");

        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });
    }

}
