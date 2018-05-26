package com.example.hoyeonlee.example.Admin.Log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 5. 27..
 */

public class LogActivity extends BackActionBarActivity {
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("관리자 로그");
        MApplication.getInstance().getApiService().getLog().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                try {
                    if (response.isSuccessful()) {
                        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(LogActivity.this, android.R.layout.simple_list_item_1, response.body());
                        listView.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(LogActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LogActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
