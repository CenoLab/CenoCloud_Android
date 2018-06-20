package cenocloud.nero.com.cenocloud.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by neroyang on 2018/3/18.
 */

public class IotList implements Serializable {
    private String msg;
    private Boolean state;
    private List<IotItem> data;

    public IotList() {
    }

    public IotList(String msg, Boolean state, List<IotItem> data) {
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

    public List<IotItem> getData() {
        return data;
    }

    public void setData(List<IotItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IotList{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", data=" + data +
                '}';
    }
}
