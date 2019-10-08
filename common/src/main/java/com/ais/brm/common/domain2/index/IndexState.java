package com.ais.brm.common.domain2.index;

/**
 * 指标中间状态实体
 *
 * @author lulj
 * @since 2018/03/22
 */
public class IndexState implements Cloneable {
    // 指标ID
    private int indexId;

    // 监控对象类型
    private int riskObjectTypeId;

    // 监控对象ID
    private String riskObjectId;

    // 维度值（维度类型通过指标ID关联间接获得）
    private String dimensionId;

    // 指标值
    private Object value;

    // 事件发生时间
    private long eventTimestamp;

    // 状态更新时间
    private long lastTimestamp;

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public String getRiskObjectId() {
        return riskObjectId;
    }

    public void setRiskObjectId(String riskObjectId) {
        this.riskObjectId = riskObjectId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    @Override
    public IndexState clone() {
        try {
            return (IndexState) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;// clone failed
        }
    }

    public static IndexState create(int objectType, String objectId,
                                    int indexId, String dimensionId) {
        IndexState state = new IndexState();
        state.setRiskObjectTypeId(objectType);
        state.setRiskObjectId(objectId);
        state.setIndexId(indexId);
        state.setDimensionId(dimensionId);
        return state;
    }

    public static IndexState create(int objectType, String objectId,
                                    int indexId, String dimensionId, Object value) {
        IndexState state = new IndexState();
        state.setRiskObjectTypeId(objectType);
        state.setRiskObjectId(objectId);
        state.setIndexId(indexId);
        state.setDimensionId(dimensionId);
        state.setValue(value);
        return state;
    }

    @Override
    public String toString() {
        return "{indexId: " + indexId +
                ", riskObjectTypeId: " + riskObjectTypeId +
                ", riskObjectId: " + riskObjectId +
                ", dimensionId: " + dimensionId +
                ", value: " + value +
                ", eventTimestamp: " + eventTimestamp +
                ", lastTimestamp: " + lastTimestamp +
                "}";
    }
}
