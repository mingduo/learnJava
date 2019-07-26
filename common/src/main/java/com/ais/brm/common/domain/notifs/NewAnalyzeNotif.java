package com.ais.brm.common.domain.notifs;

import com.ais.brm.common.Constants;

/**
 * 一个新的分析请求.接收者是analyze模块.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class NewAnalyzeNotif extends GeneralKakfaNotif {
    private String scene;//分析哪个场景.
    private String targetClass;

    public NewAnalyzeNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_ANALYZE);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }
}
