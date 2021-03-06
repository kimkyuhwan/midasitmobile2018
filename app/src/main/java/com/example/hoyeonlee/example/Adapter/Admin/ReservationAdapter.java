package com.example.hoyeonlee.example.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.ReservationHolder;

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

    public void setOnLongClickListener(OnLongClickListener listener){
        this.listener = listener;
    }
    public void addAllReservations(ArrayList<Reservation> orders){
        this.reservations = orders;
        notifyDataSetChanged();
    }
    public void deleteReservation(Reservation order){
        Boolean isRemoved = reservations.remove(order);
        if(!isRemoved){
            Toast.makeText(context, "삭제 오류", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, "주문완료", Toast.LENGTH_SHORT).show();
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation,parent,false);
        ReservationHolder reservationHolder = new ReservationHolder(context,view);
        return reservationHolder;
    }

    @Override
    public void onBindViewHolder(ReservationHolder holder, final int position) {
        final Reservation reservation = reservations.get(position);
        holder.setData(reservation.getUser().getProfile(),reservation.getUser().getUsername(),reservation.getCreatedAt(),reservation.getAmount());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.longClick(v,reservation,position);
                }
            }
        });
    }
}
