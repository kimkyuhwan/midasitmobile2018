package com.example.hoyeonlee.example.ViewHolder.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class UserHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public ImageView imageView;
    public TextView nameView;
    public TextView supervisorView;

    public UserHolder(Context context, View view){
        super(view);
        this.context = context;
        this.view = view;
        imageView = view.findViewById(R.id.iv_image);
        nameView = view.findViewById(R.id.tv_name);
        supervisorView = view.findViewById(R.id.tv_supervisor);
    }


    public void setData(String imageUrl, String name, String gender, boolean isSupervisor){
        Picasso.get().load(imageUrl)
                .resize(100,100)
                .placeholder(R.drawable.placeholder)
                .centerCrop().into(imageView); // Image scaling typeinto(imageView);
        nameView.setText(name + " (" +gender+ ")");
        supervisorView.setVisibility(isSupervisor?View.VISIBLE:View.INVISIBLE);
    }
    public View getView() {
        return view;
    }
}
