package com.ais.brm.common.ruleobject.impl;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.ruleobject.RuleObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <table border="1">
 * <tr><th>@Description:只要指标的值在黑名单内就报警</th></tr>
 * <tr><td>@Date:Created in 2018-3-9</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Component("RuleInstance101")
public class BlackListRule extends RuleObject {


    @Override
    public void checkRuleIndex(int indexValueType) {
    }

    /**
     * 只要指标的值在黑名单内就报警
     *
     * @param ruleMap
     * @param curHis
     * @return List<String>
     */
    @Override
    public String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis) {
        String black_list = ruleMap.get("black_list");
        if (checkValueExits(black_list)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //以空格分隔
        Stream.of(black_list.split(SPACE)).forEach(t -> {
            if (StringUtils.isNumeric(t)) {
                sb.append("p").
                        append(EQ).append(t).append(OR);
            } else {
                sb.append("p").
                        append(EQ).append(POINT).append(t).
                        append(POINT).append(OR);
            }

        });

        sb.delete(sb.lastIndexOf(OR), sb.length());

        setConditionValue(curHis, black_list);
        return sb.toString();
    }

    private void setConditionValue(RiskIndexResultHis curHis, String black_list) {
        StringBuffer condtionValue = new StringBuffer("当前指标值[");
        condtionValue.append(curHis.getRiskIndexValue());
        condtionValue.append("]在黑名单[");
        condtionValue.append(black_list);
        condtionValue.append("]内");
        Optional.ofNullable(curHis).ifPresent(t -> {
            t.setConditionValue(condtionValue.toString());
        });
    }


}
