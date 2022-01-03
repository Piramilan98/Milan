package com.hotel.hotel;

public class LoggedUser {
    private String type = "Admin";
    private String name = "Name";
    private String email = "Email";

    private static final LoggedUser u = new LoggedUser();

    private LoggedUser(){}

    public static LoggedUser getLoggedUser(){
        return u;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

