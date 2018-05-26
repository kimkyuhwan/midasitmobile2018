package com.example.hoyeonlee.example.Admin.Statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.Reservation;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.angmarch.views.NiceSpinner;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends BackActionBarActivity {

    ArrayList<Reservation> reservations;
    DateTime firstdate, lastdate, currentdate;
    int firstVal, lastVal;
    @BindView(R.id.leftBtn)
    ImageButton leftBtn;
    @BindView(R.id.current_month)
    TextView currentMonth;
    @BindView(R.id.rightBtn)
    ImageButton rightBtn;
    @BindView(R.id.monthChart)
    BarChart monthChart;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        setToolbar();
        setTitle("구매 내역");
        getData();
    }

    boolean isValidDate(String date) {
        DateTime now = DateTime.parse(date);
        return currentdate.getYear() == now.getYear() && currentdate.getMonthOfYear() == now.getMonthOfYear();
    }

    boolean isValidMonth(DateTime cur) {
        return firstdate.isBefore(cur) && lastdate.isAfter(cur);
    }

    void setTextDateTime() {
        String text = currentdate.toString("YYYY. MM");
        Log.d("DEBUGYU", text);
    }

    void setValidDataChart(int type) {
        ArrayList<BarEntry> ret = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("커피", 0);
        hashMap.put("티", 0);
        hashMap.put("요거트", 0);
        hashMap.put("디저트", 0);
        for (int i = 0; i < reservations.size(); i++) {
            if (isValidDate(reservations.get(i).getCreatedAt())) {
                Log.d("DEBUGYU", "DATE " + reservations.get(i).getCreatedAt());

                for (int j = 0; j < reservations.get(i).getOrders().size(); j++) {
                    String gender = reservations.get(i).getUser().getGender();
                    String category = reservations.get(i).getOrders().get(j).getMenu().getCategory();
                    int value = reservations.get(i).getOrders().get(j).getCount() * reservations.get(i).getOrders().get(j).getMenu().getPrice();
                    if (hashMap.containsKey(category)) {
                        int here = hashMap.get(category);
                        hashMap.put(category, value + here);
                    } else {
                        hashMap.put(category, value);
                    }
                }
            }
        }
        Iterator<Map.Entry<String, Integer>> it = hashMap.entrySet().iterator();
        int cnt = 0;
        while (it.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Log.d("DEBUGYU", pair.getKey() + " = " + pair.getValue());
            ret.add(new BarEntry(cnt++, pair.getValue()));
        }
        BarDataSet dataSet = new BarDataSet(ret, "");
        ArrayList<String> label = new ArrayList<String>();

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        YAxis leftAxis = monthChart.getAxisLeft();
        YAxis rightAxis = monthChart.getAxisRight();
        XAxis xAxis = monthChart.getXAxis();


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            String[] arr_value = {"티", "디저트", "커피", "요거트"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return arr_value[(int) value];
            }
        });
        xAxis.setGranularity(1.0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(20f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        data.setValueTextSize(20f);

        leftAxis.setTextSize(20f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);

        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        monthChart.setData(data);
        monthChart.setFitBars(true); // make the x-axis fit exactly all bars
        monthChart.invalidate(); // refresh
        monthChart.setScaleEnabled(false);
        monthChart.setDoubleTapToZoomEnabled(false);
        monthChart.setBackgroundColor(Color.rgb(255, 255, 255));
        monthChart.animateXY(2000, 2000);
        monthChart.setDrawBorders(false);
        monthChart.setDrawValueAboveBar(true);
    }


    void getData() {
        MApplication.getInstance().getApiService().getCompletedReservations().enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if (response.isSuccessful()) {
                    reservations = response.body();
                    if (reservations.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "기록이 없습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        Reservation first = reservations.get(0);
                        Reservation last = reservations.get(reservations.size() - 1);
                        firstdate = DateTime.parse(first.getCreatedAt());
                        lastdate = DateTime.parse(last.getCreatedAt());
                        currentdate = new DateTime(lastdate);
                        Log.d("DEBUGYU", firstdate.toString());
                        Log.d("DEBUGYU", lastdate.toString());
                        Log.d("DEBUGYU", currentdate.toString());
                        setTextDateTime();
                        setValidDataChart(type);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "확인 실패", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_LONG).show();

            }
        });
    }

    @OnClick({R.id.leftBtn, R.id.rightBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                if (isValidMonth(currentdate.minusMonths(1))) {
                    currentdate = currentdate.minusMonths(1);
                    setTextDateTime();
                    setValidDataChart(type);
                }
                break;
            case R.id.rightBtn:
                if (isValidMonth(currentdate.plusMonths(1))) {
                    currentdate = currentdate.plusMonths(1);
                    setTextDateTime();
                    setValidDataChart(type);
                }
                break;
        }
    }
}
