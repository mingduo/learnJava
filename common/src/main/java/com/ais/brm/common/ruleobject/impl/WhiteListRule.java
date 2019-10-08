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
 * <tr><th>@Description:只要指标的值不在白名单内就报警</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance102")
public class WhiteListRule extends RuleObject {
    @Override
    public void checkRuleIndex(int indexValueType) {
    }

    /**
     * 只要指标的值不在白名单内就报警
     *
     * @param ruleMap
     * @param curHis
     * @return
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String white_list = ruleMap.get("white_list");
        String ignore = ruleMap.get("ignore_null");
        if (checkValueExits(white_list, ignore)) {
            return null;
        }
        Boolean ignore_null = Boolean.parseBoolean(ignore);

        StringBuffer sb = new StringBuffer();
        //忽略空值 p==null || (p!=1&&p)
        if (ignore_null) {
            sb = sb.append("p").append(EQ).append(EMPTY).append(OR);
        } else {
            sb = sb.append("p").append(NOT_EQ).append(EMPTY).append(AND);

        }
        sb.append(LEFT);
        for (String t : white_list.split(SPACE)) {
            if (StringUtils.isNumeric(t)) {
                sb.append("p").append(NOT_EQ)
                        .append(t)
                        .append(AND);
            } else {
                sb.append("p").append(NOT_EQ)
                        .append(POINT).append(t)
                        .append(POINT).append(AND);
            }
        }
        sb.delete(sb.lastIndexOf(AND), sb.length()).append(RIGHT);

        setConditionValue(curHis, white_list);

        return sb.toString();
    }

    private void setConditionValue(RiskIndexResultHis curHis, String white_list) {
        StringBuffer condtionValue = new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]不在白名单[");
        condtionValue.append(white_list);
        condtionValue.append("]内");
        Optional.ofNullable(curHis).ifPresent(t -> {
            t.setConditionValue(condtionValue.toString());
        });
    }

    public static void main(String[] args) {
        WhiteListRule w = new WhiteListRule();
        Map<String, String> m = new HashMap<>();
        m.put("white_list", "a c 1");
        m.put("ignore_null", "true");
        RiskIndexResultHis riskIndexResultHis = new RiskIndexResultHis();

        String s = w.parseRuleParam(m, riskIndexResultHis);
        System.out.println(s);
        System.out.println(riskIndexResultHis.getConditionValue());
    }
}
