package com.example.hoyeonlee.example.DataSchema;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reservation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    @SerializedName("profile")
    @Expose
    private String profile;

    @SerializedName("orders")
    @Expose
    private ArrayList<ReservedItem> orders = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("complete_at")
    @Expose
    private Object completeAt;
    @SerializedName("complete")
    @Expose
    private Boolean complete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ArrayList<ReservedItem> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<ReservedItem> orders) {
        this.orders = orders;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(Object completeAt) {
        this.completeAt = completeAt;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

}