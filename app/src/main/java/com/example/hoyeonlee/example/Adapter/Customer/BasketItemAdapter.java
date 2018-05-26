package com.example.hoyeonlee.example.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.hoyeonlee.example.Customer.MenuDetailActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Order;
import com.example.hoyeonlee.example.DataSchema.OrderList;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Customer.BasketItemHolder;
import com.example.hoyeonlee.example.ViewHolder.Customer.CustomerMenuHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class BasketItemAdapter extends RecyclerView.Adapter<BasketItemHolder> {

    Context context;
    OrderList orderList;
    public BasketItemAdapter(Context context){
        this.context = context;
        orderList= MApplication.getOrderList();
        orderList.iter_init();
    }

    public void addMenu(Order item){
        orderList.addData(item);
        notifyDataSetChanged();
    }
    public void clear(){
        orderList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public BasketItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_item_holder,parent,false);
        BasketItemHolder holder=new BasketItemHolder(view,context);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BasketItemHolder holder, final int position) {
        Order cur=orderList.getOrder(position);
        holder.setData(holder.ischecked,cur.getName(),cur.getDetail(),cur.getCount(),cur.getPrice()*cur.getCount());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean ischecked) {
                Order temp=orderList.getOrder(position);
                holder.ischecked=ischecked;
                int price=temp.getCount()*temp.getPrice();
                int totalprice=orderList.getTotal_price();
                if(ischecked){
                    totalprice+=price;
                }
                else{
                    totalprice-=price;
                }
                orderList.setTotal_price(totalprice);
                new Handler().post(new Runnable() { // new Handler and Runnable
                    @Override
                    public void run() {
                       notifyDataSetChanged();
                       Log.d("DEBUGYU","H");
                    }
                });


            }
        });


        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order temp=orderList.getOrder(position);
                int removeprice=temp.getCount()*temp.getPrice();
                orderList.removeData(temp);
                Log.d("DEBUGYU","removeData");
                int totalprice=orderList.getTotal_price();
                if(holder.ischecked)
                    totalprice-=removeprice;
                orderList.setTotal_price(totalprice);
                notifyDataSetChanged();
            }
        });

        holder.subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order temp=orderList.getOrder(position);
                int cnt=temp.getCount();
                if(cnt>1){
                    orderList.changeValue(position,-1);
                    Log.d("DEBUGYU","changeValue -1");
                    int totalprice=orderList.getTotal_price();
                    if(holder.ischecked)
                        totalprice-=temp.getPrice();
                    orderList.setTotal_price(totalprice);
                    notifyDataSetChanged();
                }
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order temp=orderList.getOrder(position);
                orderList.changeValue(position,1);
                Log.d("DEBUGYU","changeValue 1");
                int totalprice=orderList.getTotal_price();
                if(holder.ischecked)
                    totalprice+=temp.getPrice();
                orderList.setTotal_price(totalprice);
                notifyDataSetChanged();
            }
        });

    }

}
