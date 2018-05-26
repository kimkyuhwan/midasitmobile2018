package com.example.hoyeonlee.example.ViewHolder.Customer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class CustomerMenuHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public CircleImageView imageView;
    public TextView nameView;
    public TextView priceView;
    public ConstraintLayout layout;

    public CustomerMenuHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        layout=view.findViewById(R.id.item_layout);
        imageView = view.findViewById(R.id.item_image);
        nameView = view.findViewById(R.id.item_name);
        priceView = view.findViewById(R.id.item_price);
    }



    public void setData(String imageUrl, String title, String category, int price){
        Picasso.get().load(imageUrl).into(imageView);
        nameView.setText(title);
        priceView.setText(String.valueOf(price)+" 원");
    }
    public View getView() {
        return view;
    }
}
