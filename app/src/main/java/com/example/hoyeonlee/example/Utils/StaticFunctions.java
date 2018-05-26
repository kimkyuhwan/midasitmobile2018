package com.example.hoyeonlee.example.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.hoyeonlee.example.Login.SignInActiviy;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;

public class StaticFunctions {
    private Context context;
    private static StaticFunctions instance;

    public StaticFunctions(Context context){
        this.context = context;
    }
    public static StaticFunctions getInstance(Context context){
        if(instance == null){
            instance = new StaticFunctions(context);
        }
        return instance;
    }

    //세션 아웃시 로그인 페이지로
    public void goFirstPage(){
        SharedPreferenceBase.putSharedPreference("login",false);

        Toast.makeText(context, "LOGOUT! PLEASE LOGIN AGAIN", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, SignInActiviy.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}