package com.example.hoyeonlee.example.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.*;
import com.example.hoyeonlee.example.Network.SharedPreferenceBase;
import com.example.hoyeonlee.example.Utils.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Toolbar Title정하기
        ((TextView) findViewById(R.id.tv_title)).setText("회원가입");
        final EditText emailInput = findViewById(R.id.input_email);
        final EditText pwInput = findViewById(R.id.input_password);
        final EditText pwInput_re = findViewById(R.id.input_password_re);

        findViewById(R.id.btn_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디 검증
                if(emailInput.getText().toString().trim().length() == 0){
                    Toast.makeText(SignUpActiviy.this, "아이디를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //비밀번호 검증 및 자리수 검증
                if(pwInput.getText().toString().trim().length() >= 8
                        || pwInput.getText().toString().equals(pwInput_re.getText().toString())){
                    Toast.makeText(SignUpActiviy.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                MApplication.getInstance().getApiService()
                        .signUp(emailInput.getText().toString(),pwInput.getText().toString(),pwInput_re.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                String code = String.valueOf(response.code());
                                if(code.charAt(0) == '4'|| code.charAt(0) == '5'){
                                    if(code.equals("400")){
                                        Toast.makeText(getApplicationContext(), "아이디 비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), code+" Error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                try {
                                    JSONObject result = new JSONObject(response.body().string());
                                    SharedPreferenceBase.putSharedPreference(Preferences.SHARED_PREFERENCE_NAME_COOKIE,result.getString("key"));
                                    SharedPreferenceBase.putSharedPreference("login", true);
                                    //SignIn , SignUp Activity 모두 없애고 로그인
                                    Intent intent = new Intent(getApplicationContext(), SignInActiviy.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(SignUpActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
