package com.example.hoyeonlee.example.Customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hoyeonlee.example.Adapter.Customer.EventMenuAdapter;
import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.DataSchema.Event;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventCheckActivity extends BackActionBarActivity {

    private RecyclerView recyclerView;
    private EventMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Event> events=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_check);
        setToolbar();
        setTitle("이벤트 게시판");
        recyclerView = (RecyclerView) findViewById(R.id.event_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EventMenuAdapter(this);
        recyclerView.setAdapter(adapter);

        getEventList();

    }

    void getEventList(){
        MApplication.getInstance().getApiService().getEvents().enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if(response.isSuccessful()){
                    events=response.body();
                    Log.d("DEBUGYU",""+events.size());
                    for(int i=0;i<events.size();i++){
                        Log.d("DEBUGYU",events.get(i).toString());
                    }
                    adapter.addAllEvent(events);
                    adapter.notifyDataSetChanged();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

            }
        });
    }
}
