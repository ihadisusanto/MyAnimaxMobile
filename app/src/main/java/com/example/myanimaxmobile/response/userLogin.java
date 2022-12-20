package com.example.myanimaxmobile.response;

public class userLogin {
    private String role,username,token,status,name;

    //constructor
    public userLogin(String role, String username, String token, String status) {
        this.role = role;
        this.username = username;
        this.name = name;
        this.token = token;
        this.status = status;
    }

    //getter role
    public String getRole() {
        return role;
    }
    //setter role
    public void setRole(String role) {
        this.role = role;
    }
    //getter username
    public String getUsername() {
        return username;
    }
    //setter username
    public void setUsername(String username) {
        this.username = username;
    }
    //getter name
    public String getName() {
        return name;
    }
    //setter name
    public void setName(String name) {
        this.name = name;
    }
    //getter token
    public String getToken() {
        return token;
    }
    //setter token
    public void setToken(String token) {
        this.token = token;
    }
    //getter status
    public String getStatus() {
        return status;
    }
    //setter status
    public void setStatus(String status) {
        this.status = status;
    }
}
