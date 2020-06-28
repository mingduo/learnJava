package easyrule.notifs;

import easyrule.ruleegine.Constants;

/**
 * 代表一个数据读取任务完成.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class DataReadDoneNotif extends GeneralKakfaNotif {
    private int storageType;//结果保存到哪种介质，比如mysql,redis等
    private String storageDestUri;
    private String scene;
    private long dateRangeFrom;
    private long dateRangeTo;
    private long doneTime;
    private Result result;
    private String targetClass;

    public DataReadDoneNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_DATACOLLECT_TASKDONE);
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }
}
