package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Order;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_image)
    CircleImageView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.subtractBtn)
    Button subtractBtn;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.addBtn)
    Button addBtn;
    @BindView(R.id.spinner_temperature)
    NiceSpinner spinnerTemperature;
    @BindView(R.id.spinner_size)
    NiceSpinner spinnerSize;
    @BindView(R.id.basketBtn)
    Button basketBtn;
    @BindView(R.id.orderBtn)
    Button orderBtn;

    int item_count=1;
    int item_price=3000;

    Order current_menu;
    List<String> temperature_list = new LinkedList<>(Arrays.asList("Hot", "Ice"));
    List<String> size_list = new LinkedList<>(Arrays.asList("S", "M","L"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);
        tvTitle.setText("메뉴 주문");
        Intent intent=getIntent();
        String category=intent.getExtras().getString("category");
        int id=Integer.parseInt(intent.getExtras().getString("id"));
        String name=intent.getExtras().getString("name");
        item_price =Integer.parseInt(intent.getExtras().getString("price"));
        String thumb= intent.getExtras().getString("thumb");
        current_menu=new Order(id,1,"","");
        current_menu.setPrice(item_price);
        current_menu.setName(name);

        Picasso.get().load(thumb).into(itemImage);
        itemName.setText(name);
        itemPrice.setText(String.valueOf(item_count*item_price)+" 원");
        if(category.equals("디저트")){

        }
        else {
            if(category.equals("요거트"))
                temperature_list.remove(0);
            spinnerTemperature.attachDataSource(temperature_list);
            spinnerSize.attachDataSource(size_list);
        }
    }

    @OnClick({R.id.subtractBtn, R.id.addBtn, R.id.basketBtn, R.id.orderBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.subtractBtn:
                item_count=current_menu.getCount();
                if(item_count>1){
                    itemCount.setText(String.valueOf(--item_count));
                    itemPrice.setText(String.valueOf(item_count*item_price)+" 원");
                    current_menu.setCount(item_count);
                }
                break;
            case R.id.addBtn:
                item_count=current_menu.getCount();
                itemCount.setText(String.valueOf(++item_count));
                itemPrice.setText(String.valueOf(item_count*item_price)+" 원");
                current_menu.setCount(item_count);
                break;
            case R.id.basketBtn:
                current_menu.setTemperature(spinnerTemperature.getText().toString());
                current_menu.setSize(spinnerSize.getText().toString());
                MApplication.getOrderList().addData(current_menu);
                Toast.makeText(this," 장바구니에 담겼습니다.",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.orderBtn:

                break;
        }
    }
}
