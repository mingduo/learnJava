package easyrule.notifs;

import easyrule.ruleegine.Constants;

/**
 * 同步数据通知.接收者是sync模块.
 * Created by xuechen on 2016/11/21.
 * @author xuechen
 */
public class SyncDataNotice extends GeneralKakfaNotif {
    private String taskId;//任务id
    public SyncDataNotice() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_SYNCDATA);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
