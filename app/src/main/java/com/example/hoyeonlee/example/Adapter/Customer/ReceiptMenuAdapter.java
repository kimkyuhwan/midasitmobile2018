package com.example.hoyeonlee.example.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoyeonlee.example.Customer.MenuDetailActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.ReservedItem;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Customer.CustomerMenuHolder;
import com.example.hoyeonlee.example.ViewHolder.Customer.ReceiptMenuHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReceiptMenuAdapter extends RecyclerView.Adapter<ReceiptMenuHolder> {

    Context context;
    ArrayList<ReservedItem> menu_list;
    public ReceiptMenuAdapter(Context context){
        this.context = context;
        menu_list = new ArrayList<>();
    }
    public void addAllMenus(ArrayList<ReservedItem> menus){
        this.menu_list = menus;
        notifyDataSetChanged();
    }
    public void addMenu(ReservedItem menu){
        menu_list.add(menu);
        notifyDataSetChanged();
    }
    public void clear(){
        menu_list.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(menu_list==null) return 0;
        return menu_list.size();
    }

    @Override
    public ReceiptMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receipt_item_holder,parent,false);
        ReceiptMenuHolder holder=new ReceiptMenuHolder(view,context);
        return holder;
    }

    private String getDetail(String temperature,String size){
        if (temperature!=null)
            return temperature+" / "+size;
        return "";
    }

    @Override
    public void onBindViewHolder(final ReceiptMenuHolder holder, final int position) {
        ReservedItem cur=menu_list.get(position);
        holder.setData(cur.getMenu().getName(),getDetail(cur.getTemperature(),cur.getSize()),cur.getCount(),cur.getCount()*cur.getMenu().getPrice());
    }
}
