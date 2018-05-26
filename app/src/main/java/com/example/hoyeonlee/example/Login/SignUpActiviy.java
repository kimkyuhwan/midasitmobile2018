package com.example.hoyeonlee.example.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.group_gender)
    RadioGroup groupGender;

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
        if (inputEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(SignUpActiviy.this, "아이디를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        //비밀번호 검증 및 자리수 검증
        if (inputPassword.getText().toString().length() < 8
                || !inputPassword.getText().toString().equals(inputPasswordRe.getText().toString())) {
            Toast.makeText(SignUpActiviy.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if(inputName.getText().toString().trim().length() < 2){
            Toast.makeText(this, "이름을 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", inputEmail.getText().toString())
                .addFormDataPart("password1", inputPassword.getText().toString())
                .addFormDataPart("password2", inputPasswordRe.getText().toString())
                .build();

        MApplication.getInstance().getApiService()
                .signUp(requestBody)
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
                            SharedPreferenceBase.putSharedPreference(Preferences.SHARED_PREFERENCE_NAME_COOKIE, result.getKey());
                            SharedPreferenceBase.putSharedPreference("login", true);
                            //SignIn , SignUp Activity 모두 없애고 로그인
                            final RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("first_name", inputName.getText().toString())
                                    .addFormDataPart("gender", groupGender.getCheckedRadioButtonId() == R.id.gender1 ? "true":"false")
                                    .build();
                            MApplication.getInstance().getApiService().setProfile(requestBody).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()){
                                        SharedPreferenceBase.putIntSharedPreference("status", 0);
                                        SharedPreferenceBase.putSharedPreference("name", response.body().getFirstName());
                                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(SignUpActiviy.this, "프로필 등록 에러", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(SignUpActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(SignUpActiviy.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int getStatus(User user) {
        if (user.getIsSuperuser()) {
            return 2;
        } else {
            if (user.getIsStaff()) {
                return 1;
            }
            return 0;
        }
    }
}
