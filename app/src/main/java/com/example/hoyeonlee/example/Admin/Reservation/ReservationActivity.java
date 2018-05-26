package com.example.hoyeonlee.example.Admin.Reservation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.Adapter.Admin.ReservationAdapter;
import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReservationActivity extends AppCompatActivity {
    public static final String TAG =  "RESERVATION_ACTIVITY";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ApiService apiService;
    ReservationAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);
        ((TextView) toolbar.findViewById(R.id.tv_title)).setText("예약 관리");
        apiService = MApplication.getInstance().getApiService();
        adapter = new ReservationAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        initialize();
    }
    public void initialize(){
        apiService.getAllOrders().enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                try {
                    if (response.isSuccessful()) {
                        ArrayList<Reservation> data = response.body();
                        adapter.addAllOrders(data);
                    } else {
                        Toast.makeText(ReservationActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReservationActivity.this, response.code()+" Error", Toast.LENGTH_SHORT).show();
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ReservationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
