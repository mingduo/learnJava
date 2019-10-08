package com.ais.brm.common.domain.notifs;

import com.ais.brm.common.domain.IKafkaNotif;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 基础的kafka通知，其他模块可以继承此类实现自己的通知逻辑.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class GeneralKakfaNotif implements IKafkaNotif {
    private static Gson gson = new Gson();

    private String notifId;//全局唯一通知ID.
    private String source;//who send this notify.
    private long sendTime = System.currentTimeMillis();//when send
    private int type; //类型，子类继承后，应该填写此字段，便于consumer消费.
    private Map<String, String> props;
    private static Random random = new Random();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public GeneralKakfaNotif() {
        synchronized (this) {
            notifId = String.valueOf(getType()) + "_" +
                    sdf.format(new Date()) + "_" +
                    random.nextInt(Integer.MAX_VALUE);
        }
    }

    @Override
    public String getContent() {
        return gson.toJson(this);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    @Override
    public String toString() {
        return "KakfaNotif{" +
                "notifId='" + notifId + '\'' +
                ", type=" + type +
                '}';
    }
}
