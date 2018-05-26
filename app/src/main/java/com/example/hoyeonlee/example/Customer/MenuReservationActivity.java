package com.example.hoyeonlee.example.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoyeonlee.example.BackActionBarActivity;
import com.example.hoyeonlee.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuReservationActivity extends BackActionBarActivity {

    @BindView(R.id.menu_tab)
    TabLayout menuTab;
    @BindView(R.id.menu_pager)
    ViewPager menuPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reservation);
        ButterKnife.bind(this);

        setToolbar();
        setTitle("메뉴 주문");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menuTab.setTabGravity(TabLayout.GRAVITY_FILL);

        MenuTabPagerAdapter pagerAdapter = new MenuTabPagerAdapter(getSupportFragmentManager(), menuTab.getTabCount());
        menuPager.setAdapter(pagerAdapter);

        menuPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menuTab));

        menuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                menuPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shopping_basket) {
            Intent it=new Intent(this,ShoppingBasketActivity.class);
            startActivity(it);
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}
