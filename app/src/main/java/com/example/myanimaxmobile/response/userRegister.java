package com.example.myanimaxmobile.response;

import org.json.JSONObject;
import org.json.JSONArray;

public class userRegister {
    private String status,error;
    private Object messages;
    //constructor
    public userRegister(String status, String error, Object messages) {
        this.status = status;
        this.error = error;
        this.messages = messages;
    }

    //getters
    public String getStatus() {
        return status;
    }
    public String getError() {
        return error;
    }
    public Object getMessages() {
        return messages;
    }

}
