package com.pda.tocong.personaldigitalassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pda.tocong.personaldigitalassistant.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar mBOBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mBOBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBOBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBOBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.foodtypelist,"foodmenu"))
        .addItem(new BottomNavigationItem(R.mipmap.checkout,"check out"))
        .addItem(new BottomNavigationItem(R.mipmap.more,"more"))
        .setFirstSelectedPosition(0)
        .initialise();

    }

    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
