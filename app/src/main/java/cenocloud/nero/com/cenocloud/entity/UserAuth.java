package cenocloud.nero.com.cenocloud.entity;

import java.io.Serializable;

/**
 * Created by neroyang on 2018/3/18.
 */

public class UserAuth implements Serializable {
    private Integer id;
    private String company;
    private String email;
    private String phone;
    private String createTime;
    private String token;
    private String name;

    public UserAuth() {
    }

    public UserAuth(Integer id, String company, String email, String phone, String createTime, String token, String name) {
        this.id = id;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
        this.token = token;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
