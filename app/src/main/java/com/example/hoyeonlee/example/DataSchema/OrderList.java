package com.example.hoyeonlee.example.DataSchema;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderList {
    HashMap<String,Integer> order_map;
    Iterator<Map.Entry<String, Integer> > it;

    public int size(){
        return order_map.size();
    }

    public void iter_init(){
        it = order_map.entrySet().iterator();
    }

    public Order getOrder(int idx){
        iter_init();
        if(order_map.size()<idx) return null;
        for(int i=0;i<idx;i++){
            iter_next();
        }
        return iter_next();
    }

    public void changeValue(int idx, int value){
        Order temp=getOrder(idx);
        String key=temp.makeKey();
        int cnt=temp.getCount();
        order_map.put(key,cnt+value);
    }
    public Order iter_next(){
        if(!it.hasNext()) iter_init();
        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
        Order here=new Order(pair.getKey(),pair.getValue());
        return here;
    }

    public int getTotalPrice(){
        int ret=0;
        Iterator<Map.Entry<String, Integer>> it = order_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Log.d("DEBUGYU",pair.getKey() + " = " + pair.getValue());

            Order here=new Order(pair.getKey(),pair.getValue());
            ret+=here.getCount()*here.getPrice();
        }
        return ret;
    }


    public OrderList() {
        order_map=new HashMap<String, Integer>();
    }
    public void addData(Order order){
        int cnt=order.getCount();
        String key=order.makeKey();
        Log.d("DEBUGYU",key+", "+cnt);
        if(order_map.containsKey(key)){
            Integer temp=order_map.get(key);
            temp+=cnt;
            order_map.put(key,temp);
        }
        else{
            order_map.put(key,cnt);
        }
        getJSONOrderList();
    }
    public void removeData(Order order){
        int cnt=order.getCount();
        String key=order.makeKey();
        if(order_map.containsKey(key)){
            order_map.remove(key);
        }
    }

    public void clear(){
        order_map.clear();
    }

    public String getJSONOrderList(){
        if(order_map==null){
            return null;
        }
        ArrayList<Order> orderArrayList=new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> it = order_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Log.d("DEBUGYU",pair.getKey() + " = " + pair.getValue());

            Order here=new Order(pair.getKey(),pair.getValue());
            orderArrayList.add(here);
        }
        Gson gson=new Gson();
        String ret=gson.toJson(orderArrayList);
        Log.d("DEBUGYU",ret);
        return ret;
    }
}
