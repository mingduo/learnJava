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
@Component("RuleInstance105")
public class LowerThresholdRule extends RuleObject {
    @Override
    public void checkRuleIndex( int indexValueType) throws Exception{
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw  new Exception("risk_index_type_id must be number");
        }
    }

    //指标的值小于或者等于某个值
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String value = ruleMap.get("the_lower_threshold");
        if(checkValueExits(value)){
            return null;
        }
        StringBuffer sb=new StringBuffer();
        //忽略空值 p==null || (p!=1&&p)
       sb.append("p").append(LQ).append(value);
        setConditonValue(curHis, value);
        return sb.toString();
    }

    private void setConditonValue(RiskIndexResultHis curHis, String value) {
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]低于阀值[");
        condtionValue.append(value);
        condtionValue.append("]");

        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });
    }

    public static void main(String[] args) {
        LowerThresholdRule w=new LowerThresholdRule();
        Map<String,String>m=new HashMap<>();
        m.put("the_lower_threshold","a");
        m.put("ignore_null","false");
        System.out.println(w.parseRuleParam(m, null));
    }
}
