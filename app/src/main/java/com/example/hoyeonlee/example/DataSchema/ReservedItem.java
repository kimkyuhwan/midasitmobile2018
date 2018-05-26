package com.example.hoyeonlee.example.DataSchema;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservedItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("menu")
    @Expose
    private Menu menu;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
