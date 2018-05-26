package com.example.hoyeonlee.example.Customer;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.hoyeonlee.example.Adapter.Customer.ReceiptMenuAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.ReservedItem;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiptActivity extends BackActionBarActivity {
    @BindView(R.id.receipt_list)
    RecyclerView receiptList;
    @BindView(R.id.lastPrice)
    TextView lastPrice;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ReservedItem> reservations;
    ReceiptMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("세부 내용");
        receiptList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        receiptList.setLayoutManager(mLayoutManager);
        receiptList.scrollToPosition(0);
        mAdapter = new ReceiptMenuAdapter(this);
        receiptList.setAdapter(mAdapter);
        receiptList.setItemAnimator(new DefaultItemAnimator());

        reservations = MApplication.getReservedItems();
        mAdapter.addAllMenus(reservations);
        mAdapter.notifyDataSetChanged();

        setLastPrice();

    }
    void setLastPrice(){
        int price=0;
        for(int i=0;i<reservations.size();i++){
            price+=reservations.get(i).getCount()*reservations.get(i).getMenu().getPrice();
        }
        lastPrice.setText(String.format("%,d원", price));
    }
}
