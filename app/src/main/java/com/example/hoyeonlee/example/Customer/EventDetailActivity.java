package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends BackActionBarActivity {

    @BindView(R.id.event_title)
    TextView eventTitle;
    @BindView(R.id.event_body)
    TextView eventBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("이벤트 조회");

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String body = intent.getExtras().getString("body");
        eventTitle.setText(title);
        eventBody.setText(body);
    }
}
