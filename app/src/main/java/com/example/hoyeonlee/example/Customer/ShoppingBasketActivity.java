package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.Adapter.Customer.BasketItemAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.Login.SignInActiviy;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.MainActivity;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.Utils.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingBasketActivity extends BackActionBarActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    RecyclerView recyclerView;
    @BindView(R.id.lastPrice)
    TextView lastPrice;
    @BindView(R.id.clearBtn)
    Button clearBtn;
    @BindView(R.id.orderBtn)
    Button orderBtn;
    private BasketItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("장바구니");
        recyclerView = (RecyclerView) findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(0);
        mAdapter = new BasketItemAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MApplication.getOrderList().initTotalPrice();
        updateLastPrice();
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                updateLastPrice();
            }
        });
    }
    void updateLastPrice(){
        lastPrice.setText(String.format("%,d원", MApplication.getOrderList().getTotal_price()));

    }

    @OnClick({R.id.clearBtn, R.id.orderBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clearBtn:
                MApplication.getOrderList().clear();
                mAdapter.notifyDataSetChanged();

                updateLastPrice();
                break;
            case R.id.orderBtn:
                if(MApplication.getOrderList().getTotal_price()!=0){
                    MApplication.getInstance().getApiService().order(MApplication.getOrderList().getJSONOrderList()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"주문 완료되었습니다",Toast.LENGTH_LONG).show();
                                MApplication.getOrderList().clear();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"주문 실패하었습니다",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"실패하었습니다",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                break;
        }
    }
}
