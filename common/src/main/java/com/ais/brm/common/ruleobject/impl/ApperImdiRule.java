package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.ruleobject.RuleObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <table border="1">
 * <tr><th>@Description:指标的值在所有此类监控对象范围内以前从未见</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance109")
public class ApperImdiRule extends RuleObject {
    @Override
    public void checkRuleIndex(int indexValueType) {
    }

    //指标的值在所有此类监控对象范围内以前从未见
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String ignore = ruleMap.get("ignore_null");
        String day_range = ruleMap.get("day_range");
        if (checkValueExits(ignore, day_range)) {
            return null;
        }


        Boolean ignore_null = Boolean.parseBoolean(ignore);
        String index_value_all = ruleMap.get("all_index_value");
        final List<String> value_all = Arrays.asList(index_value_all.split(","));


        StringBuffer sb = new StringBuffer();
        String idx_value = ruleMap.get("index_value");
        if (StringUtils.isBlank(idx_value) && ignore_null) {
            sb = sb.append(idx_value).append(NOT_EQ).append(EMPTY);
        } else if (!value_all.contains(idx_value)) {
            sb = sb.append("true");
        } else {
            sb = sb.append("false");
        }
        setConditionValue(curHis, getIdxValAll(index_value_all, value_all));

        return sb.toString();
    }

    //获取长度小于等于5的指标值
    private String getIdxValAll(String index_value_all, List<String> value_all) {
        if (value_all.size() > 5) {
            return value_all.stream().filter(s -> value_all.indexOf(s) < 5)
                    .collect(Collectors.joining(",")).concat("...");
        }
        return index_value_all;

    }

    private void setConditionValue(RiskIndexResultHis curHis, String index_value_all) {
        StringBuffer condtionValue=new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]在当前指标值范围[");
        condtionValue.append(index_value_all);
        condtionValue.append("]中从未见");
        Optional.ofNullable(curHis).ifPresent(t->{
            t.setConditionValue(condtionValue.toString());
        });
    }

    public static void main(String[] args) {
        String index_value_all="1,2,5,3,4,5,7";
        final List<String> value_all = Arrays.asList(index_value_all.split(","));

        if(value_all.size()>5){
            String s1 = value_all.stream().filter(s -> value_all.indexOf(s) < 5).collect(Collectors.joining(","))+"...";
            System.out.println("args = [" + s1 + "]");
        }


    }
}
