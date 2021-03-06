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

public class MenuHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public ImageView imageView;
    public TextView titleView;
    public TextView categoryView;
    public TextView priceView;

    public MenuHolder(Context context, View view){
        super(view);
        this.context = context;
        this.view = view;
        imageView = view.findViewById(R.id.iv_image);
        titleView = view.findViewById(R.id.tv_title);
        categoryView = view.findViewById(R.id.tv_category);
        priceView = view.findViewById(R.id.tv_time);
    }


    public void setData(String imageUrl, String title, String category, int price){
        Picasso.get().load(imageUrl)
                .resize(100,100)
                .placeholder(R.drawable.placeholder)
                .centerCrop().into(imageView); // Image scaling typeinto(imageView);
        String pr = String.format("%,d원", price);
        titleView.setText(title);
        categoryView.setText(category);
        priceView.setText(pr);
    }
    public View getView() {
        return view;
    }
}
