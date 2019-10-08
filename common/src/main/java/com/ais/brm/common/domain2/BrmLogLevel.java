package com.ais.brm.common.domain2;

/**
 * Created by zhaocw on 2016-7-19.
 *
 * @author zhaocw
 */
public enum BrmLogLevel {
    DEBUG(1), INFO(2), WARN(3), ERROR(4), FATAL(5);

    private int value;

    BrmLogLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
