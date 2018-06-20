package cenocloud.nero.com.cenocloud.entity;

import java.io.Serializable;

/**
 * Created by neroyang on 2018/3/18.
 */

public class ResponseResult<T> implements Serializable {

    private String msg;
    private Boolean state;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(String msg, Boolean state, T data) {
        this.msg = msg;
        this.state = state;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", data=" + data +
                '}';
    }
}
