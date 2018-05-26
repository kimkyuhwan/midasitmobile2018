package com.example.hoyeonlee.example.Admin.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Report;
import com.example.hoyeonlee.example.DataSchema.User;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.btn_detail)
    Button btnDetail;

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

        if (user.getIsStaff()) {
            btnSupervisor.setText("관리자 해임");
        } else {
            btnSupervisor.setText("관리자 위임");
        }


    }

    @OnClick({R.id.btn_delete, R.id.btn_supervisor,R.id.btn_detail})
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
                                deleteUser(user);
                                dismiss();
                            }
                        }).show();
                break;
            case R.id.btn_supervisor:
                updateUser(user);
                dismiss();
                break;
            case R.id.btn_detail:
                dismiss();
                context.goDetail();
                break;
        }
    }

    public void deleteUser(final User user) {
        apiService.deleteUser(user.getId() + "").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        context.adapter.deleteUser(user);
                    } else {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, response.code() + " Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void updateUser(final User user) {
        MultipartBody requestbody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("is_staff", user.getIsStaff() ? "false" : "true").build();

        apiService.updateUser(user.getId() + "", requestbody).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful()) {
                        context.adapter.updateUser(user, response.body());
                    } else {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, response.code() + " Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


}
