package com.example.myanimaxmobile.response;

public class userUpdate {
    private String status,error,message;

    //constructor
    public userUpdate(String status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    //getter status
    public String getStatus() {
        return status;
    }
    //setter status
    public void setStatus(String status) {
        this.status = status;
    }
    //getter error
    public String getError() {
        return error;
    }
    //setter error
    public void setError(String error) {
        this.error = error;
    }
    //getter message
    public String getMessage() {
        return message;
    }
    //setter message
    public void setMessage(String message) {
        this.message = message;
    }
}
