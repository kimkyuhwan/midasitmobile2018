package com.example.hoyeonlee.example.DataSchema;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Order{


    @SerializedName("menu_id")
    private int menu_id;
    @SerializedName("temperature")
    private String temperature;
    @SerializedName("count")
    private int count;
    @SerializedName("size")
    private String size;
    private transient int price;
    private transient String name;

    public Order(int menu_id, int count,String temperature, String size) {
        this.menu_id = menu_id;
        this.temperature = temperature;
        this.count = count;
        this.size = size;
    }


    public void changeCount(int value){
        count+=value;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order(String key, int cnt){
        String value="";
        count=cnt;
        temperature=null;
        size=null;
        int state=0;
        for(int i=0;i<key.length();i++){
            if(key.charAt(i)=='#'){
                if(!value.equals("")){
                    switch (state){
                        case 0: menu_id=Integer.parseInt(value); break;
                        case 1: temperature=value; break;
                        case 2: size=value; break;
                        case 3: name=value; break;
                        case 4: price=Integer.parseInt(value); break;
                        default: break;
                    }
                }
                value="";
                state++;
            }
            else{
                value+=key.charAt(i);
            }
        }
        Log.d("DEUBGYU",getJSONString());
    }

    public int getId() {
        return menu_id;
    }

    public void setId(int id) {
        this.menu_id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String makeKey(){
        String ret=String.valueOf(menu_id)+"#";
        if(temperature!=null && temperature.equals("null"))
            temperature=null;
        if(size!=null && size.equals("null"))
            size=null;
        if(temperature!=null){
            ret+=temperature;
        }
        ret+="#";
        if(size!=null){
            ret+=size;
        }
        ret+="#";
        ret+=name+"#"+price+"#";
        return ret;
    }

    public String getJSONString(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    public String getDetail(){
        if(size==null || size.equals("null")) return "";
        else if(temperature==null || temperature.equals("null")) return size;
        return temperature+" / " + size;
    }





}
