package com.example.hoyeonlee.example.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.Admin.User.UserActivity;
import com.example.hoyeonlee.example.Admin.Menu.MenuActivity;
import com.example.hoyeonlee.example.Admin.Reservation.ReservationActivity;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.Utils.StaticFunctions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BackActionBarActivity {


    @BindView(R.id.layout_reservation)
    FrameLayout layoutReservation;
    @BindView(R.id.layout_customer)
    FrameLayout layoutCustomer;
    @BindView(R.id.layout_menu)
    FrameLayout layoutMenu;
    @BindView(R.id.tv_hello)
    TextView helloTextView;

    @BindView(R.id.btn_logout)
    ImageView logoutButton;
    int status = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setToolbar();
        setLogoutVisible();
        setTitle("홈");
        status = SharedPreferenceBase.getIntSharedPreference("status");
        helloTextView.setText(SharedPreferenceBase.getSharedPreference("name", "")
                + (status==1?"관리자":"최고관리자")+"님 환영합니다");
    }

    @OnClick({R.id.layout_reservation, R.id.layout_customer, R.id.layout_menu,R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_reservation:
                startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                break;
            case R.id.layout_customer:
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                break;
            case R.id.layout_menu:
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                break;
            case R.id.btn_logout:
                StaticFunctions.getInstance(HomeActivity.this).goFirstPage();
                break;
            default:
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));

        }
    }
}
