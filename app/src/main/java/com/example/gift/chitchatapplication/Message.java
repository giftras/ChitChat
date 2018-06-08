package com.example.gift.chitchatapplication;

/**
 * Created by MacbookPro on 6/8/2018 AD.
 */

public class Message {
    private String content, username;
    public Message(){

    }
    public Message(String content, String username){
        this.content= content;
        this.username = username;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
