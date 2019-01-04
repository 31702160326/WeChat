package com.example.administrator.wechat;

/**
 * Created by Administrator on 2019/1/*/

public class UserInfo {
    private String user;

    public UserInfo(String user, String name) {
        this.user = user;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String name;
}
