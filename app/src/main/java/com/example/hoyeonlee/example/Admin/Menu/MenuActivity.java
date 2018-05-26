package com.example.hoyeonlee.example.Admin.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.Adapter.Admin.MenuAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.Etc.OnLongClickListener;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class MenuActivity extends BackActionBarActivity {
    public static final int PICK_IMAGE_REQUEST = 1000;
    private static final String TAG = "MENU_ACTIVITY";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu_listview)
    RecyclerView menuListview;
    @BindView(R.id.btn_insert)
    FloatingActionButton btnInsert;
    MenuUpdateDialog dialog;
    MenuAdapter adapter;
    ApiService apiService;
    @BindView(R.id.menu_tab)
    TabLayout menuTab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("메뉴 관리");
        apiService = MApplication.getInstance().getApiService();

        adapter = new MenuAdapter(this);
        menuListview.setLayoutManager(new LinearLayoutManager(this));
        menuListview.setAdapter(adapter);

        //LongClick Listener처리
        adapter.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public void longClick(View view, Object object, int position) {
                final Menu data = (Menu) object;
                dialog = new MenuUpdateDialog(MenuActivity.this, data);
                dialog.show();
            }
        });
        initialize();

        menuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        adapter.setType("커피");
                        break;
                    case 1:
                        adapter.setType("티");
                        break;
                    case 2:
                        adapter.setType("요거트");
                        break;
                    case 3:
                        adapter.setType("디저트");
                        break;
                    default:
                        adapter.setType("커피");
                        break;
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

    public void initialize() {
        //데이터 받기
        apiService.getAllMenus().enqueue(new Callback<ArrayList<Menu>>() {
            @Override
            public void onResponse(Call<ArrayList<Menu>> call, Response<ArrayList<Menu>> response) {
                if (response.isSuccessful()) {
                    adapter.addAllMenus(response.body());
                } else {
                    String code = String.valueOf(response.code());
                    if (code.equals("400")) {
                        try {
                            Log.v(TAG, response.errorBody().string());
                            Toast.makeText(MenuActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    Toast.makeText(MenuActivity.this, code + " Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Menu>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_insert)
    public void onViewClicked() {
        dialog = new MenuUpdateDialog(this);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(data.getData());
                dialog.setImage(getBytes(is), data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }


}
