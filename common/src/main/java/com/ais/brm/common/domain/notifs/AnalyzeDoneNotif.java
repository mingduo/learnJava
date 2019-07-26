package com.ais.brm.common.domain.notifs;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.Result;

/**
 * 代表一个分析任务完成.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class AnalyzeDoneNotif extends GeneralKakfaNotif {
    private int storageType;//结果保存到哪种介质，比如mysql,redis等
    private String storageDestUri;
    private String scene;
    private long doneTime;
    private Result result;

    public AnalyzeDoneNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_ANALYZE_TASKDONE);
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

    public void setDoneTime(long doneTime) {
        this.doneTime = doneTime;
    }

    public long getDoneTime() {
        return doneTime;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
