package com.example.hoyeonlee.example.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.Admin.User.UserActivity;
import com.example.hoyeonlee.example.Admin.HomeActivity;
import com.example.hoyeonlee.example.Customer.CustomerActivity;
import com.example.hoyeonlee.example.DataSchema.LoginResult;
import com.example.hoyeonlee.example.DataSchema.User;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.Utils.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActiviy extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_signin)
    Button btnSignin;
    @BindView(R.id.btn_signup)
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        tvTitle.setText("로그인");

        //로그인 여부 확인
        if (SharedPreferenceBase.getSharedPreference("login") == true) {
            //Code 가 0일 때 고객 , 1 2 일때 관리자
            if(SharedPreferenceBase.getIntSharedPreference("status") == 0){
                startActivity(new Intent(this, CustomerActivity.class));
            }else {
                startActivity(new Intent(this, HomeActivity.class));
            }
            finish();
        }
    }

    @OnClick({R.id.btn_signin, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", inputEmail.getText().toString())
                        .addFormDataPart("password", inputPassword.getText().toString())
                        .build();
                MApplication.getInstance().getApiService().signIn(requestBody)
                        .enqueue(new Callback<LoginResult>() {
                            @Override
                            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                                String code = String.valueOf(response.code());
                                if (code.charAt(0) == '4' || code.charAt(0) == '5') {
                                    if (code.equals("400")) {
                                        Toast.makeText(getApplicationContext(), "아이디 비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), code + " Error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (response.isSuccessful()) {
                                    LoginResult result = response.body();
                                    User user = result.getUser();
                                    SharedPreferenceBase.putSharedPreference(Preferences.SHARED_PREFERENCE_NAME_COOKIE, result.getKey());
                                    SharedPreferenceBase.putSharedPreference("name", user.getFirstName());
                                    SharedPreferenceBase.putIntSharedPreference("status", getStatus(user));
                                    SharedPreferenceBase.putSharedPreference("login", true);

                                    Intent intent;
                                    if(getStatus(user) != 0){
                                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    }else{
                                        intent = new Intent(getApplicationContext(), UserActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            @Override
                            public void onFailure(Call<LoginResult> call, Throwable t) {
                                Toast.makeText(SignInActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btn_signup:
                startActivity(new Intent(getApplicationContext(), SignUpActiviy.class));
                break;
        }
    }
    private int getStatus(User user){
        if(user.getIsSuperuser()){
            return 2;
        }else{
            if(user.getIsStaff()){
                return 1;
            }
            return 0;
        }
    }
}
