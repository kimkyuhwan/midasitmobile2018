package com.example.hoyeonlee.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by hoyeonlee on 2018. 2. 24..
 */

public class BackActionBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    Toolbar toolbar;
    protected void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }
    protected void setLogoutVisible(){
        ImageView iv = toolbar.findViewById(R.id.btn_logout);
        iv.setVisibility(View.VISIBLE);
    }
    protected void setTitle(@NonNull String title){
        TextView titleTextView = toolbar.findViewById(R.id.tv_title);
        titleTextView.setText(title);
    }

    protected void setColor(@NonNull int color){
        TextView titleTextView = toolbar.findViewById(R.id.tv_title);
        titleTextView.setTextColor(color);
    }
}
