package com.kazimasum.ecommdemo;

public class userprofileresponsemodel {
    String id, name, email, password, mobile, address;

    public userprofileresponsemodel() {
    }

    public userprofileresponsemodel(String id, String name, String email, String password, String mobile, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
    }

    public userprofileresponsemodel(String email, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }
}
