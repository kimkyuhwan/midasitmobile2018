package com.example.hoyeonlee.example.Admin.Event;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventCreatelActivity extends BackActionBarActivity {


    @BindView(R.id.event_title)
    EditText eventTitle;
    @BindView(R.id.event_body)
    EditText eventBody;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    @BindView(R.id.writeBtn)
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("이벤트 생성");


    }

    @OnClick({R.id.cancelBtn, R.id.writeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.writeBtn:
                if(eventTitle.getText().toString().length()!=0 && eventBody.getText().toString().length()!=0){
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("title", eventTitle.getText().toString())
                            .addFormDataPart("description", eventBody.getText().toString())
                            .build();
                    MApplication.getInstance().getApiService().createEvent(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"성공적으로 등록되었습니다.",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                break;
        }
    }
}
