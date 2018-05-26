package com.example.hoyeonlee.example.Admin.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.hoyeonlee.example.Admin.Menu.MenuActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.User;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hoyeonlee on 2018. 3. 23..
 */

public class UserDialog extends Dialog {
    private static final String TAG = "USER_DIALOG_LOG";
    UserActivity context;
    ApiService apiService;
    User user;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_supervisor)
    Button btnSupervisor;

    public UserDialog(@NonNull Context context) {
        super(context);
        this.context = (UserActivity) context;
    }

    //For Update Constructor
    public UserDialog(@NonNull Context context, User user) {
        super(context);
        this.context = (UserActivity) context;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user);
        ButterKnife.bind(this);
        apiService = MApplication.getInstance().getApiService();

        //Dialog Size 조정
//        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
//        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = (int) (metrics.heightPixels * 0.4); //set height to 90% of total
//        int width = (int) (metrics.widthPixels * 0.8); //set width to 90% of total
//        getWindow().setLayout(width, height); //set layout

        if(user.getIsStaff()){
            btnSupervisor.setText("관리자 해임");
        }else{
            btnSupervisor.setText("관리자 위임");
        }



    }

    @OnClick({R.id.btn_delete, R.id.btn_supervisor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("정말 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setNegativeButton("취소", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("확인", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                deleteMenu(prevMenu);
                                dismiss();
                            }
                        }).show();
                break;
            case R.id.btn_supervisor:
                break;
        }
    }
}
