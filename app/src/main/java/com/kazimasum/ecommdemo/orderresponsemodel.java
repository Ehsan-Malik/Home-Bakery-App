package com.kazimasum.ecommdemo;

public class orderresponsemodel {

    String message;

    public orderresponsemodel(String message) {
        this.message = message;
    }

    public orderresponsemodel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
