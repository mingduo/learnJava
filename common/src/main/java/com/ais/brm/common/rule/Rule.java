package com.ais.brm.common.rule;

import java.util.Date;

/**
 * 代表一条风控规则，具体设计请参看《架构设计》.doc
 * Created by zhaocw on 2016/6/16.
 * @author zhaocw
 */
public class Rule {
    private int level;
    private String bpl;
    private String createUser;
    private Date createDate;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBpl() {
        return bpl;
    }

    public void setBpl(String bpl) {
        this.bpl = bpl;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
