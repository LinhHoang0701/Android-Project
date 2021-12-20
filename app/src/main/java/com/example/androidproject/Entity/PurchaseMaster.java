package com.example.androidproject.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseMaster implements IEntity, Serializable {
    private String masterId;
    private String username, purchaseTime, destination, status, paymentMethod;
    private ArrayList<PurchaseDetail> purchaseList;

    public PurchaseMaster() {
    }

    public PurchaseMaster(ArrayList<PurchaseDetail> purchaseList){
        this.purchaseList = purchaseList;
    }

    public float getTotal() {
        float total = 0;
        for(PurchaseDetail p: purchaseList) {
            total += p.getSubTotal();
        }
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PurchaseDetail> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(ArrayList<PurchaseDetail> purchaseList) {
        this.purchaseList = purchaseList;
    }
}
