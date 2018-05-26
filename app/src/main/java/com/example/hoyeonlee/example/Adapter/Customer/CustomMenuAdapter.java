package com.example.hoyeonlee.example.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.Customer.MenuDetailActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.MenuHolder;
import com.example.hoyeonlee.example.ViewHolder.Customer.CustomerMenuHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class CustomMenuAdapter extends RecyclerView.Adapter<CustomerMenuHolder> {

    Context context;
    ArrayList<Menu> menu_list;
    public CustomMenuAdapter(Context context){
        this.context = context;
        menu_list = new ArrayList<>();
    }
    public void addAllMenus(ArrayList<Menu> menus){
        this.menu_list = menus;
        notifyDataSetChanged();
    }
    public void addMenu(Menu menu){
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
    public CustomerMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_item_holder,parent,false);
        CustomerMenuHolder holder=new CustomerMenuHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomerMenuHolder holder, final int position) {
        Menu cur=menu_list.get(position);
        holder.setData(cur.getThumb(),cur.getName(),"coffee",cur.getPrice());
        if(position%2==0){
            holder.layout.setBackgroundColor(Color.WHITE);
        }
        else{
            holder.layout.setBackgroundColor(Color.rgb(0xf7,0xf7,0xf7));
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_LONG).show();
                Intent it=new Intent(context,MenuDetailActivity.class);
                Menu item=menu_list.get(position);
                it.putExtra("id",String.valueOf(item.getId()));
                it.putExtra("name",item.getName());
                it.putExtra("price",String.valueOf(item.getPrice()));
                it.putExtra("thumb",item.getThumb());
                it.putExtra("category",item.getCategory());
                context.startActivity(it);
            }
        });

    }
}
