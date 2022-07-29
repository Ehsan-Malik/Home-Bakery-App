package com.kazimasum.ecommdemo;

import com.google.gson.annotations.SerializedName;

public class ordermodel {
     String id, mobile, orderdate, orderdetails, amount, deliveryaddress, paymenttype, customeraccountnumber, paymentstatus, orderstatus, feedback, shop;

    public ordermodel() {
    }

    public ordermodel(String id, String mobile, String orderdate, String orderdetails, String amount, String deliveryaddress, String paymenttype, String customeraccountnumber, String paymentstatus, String orderstatus, String feedback, String shop) {
        this.id = id;
        this.mobile = mobile;
        this.orderdate = orderdate;
        this.orderdetails = orderdetails;
        this.amount = amount;
        this.deliveryaddress = deliveryaddress;
        this.paymenttype = paymenttype;
        this.customeraccountnumber = customeraccountnumber;
        this.paymentstatus = paymentstatus;
        this.orderstatus = orderstatus;
        this.feedback = feedback;
        this.shop = shop;

    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public String getOrderdetails() {
        return orderdetails;
    }

    public String getAmount() {
        return amount;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public String getCustomeraccountnumber() {
        return customeraccountnumber;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public String getFeedback() {
        return feedback;
    }
    public String getShop() {
        return shop;
    }

}
