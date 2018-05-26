package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerActivity extends BackActionBarActivity {

    @BindView(R.id.menuReserveBtn)
    Button menuReserveBtn;
    @BindView(R.id.checkReserveBtn)
    Button checkReserveBtn;
    @BindView(R.id.purchaseHistoryBtn)
    Button purchaseHistoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        setToolbar();
        setLogoutVisible();
        setTitle("홈");

    }

    @OnClick({R.id.menuReserveBtn, R.id.checkReserveBtn, R.id.purchaseHistoryBtn})
    public void onViewClicked(View view) {
        Intent it;
        switch (view.getId()) {
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
        }
    }
}
