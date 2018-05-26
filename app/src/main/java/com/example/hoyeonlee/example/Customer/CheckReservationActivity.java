package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hoyeonlee.example.Adapter.Customer.CustomMenuAdapter;
import com.example.hoyeonlee.example.Adapter.Customer.ReservationAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckReservationActivity extends BackActionBarActivity {

    @BindView(R.id.reservation_list)
    RecyclerView reservationList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Reservation> reservations;
    ReservationAdapter mAdapter;


    private final int STATE_ALL=0;
    private final int STATE_ING=1;
    private final int STATE_DONE=2;


    int checkstate=STATE_ALL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reservation);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("예약 확인");
        reservationList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        reservationList.setLayoutManager(mLayoutManager);
        reservationList.scrollToPosition(0);
        mAdapter=new ReservationAdapter(this);
        reservationList.setAdapter(mAdapter);
        reservationList.setItemAnimator(new DefaultItemAnimator());
        initDataSet();

    }

    void initDataSet(){
        switch (checkstate){
            case STATE_ALL:
                MApplication.getInstance().getApiService().getMyOrders().enqueue(new Callback<ArrayList<Reservation>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                        if (response.isSuccessful()) {
                            mAdapter.clear();
                            reservations=response.body();
                            mAdapter.addAllOrders(reservations);
                            if(reservations.size()==0)
                                Toast.makeText(getApplicationContext(),"예약 기록이 없습니다",Toast.LENGTH_LONG).show();
                            mAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"확인 실패",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case STATE_ING:
                MApplication.getInstance().getApiService().getMyUnCompletedOrders().enqueue(new Callback<ArrayList<Reservation>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                        if (response.isSuccessful()) {
                            mAdapter.clear();
                            reservations=response.body();
                            mAdapter.addAllOrders(reservations);
                            if(reservations.size()==0)
                                Toast.makeText(getApplicationContext(),"예약중인 메뉴가 없습니다",Toast.LENGTH_LONG).show();
                            mAdapter.notifyDataSetChanged();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"확인 실패",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_LONG).show();

                    }
                });
                break;
            case STATE_DONE:
                MApplication.getInstance().getApiService().getMyCompletedOrders().enqueue(new Callback<ArrayList<Reservation>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                        if (response.isSuccessful()) {
                            mAdapter.clear();

                            reservations=response.body();
                            mAdapter.addAllOrders(reservations);
                            mAdapter.notifyDataSetChanged();


                        }
                        else{
                            Toast.makeText(getApplicationContext(),"확인 실패",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void     onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_LONG).show();

                    }
                });
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.purchase_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.state_btn) {
            checkstate=(checkstate+1)%3;
            int icon=R.drawable.all_list;
            switch (checkstate){
                case STATE_ALL: icon=R.drawable.all_list; break;
                case STATE_ING: icon=R.drawable.ing_list; break;
                case STATE_DONE: icon=R.drawable.done_list; break;
            }
            item.setIcon(icon);
            initDataSet();
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}
