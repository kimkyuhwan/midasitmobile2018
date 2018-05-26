package com.example.hoyeonlee.example.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hoyeonlee.example.Admin.Customer.CustomerActivity;
import com.example.hoyeonlee.example.Admin.Menu.MenuActivity;
import com.example.hoyeonlee.example.Admin.Reservation.ReservationActivity;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.layout_reservation)
    FrameLayout layoutReservation;
    @BindView(R.id.layout_customer)
    FrameLayout layoutCustomer;
    @BindView(R.id.layout_menu)
    FrameLayout layoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layout_reservation, R.id.layout_customer, R.id.layout_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_reservation:
                startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                break;
            case R.id.layout_customer:
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                break;
            case R.id.layout_menu:
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                break;
        }
    }
}
