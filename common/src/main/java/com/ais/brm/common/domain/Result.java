package com.ais.brm.common.domain;

import com.ais.brm.common.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 代表一个执行结果.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class Result {
    private int resultCode;
    private String resultMessage;
    private Map<String,String> props = new HashMap<>();

    public Result(int code) {
        this.resultCode = code;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * .
     * @return
     */
    public static Result successResult() {
        return new Result(Constants.RESULTCODE_SUCCESS);
    }

    /**
     * .
     * @return
     */
    public static Result failedResult() {
        return new Result(Constants.RESULTCODE_FAILED);
    }

    /**
     * .
     * @param s
     * @return
     */
    public static Result failedResult(String s) {
        Result result= new Result(Constants.RESULTCODE_FAILED);
        result.setResultMessage(s);
        return result;
    }

    /**
     * .
     * @param s
     * @return
     */
    public static Result successResult(String s) {
        Result result= new Result(Constants.RESULTCODE_SUCCESS);
        result.setResultMessage(s);
        return result;
    }

    /**
     * .
     * @return
     */
    public boolean isSucceed() {
        return getResultCode()==Constants.RESULTCODE_SUCCESS;
    }

    /**
     * .
     * @return
     */
    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
