package com.example.hoyeonlee.example.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Menus;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.MenuHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {

    Context context;
    ArrayList<Menu> menus;
    ArrayList<Menu> partialMenus;
    String type = "커피";
    private OnLongClickListener listener;

    public MenuAdapter(Context context){
        this.context = context;
        menus = new ArrayList<>();
        partialMenus = new ArrayList<>();
    }

    public void setOnLongClickListener(OnLongClickListener listener){
        this.listener = listener;
    }
    public void setType(String type){
        this.type = type;
        partialMenus = new ArrayList<>();
        for(Menu menu : menus){
            if(menu.getCategory().equals(type)){
                partialMenus.add(menu);
            }
        }
        notifyDataSetChanged();
    }
    public void addAllMenus(ArrayList<Menu> menus){
        this.menus = menus;
        setType(type);
    }
    public void addMenu(Menu menu){
        menus.add(menu);
        setType(type);
    }
    public void updateMenu(Menu prevMenu,Menu nowMenu){
        int prevIndex = menus.indexOf(prevMenu);
        menus.add(prevIndex+1,nowMenu);
        menus.remove(prevIndex);
        setType(type);
    }
    public void deleteMenu(Menu menu){
        Boolean isRemoved = menus.remove(menu);
        if(!isRemoved){
            Toast.makeText(context, "삭제 오류", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();
        setType(type);
    }
    public void clear(){
        menus.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return partialMenus.size();
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        MenuHolder menuHolder = new MenuHolder(context,view);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, final int position) {
        final Menu menu = partialMenus.get(position);
        holder.setData(menu.getThumb(),menu.getName(),menu.getCategory(),menu.getPrice());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.longClick(v,menu,position);
                }
            }
        });
    }
}
