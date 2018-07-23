package kl.challenge.consumer.dto;

import java.io.Serializable;

/**
 * Created by kelvinleung on 21/7/2018.
 */
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T result;

    public Result() {
        code = 0;
        msg = "OK";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
