package com.ais.brm.common.ruleobject;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;

import java.util.Map;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-13</td>
 * </tr>
 * </table>
 *
 * @author : weizc
 */
public interface IRuleObject {
    //检查规则指标是否出现错误
    void checkRuleIndex(int indexValueType) throws Exception;

    //硬编码解析map 转化mvel表达式
    String parseRuleParam(Map<String, String> ruleMap, RiskIndexResultHis curHis);
}
