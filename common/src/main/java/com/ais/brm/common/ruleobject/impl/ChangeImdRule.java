package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.enumdomain.IndexValType;
import com.ais.brm.common.ruleobject.RuleObject;
import com.ais.brm.common.utils.FormatUtis;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <table border="1">
 * <tr><th>@Description:指标的值和上次的值比较有比较大的变化，包括突增和突减</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance108")
public class ChangeImdRule extends RuleObject {
    @Override
    public void checkRuleIndex(int indexValueType) throws  Exception {
        if (indexValueType != IndexValType.NUM_TYPE.getTypeId()) {
            throw  new Exception("risk_index_type_id must be number");
        }
    }

    /***
     * <table border="1">
     * <tr><th>@Description:指标的值和上次的值比较有比较大的变化</th></tr>
     * <tr><td>@Date Created in 2018-3-15</td></tr>
     * <tr><td>@param [ruleMap]</td></tr>
     * <tr><td>@return java.util.List<java.lang.String></td></tr>
     * <tr><td>@author weizc</td></tr>
     * </table>
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String spike_type = ruleMap.get("spike_type");
        String spike_rate = ruleMap.get("spike_rate");

        //低于此值才告警，可以不设置
        String min_threshold = ruleMap.get("min_threshold");
        //高于此值才告警，可以不设置
        String max_threshold = ruleMap.get("max_threshold");

        if (checkValueExits(spike_type, spike_rate)) {
            return null;
        }
        List<Map.Entry<String, String>> indexList = ruleMap.entrySet().stream()
                .filter((t) -> t.getKey().startsWith("index_value"))
                .collect(Collectors.toList());
        StringBuffer sb = new StringBuffer();
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());

        BigDecimal index_value = new BigDecimal(ruleMap.get("index_value"));
        BigDecimal index_value_last = new BigDecimal(ruleMap.get("index_value_last"));
        double d = 0;
        if ("up".equalsIgnoreCase(spike_type)) {
            d = index_value.subtract(index_value_last)
                    .divide(index_value_last, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            condtionValue.append("]大于等于");
        } else if ("down".equalsIgnoreCase(spike_type)) {
            d = Math.abs(index_value_last.subtract(index_value)
                    .divide(index_value_last, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
            condtionValue.append("]小于等于");

        }
        condtionValue.append("上次值[");
        condtionValue.append(index_value_last.doubleValue());
        condtionValue.append("]的");
        condtionValue.append(FormatUtis.toPercent(d));


        sb = sb.append(d).append(MQ).append(spike_rate);
        if (StringUtils.isNotBlank(min_threshold)) {
            String s=String.format(",或指标值低于最低阀值[%s]",min_threshold);
            condtionValue.append(s);
            sb.append(OR).append(curHis.getRiskIndexValue()).append(LESS)
                    .append(min_threshold);
        }
        if (StringUtils.isNotBlank(max_threshold)) {
            sb.append(OR).append(curHis.getRiskIndexValue()).append(MORE)
                    .append(max_threshold);
            String s=String.format(",或指标值高于最高阀值[%s]",max_threshold);
            condtionValue.append(s);
        }
        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });

        return sb.toString();
    }

    public static void main(String[] args) {
        ChangeImdRule w = new ChangeImdRule();
        Map<String, String> m = new HashMap<>();
        m.put("index_value", "11");
        m.put("index_value_last", "2");
        m.put("spike_type", "down");
        m.put("spike_rate", "0.2");
        m.put("max_threshold", "4.2");

        RiskIndexResultHis riskIndexResultHis = new RiskIndexResultHis();

        String s = w.parseRuleParam(m, riskIndexResultHis);
        System.out.println(s);
        System.out.println(riskIndexResultHis.getConditionValue());

    }
}
