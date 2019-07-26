package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.IMessage;
import com.google.gson.Gson;

import java.util.Date;

/**
 * .
 * Created by zhaocw on 2016-7-19.
 * @author zhaocw
 */
public class BrmLog implements IMessage{
    private String module;//哪个模块发的日志
    private String subModule;//具体哪个子模块
    private BrmLogLevel level;//日志级别，引用Constants常量.
    private Date date;
    private String message;

    private static Gson gson = new Gson();

    public String getSubModule() {
        return subModule;
    }

    public void setSubModule(String subModule) {
        this.subModule = subModule;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public BrmLogLevel getLevel() {
        return level;
    }

    public void setLevel(BrmLogLevel level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getContent() {
        return gson.toJson(this);
    }
}
