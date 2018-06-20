package cenocloud.nero.com.cenocloud.entity;

import java.io.Serializable;

/**
 * Created by neroyang on 2018/3/18.
 */

public class IotItem implements Serializable {
    private String company;
    private String createTime;
    private Integer currentConn;
    private Integer currentOnline;
    private String  description;
    private Integer did;
    private Integer id;
    private Integer maxConnect;
    private Integer  messageCount;
    private String name;
    private String productKey;
    private String tech;
    private Integer trans;
    private String type;

    public IotItem() {
    }

    public IotItem(String company, String createTime, Integer currentConn, Integer currentOnline, String description, Integer did, Integer id, Integer maxConnect, Integer messageCount, String name, String productKey, String tech, Integer trans, String type) {
        this.company = company;
        this.createTime = createTime;
        this.currentConn = currentConn;
        this.currentOnline = currentOnline;
        this.description = description;
        this.did = did;
        this.id = id;
        this.maxConnect = maxConnect;
        this.messageCount = messageCount;
        this.name = name;
        this.productKey = productKey;
        this.tech = tech;
        this.trans = trans;
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCurrentConn() {
        return currentConn;
    }

    public void setCurrentConn(Integer currentConn) {
        this.currentConn = currentConn;
    }

    public Integer getCurrentOnline() {
        return currentOnline;
    }

    public void setCurrentOnline(Integer currentOnline) {
        this.currentOnline = currentOnline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxConnect() {
        return maxConnect;
    }

    public void setMaxConnect(Integer maxConnect) {
        this.maxConnect = maxConnect;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public Integer getTrans() {
        return trans;
    }

    public void setTrans(Integer trans) {
        this.trans = trans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IotItem{" +
                "company='" + company + '\'' +
                ", createTime='" + createTime + '\'' +
                ", currentConn=" + currentConn +
                ", currentOnline=" + currentOnline +
                ", description='" + description + '\'' +
                ", did=" + did +
                ", id=" + id +
                ", maxConnect=" + maxConnect +
                ", messageCount=" + messageCount +
                ", name='" + name + '\'' +
                ", productKey='" + productKey + '\'' +
                ", tech='" + tech + '\'' +
                ", trans=" + trans +
                ", type='" + type + '\'' +
                '}';
    }
}
