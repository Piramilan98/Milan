package com.hotel.hotel;

public class SettingSingleton {
    private String user_id = "";
    private String name = "";
    private String type = "";
    private String phone = "";
    private String email = "";


    private static final SettingSingleton user = new SettingSingleton();

    private SettingSingleton(){}

    public static SettingSingleton getSetting(){
        return user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
