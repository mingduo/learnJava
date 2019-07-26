package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * 风险点的多指标分析请求.接收者是re模块.
 *
 * @author weizc
 */
public class MultiIndexAnalyzeNotif extends GeneralKakfaNotif {
    @SerializedName("the_day")
    private int theDay;

    @SerializedName("the_hour")
    private int theHour;

    @SerializedName("mul_rule_id")
    private int mulRuleId;

    @SerializedName("risk_object_type_id")
    private int riskObjectTypeId;

    @SerializedName("risk_object_id_submit_type")
    private String riskObjectIdSubmitType;

    @SerializedName("risk_object_id_single_content")
    private String riskObjectIdSingleContent;

    @SerializedName("risk_object_id_batch_content")
    private List<String> riskObjectIdBatchContent;

    @SerializedName("custom_params")
    private Map<String, String> customParams;

    public MultiIndexAnalyzeNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_RISKANALYZE);
    }

    public int getTheDay() {
        return theDay;
    }

    public void setTheDay(int theDay) {
        this.theDay = theDay;
    }

    public int getTheHour() {
        return theHour;
    }

    public void setTheHour(int theHour) {
        this.theHour = theHour;
    }

    public int getMulRuleId() {
        return mulRuleId;
    }

    public void setMulRuleId(int mulRuleId) {
        this.mulRuleId = mulRuleId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public String getRiskObjectIdSubmitType() {
        return riskObjectIdSubmitType;
    }

    public void setRiskObjectIdSubmitType(String riskObjectIdSubmitType) {
        this.riskObjectIdSubmitType = riskObjectIdSubmitType;
    }

    public String getRiskObjectIdSingleContent() {
        return riskObjectIdSingleContent;
    }

    public void setRiskObjectIdSingleContent(String riskObjectIdSingleContent) {
        this.riskObjectIdSingleContent = riskObjectIdSingleContent;
    }

    public List<String> getRiskObjectIdBatchContent() {
        return riskObjectIdBatchContent;
    }

    public void setRiskObjectIdBatchContent(List<String> riskObjectIdBatchContent) {
        this.riskObjectIdBatchContent = riskObjectIdBatchContent;
    }

    public Map<String, String> getCustomParams() {
        return customParams;
    }

    public void setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
    }
}
