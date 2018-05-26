package com.example.hoyeonlee.example.ViewHolder.Customer;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class EventItemHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;

    public TextView titleView;
    public LinearLayout linearLayout;
    public EventItemHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        this.linearLayout = view.findViewById(R.id.event_layout);
        this.titleView = view.findViewById(R.id.event_title);
    }



    public void setData(String title){
        titleView.setText(title);

    }
    public View getView() {
        return view;
    }
}
