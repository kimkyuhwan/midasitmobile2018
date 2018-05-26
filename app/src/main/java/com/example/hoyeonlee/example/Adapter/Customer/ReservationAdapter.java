package com.example.hoyeonlee.example.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.Customer.ReceiptActivity;
import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Customer.ReservationHolder;

import java.util.ArrayList;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationHolder> {


    Context context;
    ArrayList<Reservation> reservations;
    private OnLongClickListener listener;

    public ReservationAdapter(Context context){
        this.context = context;
        reservations = new ArrayList<>();
    }

    public void addAllOrders(ArrayList<Reservation> orders){
        this.reservations = orders;
        notifyDataSetChanged();
    }

    public void clear(){
        reservations.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return reservations.size();
    }

    @Override
    public ReservationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation_customer,parent,false);
        ReservationHolder OrderHolder = new ReservationHolder(context,view);
        return OrderHolder;
    }

    @Override
    public void onBindViewHolder(ReservationHolder holder, final int position) {
        final Reservation reservation = reservations.get(position);
        holder.setData(reservation.getComplete(),reservation.getCreatedAt(),reservation.getAmount());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context,ReceiptActivity.class);
                MApplication.setReservedItems(reservations.get(position).getOrders());
                context.startActivity(it);
            }
        });
    }
}
