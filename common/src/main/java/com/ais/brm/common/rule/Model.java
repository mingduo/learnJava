package com.ais.brm.common.rule;

/**
 * 风控模型.
 * Created by zhaocw on 2016/6/16.
 * @author zhaocw
 */
public class Model {
    private int id;
    private String code;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
