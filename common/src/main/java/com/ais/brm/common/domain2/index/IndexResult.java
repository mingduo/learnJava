package com.ais.brm.common.domain2.index;

import java.sql.Timestamp;

/**
 * 指标结果实体
 *
 * @author by gengrc
 * @since 2018/4/26
 */
public class IndexResult extends IndexState {
    //上次指标值


    private Object lastValue;
    private Timestamp collectTime;
    private Timestamp saveTime;
    private Timestamp lastUpdateTime;

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public Timestamp getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Timestamp collectTime) {
        this.collectTime = collectTime;
    }

    public Object getLastValue() {
        return lastValue;
    }

    public void setLastValue(Object lastValue) {
        this.lastValue = lastValue;
    }

    public Timestamp getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Timestamp saveTime) {
        this.saveTime = saveTime;
    }
}
