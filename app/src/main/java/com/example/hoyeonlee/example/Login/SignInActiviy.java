package com.example.hoyeonlee.example.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.MainActivity;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;
import com.example.hoyeonlee.example.R;
import com.example.hoyeonlee.example.Utils.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
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
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.btn_signin, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                MApplication.getInstance().getApiService()
                        .signIn(inputEmail.getText().toString(), inputPassword.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                String code = String.valueOf(response.code());
                                if (code.charAt(0) == '4' || code.charAt(0) == '5') {
                                    if (code.equals("400")) {
                                        Toast.makeText(SignInActiviy.this, "아이디 비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(SignInActiviy.this, code + " Error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                try {
                                    //토큰 저장하기
                                    JSONObject result = new JSONObject(response.body().string());
                                    SharedPreferenceBase.putSharedPreference(Preferences.SHARED_PREFERENCE_NAME_COOKIE, result.getString("key"));
                                    SharedPreferenceBase.putSharedPreference("login", true);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(SignInActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btn_signup:
                startActivity(new Intent(getApplicationContext(), SignUpActiviy.class));
                break;
        }
    }
}
