package com.example.hoyeonlee.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hoyeonlee.example.Customer.CustomerActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Menus;
import com.example.hoyeonlee.example.Firebase.MyFirebaseInstanceIDService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.print("Hello World!");
        Intent it=new Intent(this,CustomerActivity.class);
        Log.d("DEBUGYU",FirebaseInstanceId.getInstance().getToken());
        startActivity(it);
        finish();
    }
}
