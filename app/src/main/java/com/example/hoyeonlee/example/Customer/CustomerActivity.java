package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hoyeonlee.example.Admin.HomeActivity;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.Utils.StaticFunctions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerActivity extends BackActionBarActivity {
    @BindView(R.id.btn_logout)
    ImageView btnLogout;
    @BindView(R.id.menuReserveBtn)
    Button menuReserveBtn;
    @BindView(R.id.checkReserveBtn)
    Button checkReserveBtn;
    @BindView(R.id.purchaseHistoryBtn)
    Button purchaseHistoryBtn;
    @BindView(R.id.eventBoardBtn)
    Button eventBoardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        setToolbar();
        setLogoutVisible();
        setTitle("홈");

    }

    @OnClick({R.id.btn_logout, R.id.menuReserveBtn, R.id.checkReserveBtn, R.id.purchaseHistoryBtn, R.id.eventBoardBtn})
    public void onViewClicked(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.btn_logout:
                StaticFunctions.getInstance(CustomerActivity.this).goFirstPage();
                break;
            case R.id.menuReserveBtn:
                it=new Intent(this,MenuReservationActivity.class);
                startActivity(it);
                break;
            case R.id.checkReserveBtn:
                it=new Intent(this,CheckReservationActivity.class);
                startActivity(it);
                break;
            case R.id.purchaseHistoryBtn:
                it=new Intent(this,MonthPurchaseHistoryActivity.class);
                startActivity(it);
                break;
            case R.id.eventBoardBtn:
                it=new Intent(this,EventCheckActivity.class);
                startActivity(it);
                break;
        }
    }
/*
    @OnClick({R.id.menuReserveBtn, R.id.checkReserveBtn, R.id.purchaseHistoryBtn, R.id.eventBoardBtn})
    public void onViewClicked(View view) {

        switch (view.getId()) {

        }
    }*/

}
