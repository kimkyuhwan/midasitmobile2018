package com.example.hoyeonlee.example.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.hoyeonlee.example.MApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hoyeonlee on 2018. 3. 17..
 */

public class SharedPreferenceBase{
    private static SharedPreferences sp;
    public static void putSharedPreference(String key, HashSet<String> value){
        sp = MApplication.getInstance().getSharedPreferences("cookie-storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key,value);
        editor.commit();
    }


    public static Set<String> getSharedPreference(String key, @Nullable Set<String> defaultValue){
        sp = MApplication.getInstance().getSharedPreferences("cookie-storage", Context.MODE_PRIVATE);
        return sp.getStringSet(key,defaultValue);
    }

    //Aditionol method for String type

    public static void putSharedPreference(String key, String value){
        sp = MApplication.getInstance().getSharedPreferences("cookie-storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getSharedPreference(String key, @Nullable String defaultValue){
        sp = MApplication.getInstance().getSharedPreferences("cookie-storage", Context.MODE_PRIVATE);
        return sp.getString(key,defaultValue);
    }

}
