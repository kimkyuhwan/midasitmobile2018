package com.example.hoyeonlee.example.Customer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MenuTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount=0;
    private final int TAB_NUMBER_MENU_COFFEE=0;
    private final int TAB_NUMBER_MENU_TEA=1;
    private final int TAB_NUMBER_MENU_YOGERT=2;
    private final int TAB_NUMBER_MENU_DESSERT=3;
    public MenuTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        MenuFragment menuFragment;
        switch (position){
            case TAB_NUMBER_MENU_COFFEE:
                menuFragment =MenuFragment.newInstance("커피");
                return menuFragment;
            case TAB_NUMBER_MENU_TEA:
                menuFragment =MenuFragment.newInstance("티");
                return menuFragment;
            case TAB_NUMBER_MENU_YOGERT:
                menuFragment =MenuFragment.newInstance("요거트");
                return menuFragment;
            case TAB_NUMBER_MENU_DESSERT:
                menuFragment =MenuFragment.newInstance("디저트");
                return menuFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
