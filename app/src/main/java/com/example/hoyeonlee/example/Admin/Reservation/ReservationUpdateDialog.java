package com.example.hoyeonlee.example.Admin.Reservation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.DataSchema.ReservedItem;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.ViewHolder.Admin.ReservedCustomView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 3. 23..
 */

public class ReservationUpdateDialog extends Dialog {
    private static final String TAG = "RESERVATION_DIALOG_LOG";


    ApiService apiService;
    ReservationActivity context;
    @BindView(R.id.layout_reservation)
    LinearLayout reservationLayout;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    Reservation reservation;
    @BindView(R.id.tv_list)
    TextView listTextView;

    public ReservationUpdateDialog(@NonNull Context context, Reservation reservation) {
        super(context);
        this.context = (ReservationActivity) context;
        this.reservation = reservation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reservation);
        ButterKnife.bind(this);
        apiService = MApplication.getInstance().getApiService();
        //이름 + 주문내역
        listTextView.setText(reservation.getUser().getUsername()+"님 주문내역");
        //Dialog Size 조정
        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.90); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.95); //set width to 90% of total
        getWindow().setLayout(width, height); //set layout

        addView();
    }

    public void addView() {
        for (int i = 0; i < reservation.getOrders().size(); i++) {
            ReservedCustomView view = new ReservedCustomView(context);
            ReservedItem item = reservation.getOrders().get(i);
            String imgUrl = item.getMenu().getThumb();
            String name = item.getMenu().getName();
            String count = item.getCount() + "";
            String attr = "";
            if (item.getTemperature() != null) {
                attr = item.getTemperature() + " / " + item.getSize();
            } else {
                view.findViewById(R.id.tv_attribute).setVisibility(View.GONE);
            }
            view.setData(imgUrl, name, attr, count);
            reservationLayout.addView(view);
        }
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        apiService.updateReservation(reservation.getId() + "").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    context.adapter.deleteReservation(reservation);
                    Log.v(TAG, response.body().toString());
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
