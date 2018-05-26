package com.example.hoyeonlee.example.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoyeonlee.example.Customer.EventDetailActivity;
import com.example.hoyeonlee.example.Customer.MenuDetailActivity;
import com.example.hoyeonlee.example.DataSchema.Event;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Customer.CustomerMenuHolder;
import com.example.hoyeonlee.example.ViewHolder.Customer.EventItemHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class EventMenuAdapter extends RecyclerView.Adapter<EventItemHolder> {

    Context context;
    ArrayList<Event> event_list;
    public EventMenuAdapter(Context context){
        this.context = context;
        event_list = new ArrayList<>();
    }
    public void addAllEvent(ArrayList<Event> events){
        this.event_list = events;
        notifyDataSetChanged();
    }
    public void addEvent(Event event){
        event_list.add(event);
        notifyDataSetChanged();
    }
    public void clear(){
        event_list.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(event_list==null) return 0;
        return event_list.size();
    }

    @Override
    public EventItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item_holder,parent,false);
        EventItemHolder holder=new EventItemHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final EventItemHolder holder, final int position) {
        Event cur=event_list.get(position);
        holder.setData(cur.getTitle());
        if(position%2==1){
            holder.linearLayout.setBackgroundColor(Color.rgb(0xf0,0xf0,0xf0));
        }
        else{
            holder.linearLayout.setBackgroundColor(Color.rgb(0xff,0xff,0xff));

        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(context,EventDetailActivity.class);
                Event item=event_list.get(position);
                it.putExtra("title",String.valueOf(item.getTitle()));
                it.putExtra("body",String.valueOf(item.getDescription()));
                context.startActivity(it);
            }
        });

    }
}
