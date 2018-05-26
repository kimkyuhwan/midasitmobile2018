package com.example.hoyeonlee.example.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.MenuHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

    Context context;
    ArrayList<Menu> menus;
    public MenuAdapter(Context context){
        this.context = context;
        menus = new ArrayList<>();
    }
    public void addAllMenus(ArrayList<Menu> menus){
        this.menus = menus;
    }
    public void addMenu(Menu menu){
        menus.add(menu);
        notifyDataSetChanged();
    }
    public void clear(){
        menus.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);

        return null;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {

    }
}
