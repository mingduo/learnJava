package com.ais.brm.common.domain.notifs;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.Result;

/**
 * 代表一个采集任务完成.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class DataCollectDoneNotif extends GeneralKakfaNotif {
    private int storageType;
    private String storageDestUri;
    private String scene;
    private long dateRangeFrom;
    private long dateRangeTo;
    private long doneTime;
    private Result result;
    private String targetClass;

    public DataCollectDoneNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_DATACOLLECT_TASKDONE);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageDestUri(String storageDestUri) {
        this.storageDestUri = storageDestUri;
    }

    public String getStorageDestUri() {
        return storageDestUri;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getScene() {
        return scene;
    }

    public void setDateRangeFrom(long dateRangeFrom) {
        this.dateRangeFrom = dateRangeFrom;
    }

    public long getDateRangeFrom() {
        return dateRangeFrom;
    }

    public void setDateRangeTo(long dateRangeTo) {
        this.dateRangeTo = dateRangeTo;
    }

    public long getDateRangeTo() {
        return dateRangeTo;
    }

    public void setDoneTime(long doneTime) {
        this.doneTime = doneTime;
    }

    public long getDoneTime() {
        return doneTime;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }
}
