package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.enumdomain.IndexValType;
import com.ais.brm.common.ruleobject.RuleObject;
import com.ais.brm.common.utils.FormatUtis;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <table border="1">
 * <tr><th>@Description:指标的值和此监控对象此前的平均值比较有突然的变化</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance110")
public class NotMatchAvgRule extends RuleObject {
    @Override
    public void checkRuleIndex( int indexValueType) throws Exception {
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw  new Exception("risk_index_type_id must be number");
        }
    }

    /***
     * <table border="1">
     * <tr><th>@Description:指标的值和此监控对象此前的平均值比较有突然的变化</th></tr>
     * <tr><td>@Date Created in 2018-3-15</td></tr>
     * <tr><td>@param [ruleMap]</td></tr>
     * <tr><td>@return java.util.List<java.lang.String></td></tr>
     * <tr><td>@author weizc</td></tr>
     * </table>
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String spike_type = ruleMap.get("avg_spike_type");
        String spike_rate = ruleMap.get("avg_spike_rate");
        String day_range = ruleMap.get("day_range");
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        BigDecimal idx_value_avg = new BigDecimal(ruleMap.get("avg_index_value"));
        //低于此值才告警，可以不设置
        if (checkValueExits(spike_type, spike_rate,day_range)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        BigDecimal index_value = new BigDecimal(ruleMap.get("index_value"));
        double d = 0;
        if ("up".equalsIgnoreCase(spike_type)) {
            condtionValue.append("]大于等于");
            d = index_value.subtract(idx_value_avg)
                    .divide(idx_value_avg, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if ("down".equalsIgnoreCase(spike_type)) {
            condtionValue.append("]小于等于");
            d = Math.abs(idx_value_avg.subtract(index_value)
                    .divide(idx_value_avg, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        sb = sb.append(d).append(MQ).append(spike_rate);


        condtionValue.append("平均值[");
        condtionValue.append(FormatUtis.toDecimal(idx_value_avg.doubleValue()));
        condtionValue.append("]的");

        condtionValue.append(FormatUtis.toPercent(d));
        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });
        return sb.toString();
    }


    public static void main(String[] args) {
        NotMatchAvgRule w = new NotMatchAvgRule();
        Map<String, String> m = new HashMap<>();
        m.put("index_value", "18");
        m.put("day_range", "1");
        m.put("avg_spike_type", "down");
        m.put("avg_spike_rate", "0.2");

        m.put("avg_index_value", "12");
        RiskIndexResultHis riskIndexResultHis = new RiskIndexResultHis();

        String s = w.parseRuleParam(m, riskIndexResultHis);
        System.out.println(s);
        System.out.println(riskIndexResultHis.getConditionValue());

    }
}
