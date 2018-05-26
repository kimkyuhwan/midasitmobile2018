package com.example.hoyeonlee.example.Customer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoyeonlee.example.DataSchema.Menu;

import com.example.hoyeonlee.example.Adapter.Customer.CustomMenuAdapter;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {


    RecyclerView recyclerView;
    private CustomMenuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Menu> MenuList;
    private String category;


    public static final MenuFragment newInstance (String category) {
        MenuFragment f = new MenuFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("category",category);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_menu,container,false);
        category=getArguments().getString("category");
        recyclerView=(RecyclerView)view.findViewById(R.id.coffee_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(0);
        mAdapter=new CustomMenuAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initDataset();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDataset(){
        MApplication.getInstance().getApiService().getMenuWithCategory(category).enqueue(new Callback<ArrayList<Menu>>() {
            @Override
            public void onResponse(Call<ArrayList<Menu>> call, Response<ArrayList<Menu>> response) {
                ArrayList<Menu> menuArrayList=response.body();
                MenuList=new ArrayList<>();
                for(int i=0;i<menuArrayList.size();i++) {
                    MenuList.add(menuArrayList.get(i));
                    Log.d("DEBUGYU",menuArrayList.get(i).getName());
                }
                mAdapter.addAllMenus(MenuList);
            }

            @Override
            public void onFailure(Call<ArrayList<Menu>> call, Throwable t) {
                Toast.makeText(getActivity(),"실패!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
