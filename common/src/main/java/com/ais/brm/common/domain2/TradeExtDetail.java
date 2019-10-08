package com.ais.brm.common.domain2;

/**
 * @author zhaocw.
 */
public class TradeExtDetail {
    private long riskResultDetailId;
    private String tradeId;
    private long specId;
    private String jsonValues;
    private String description;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRiskResultDetailId() {
        return riskResultDetailId;
    }

    public void setRiskResultDetailId(long riskResultDetailId) {
        this.riskResultDetailId = riskResultDetailId;
    }

    public String getJsonValues() {
        return jsonValues;
    }

    public void setJsonValues(String jsonValues) {
        this.jsonValues = jsonValues;
    }
}
