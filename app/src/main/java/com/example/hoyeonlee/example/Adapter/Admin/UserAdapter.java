package com.example.hoyeonlee.example.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.User;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.UserHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {


    Context context;
    ArrayList<User> users;
    private OnLongClickListener listener;

    public UserAdapter(Context context){
        this.context = context;
        users = new ArrayList<>();
    }

    public void setOnLongClickListener(OnLongClickListener listener){
        this.listener = listener;
    }
    public void addAllUsers(ArrayList<User> users){
        for(User user : users){
            if(!user.getIsStaff()){
                this.users.add(user);
            }
        }
        notifyDataSetChanged();
    }
    public void addOnlyStaffs(ArrayList<User> users){
        for(User user : users){
            if(user.getIsStaff()){
                this.users.add(user);
            }
        }
        notifyDataSetChanged();
    }
    public void addUser(User user){
        users.add(user);
        notifyDataSetChanged();
    }
    public void updateUser(User prevUser,User nowUser){
        int prevIndex = users.indexOf(prevUser);
        users.add(prevIndex+1,nowUser);
        users.remove(prevIndex);
        notifyDataSetChanged();
    }
    public void deleteUser(User user){
        Boolean isRemoved = users.remove(user);
        if(!isRemoved){
            Toast.makeText(context, "삭제 오류", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        UserHolder menuHolder = new UserHolder(context,view);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        final User user = users.get(position);
        holder.setData(user.getProfile(),user.getFirstName(),user.getGender(),user.getIsStaff());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.longClick(v,user,position);
                }
            }
        });
    }
}
