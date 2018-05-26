package com.example.hoyeonlee.example.ViewHolder.Customer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReceiptMenuHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public TextView nameView;
    public TextView countView;
    public TextView priceView;
    public TextView detailView;


    public ReceiptMenuHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        nameView=view.findViewById(R.id.item_name);
        countView = view.findViewById(R.id.item_count);
        priceView = view.findViewById(R.id.item_price);
        detailView = view.findViewById(R.id.item_detail);
    }



    public void setData(String name, String detail, int count, int price){
        nameView.setText(name);
        if(detail!=null) detailView.setText(detail);
        countView.setText(String.format("x%d",count));
        priceView.setText(String.format("%,d원", price));
    }
    public View getView() {
        return view;
    }
}
