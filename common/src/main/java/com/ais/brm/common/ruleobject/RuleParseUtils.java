package com.ais.brm.common.ruleobject;

import com.ais.brm.common.domain2.RiskIndexAnalyzeNotif;
import com.ais.brm.common.domain2.ruleegine.*;
import com.ais.brm.common.enumdomain.IndexValType;
import com.ais.brm.common.enumdomain.RiskIndexSingleMonRuleType;
import org.javatuples.Pair;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.mvel.MVELRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Map;

/**
 * <table border="1">
 * <tr><th>@Description: 规则解析方法</th></tr>
 * <tr><td>@Date:Created in 2018-3-16</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RuleParseUtils {
    static Logger logger = LoggerFactory.getLogger(RuleParseUtils.class);


    //注册规则
    public static String registMvelRule(RuleObject bean, RiskIndexRule rule, Map<String, String> ruleParam,
                                        Map<RiskIndexResultHis, MVELRule> m,
                                        RiskIndexResultHis curHis) throws Exception {
        String condition = bean.parseRuleParam(ruleParam, curHis);
        if (condition == null) {
            throw new Exception("condtion cannot be null" +
                    ",some params may be wrong");
        }
        MVELRule mvelRule = new MVELRule()
                .name(rule.getName())
                .description(rule.getDescription())
                .when(condition);
        m.put(curHis, mvelRule);
        return condition;
    }

    //注册规则
    public static String registPairRule(RuleObject bean, RiskIndexRule rule, Map<String, String> ruleParam,
                                        Map<RiskIndexResultHis, Pair<RiskIndexRule, MVELRule>> m,
                                        RiskIndexResultHis curHis) throws Exception {
        String condition = bean.parseRuleParam(ruleParam, curHis);
        if (condition == null) {
            throw new Exception("condtion cannot be null" +
                    ",some params may be wrong");
        }
        MVELRule mvelRule = new MVELRule()
                .name(rule.getName())
                .description(rule.getDescription())
                .when(condition);
        m.put(curHis, Pair.with(rule, mvelRule));
        return condition;
    }

    //执行mvel表达式
    public static boolean isHisIndexSatisfy(MVELRule mvelRule, RiskIndexResultHis his) {
        Facts facts = his.getFacts(his.getRiskIndexValue());
        logger.info("get RISK_INDEX_VALUE:{} -> facts :{}", his.getRiskIndexValue(), facts);
        try {
            boolean b = mvelRule.evaluate(facts);
            return b;
        } catch (Exception e) {
            logger.info("rule evaluate false:", e.getMessage());
            return false;
        }
    }

    //获取job_trace对象
    public static RiskIndexAnalyzeTrace getRiskIndexAnalyzeTrace
    (RiskIndexAnalyzeNotif riskIndexAnalyzeNotif,
     int isSuccess, Timestamp analyzeBegTime, Timestamp analyzeEndTime) {
        RiskIndexAnalyzeTrace trace = new RiskIndexAnalyzeTrace();
        String jobId = riskIndexAnalyzeNotif.getProps().get("jobId");
        trace.setJobId(Long.parseLong(jobId));
        trace.setStartTime(new Timestamp(riskIndexAnalyzeNotif.getSendTime()));
        trace.setEndTime(new Timestamp(System.currentTimeMillis()));
        trace.setAnalyzeStartTime(analyzeBegTime);
        trace.setAnalyzeEndTime(analyzeEndTime);

        //成功
        trace.setIsSuccess(isSuccess);
        trace.setRuleId(riskIndexAnalyzeNotif.getRuleId());
        return trace;
    }

    //获取告警对象
    public static RiskIndexAlerts getRiskIndexAlerts(RiskIndexRule rule, RiskIndexResultHis t) {
        RiskIndexAlerts alerts = new RiskIndexAlerts();
        alerts.setAlertTime(new Timestamp(System.currentTimeMillis()));
        alerts.setIsRead(0);
        alerts.setRiskObjectId(t.getRiskObjectId());
        alerts.setRiskObjectTypeId(t.getRiskObjectTypeId());
        alerts.setRiskIndexId(t.getRiskIndexId());
        StringBuffer sb = new StringBuffer(t.getConditionValue());
        String typeName = RiskIndexSingleMonRuleType
                .getMonitorType(rule.getRuleType()).getTypeName();
        sb.append(",触发告警, 匹配规则类型:[").append(typeName).append("]");
        // sb.append(",指标采集时间:[").append(DateUtils.getFormatTime(t.getCollectTime()));
        sb.append("]");
        alerts.setAlertContent(sb.toString());
        alerts.setRuleId(rule.getId());
        return alerts;
    }

    //解析指标值
    public static String getIndexValue(Object value, TblRiskIndex riskIndex) {
        String indexValue = null;
        if (riskIndex.getIndexValueType() == IndexValType.NUM_TYPE.getTypeId()) {
            String s = value.toString();
            double d = Double.parseDouble(s);
            indexValue = String.valueOf(d);
        } else if (riskIndex.getIndexValueType() == IndexValType.ENUM_TYPE.getTypeId()) {

        } else if (riskIndex.getIndexValueType() == IndexValType.STR_TYPE.getTypeId()) {
            indexValue = String.valueOf(value);
        }
        return indexValue;
    }


}
