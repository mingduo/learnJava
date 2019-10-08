package com.ais.brm.common.domain;

/**
 * Created by zhaocw on 2016/5/5.
 *
 * @author zhaocw
 */
public class BrmException extends Exception {
    private Exception source;

    public BrmException(Exception e) {
        super(e);
        this.source = e;
    }

    public BrmException(String s) {
        super(s);
    }

    public BrmException(String s, Exception e) {
        super(s, e);
        this.source = e;
    }

    public Exception getSource() {
        return source;
    }

    public void setSource(Exception source) {
        this.source = source;
    }
}
