package com.example.hoyeonlee.example.Admin.User;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Report;
import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.DataSchema.ReservedItem;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hoyeonlee on 2018. 3. 23..
 */

public class ReportDialog extends Dialog {
    private static final String TAG = "REPORT_DIALOG_LOG";
    UserActivity context;
    ApiService apiService;
    Report report;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.listView)
    ListView listView;

    public ReportDialog(@NonNull Context context) {
        super(context);
        this.context = (UserActivity) context;
    }

    //For Update Constructor
    public ReportDialog(@NonNull Context context, Report report) {
        super(context);
        this.context = (UserActivity) context;
        this.report = report;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);
        ButterKnife.bind(this);
        apiService = MApplication.getInstance().getApiService();
        ArrayList<String> list = new ArrayList<>();
        for(Reservation item :report.getOrders()){
            String dateTime = DateTime.parse(item.getCreatedAt()).toString("MM월 dd일 HH시 mm분 ss초  ");
            String completed = item.getComplete()?"완료":"대기";
            list.add(dateTime+completed);
        }
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(mAdapter);

//        Dialog Size 조정
        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.9); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.9); //set width to 90% of total
        getWindow().setLayout(width, height); //set layout
        if (report != null) {
            initialize();
        } else {
            Toast.makeText(context, "오류", Toast.LENGTH_SHORT).show();
        }
    }

    public void initialize() {
        tvInfo.setText(report.getUser().getFirstName() + "님의 구매 기록");
        tvCount.setText("총 주문횟수 : " + report.getCount() + "회");
        tvAmount.setText("총 주문금액 : " + String.format("%,d원", Integer.valueOf(report.getAmount())));
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        dismiss();
    }
}
