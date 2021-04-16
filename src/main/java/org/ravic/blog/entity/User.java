package org.ravic.blog.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户的实体类
 */
public class User {
    //用户的编号
    //blog多对一user,user数据表的主键,关联blog的外键userId
    private Long id;

    //用户昵称
    private String nickname;

    //用户名
    @NotNull(message = "用户名不能为空")
    private String username;

    //用户密码
    private String password;

    //用户邮箱
    private String email;

    //用户头像
    private String avatar;

    //用户类型
    //1表示管理员
    private Integer type;

    //用户创建时间
    private Date createTime;

    //用户修改时间
    private Date updateTime;

    public User() {
    }

    public User(Long id, String nickname, String username, String password, String email, String avatar, Integer type, Date createTime, Date updateTime) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.type = type;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
