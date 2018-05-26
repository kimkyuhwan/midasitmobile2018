package com.example.hoyeonlee.example.DataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 27..
 */

public class Report {
    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("orders")
    @Expose
    private ArrayList<Reservation> orders;

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("amount")
    @Expose
    private String amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Reservation> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Reservation> orders) {
        this.orders = orders;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
