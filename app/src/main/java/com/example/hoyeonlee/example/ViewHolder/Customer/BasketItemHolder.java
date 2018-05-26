package com.example.hoyeonlee.example.ViewHolder.Customer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class BasketItemHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public AppCompatCheckBox checkBox;
    public TextView nameView;
    public TextView priceView;
    public TextView detailView;
    public Button subtractBtn,addBtn;
    public ImageButton removeBtn;
    public TextView itemCountView;
    public boolean ischecked=true;

    public BasketItemHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        checkBox = view.findViewById(R.id.item_checkbox);
        nameView = view.findViewById(R.id.item_name);
        priceView = view.findViewById(R.id.item_price);
        detailView= view.findViewById(R.id.item_detail);
        subtractBtn =view.findViewById(R.id.subtractBtn);
        addBtn =view.findViewById(R.id.addBtn);
        removeBtn =view.findViewById(R.id.removeBtn);
        itemCountView=view.findViewById(R.id.item_count);
    }



    public void setData(Boolean ischecked, String name,String detail, int cnt, int price){
        this.ischecked=ischecked;
        checkBox.setChecked(ischecked);
        nameView.setText(name);
        detailView.setText(detail);
        itemCountView.setText(String.valueOf(cnt));
        priceView.setText(String.valueOf(price)+" 원");

    }
    public View getView() {
        return view;
    }
}
