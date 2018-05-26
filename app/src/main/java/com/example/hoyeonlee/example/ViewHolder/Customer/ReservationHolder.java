package com.example.hoyeonlee.example.ViewHolder.Customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReservationHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    public ImageView profileView;
    public TextView timeView;
    public TextView priceView;
    public ReservationHolder(Context context, View view){
        super(view);
        this.context = context;
        this.view = view;
        profileView = view.findViewById(R.id.iv_image);
        timeView = view.findViewById(R.id.tv_time);
        priceView = view.findViewById(R.id.tv_price);
    }


    public void setData(boolean state, String time, int price){
        String dateTime = DateTime.parse(time).toString("MM월 dd일 HH시 mm분 ss초");
        if(state)
            profileView.setBackgroundResource(R.drawable.done_list);
        else
            profileView.setBackgroundResource(R.drawable.ing_list);
        timeView.setText(dateTime);
        String pr = String.format("%,d원", price);
        priceView.setText(pr);
    }


    public View getView() {
        return view;
    }
}
