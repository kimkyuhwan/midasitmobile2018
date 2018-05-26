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

public class SignUpActiviy extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_password_re)
    EditText inputPasswordRe;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        //Toolbar Title정하기
        tvTitle.setText("회원가입");
        // TODO 이름 검증에 대한 내용이 없습니다.
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        if (inputEmail.getText().toString().length() == 0) {
            Toast.makeText(SignUpActiviy.this, "아이디를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        //비밀번호 검증 및 자리수 검증
        if (inputPassword.getText().toString().length() < 8
                || !inputPassword.getText().toString().equals(inputPasswordRe.getText().toString())) {
            Toast.makeText(SignUpActiviy.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        MApplication.getInstance().getApiService()
                .signUp(inputEmail.getText().toString(), inputPassword.getText().toString(), inputPasswordRe.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String code = String.valueOf(response.code());
                        if (code.charAt(0) == '4' || code.charAt(0) == '5') {
                            if (code.equals("400")) {
                                Toast.makeText(getApplicationContext(), "아이디 비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), code + " Error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            JSONObject result = new JSONObject(response.body().string());
                            SharedPreferenceBase.putSharedPreference(Preferences.SHARED_PREFERENCE_NAME_COOKIE, result.getString("key"));
                            SharedPreferenceBase.putSharedPreference("login", true);
                            //SignIn , SignUp Activity 모두 없애고 로그인
                            Intent intent = new Intent(getApplicationContext(), SignInActiviy.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUpActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
