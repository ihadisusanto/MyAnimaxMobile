package com.example.myanimaxmobile.response;

public class userMe {
    private String username,name,email,genre,role;

    //constructor
    public userMe(String username, String name, String email, String genre, String role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.genre = genre;
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
    //getter email
    public String getEmail() {
        return email;
    }
    //setter email
    public void setEmail(String email) {
        this.email = email;
    }
    //getter genre
    public String getGenre() {
        return genre;
    }
    //setter genre
    public void setGenre(String genre) {
        this.genre = genre;
    }
    //getter role
    public String getRole() {
        return role;
    }
    //setter role
    public void setRole(String role) {
        this.role = role;
    }
}
