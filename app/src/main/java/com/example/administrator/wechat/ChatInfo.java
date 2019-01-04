package com.example.administrator.wechat;

/**
 * Created by Administrator on 2019/1/3.
 */

public class ChatInfo {
    private String chat;
    private String time;
    private String name;
    public ChatInfo(String chat, String time, String name) {
        this.chat = chat;
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
