package com.ais.brm.common.rule;

/**
 * 场景.
 * Created by zhaocw on 2016/6/16.
 * @author zhaocw
 */
public class Scene {
    private String code;
    private String name;
    private String description;
    private Scene parent;
    private boolean isLeaf;
    private boolean isRoot;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Scene getParent() {
        return parent;
    }

    public void setParent(Scene parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }
}
