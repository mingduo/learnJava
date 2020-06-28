package easyrule.notifs;

import easyrule.ruleegine.Constants;

/**
 * 一个新的读取数据请求.接收者是datareader模块.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class NewDataReadNotif extends GeneralKakfaNotif {
    private String scene;//读取哪个场景.
    private String targetClass;

    public NewDataReadNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_NEWDATAREAD);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetClass() {
        return targetClass;
    }
}
