package com.example.hoyeonlee.example.Admin.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.hoyeonlee.example.Adapter.Admin.UserAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.User;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class UserActivity extends BackActionBarActivity {

    @BindView(R.id.menu_tab)
    TabLayout menuTab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("사용자 관리");
        userAdapter = new UserAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        initialize();
        userAdapter.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public void longClick(View v, Object object, int position) {
                UserDialog dialog = new UserDialog(UserActivity.this,(User)object);
                dialog.show();
            }
        });
    }
    public void initialize(){
        getUsers(false);
        menuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1){
                    getUsers(true);
                }else{
                    getUsers(false);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void getUsers(final boolean isOnlyStaff){
        MApplication.getInstance().getApiService().getUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        if(isOnlyStaff){
                            userAdapter.clear();
                            userAdapter.addOnlyStaffs(response.body());
                        }else{
                            userAdapter.clear();
                            userAdapter.addAllUsers(response.body());
                        }
                    } else {
                        Toast.makeText(UserActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(UserActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
