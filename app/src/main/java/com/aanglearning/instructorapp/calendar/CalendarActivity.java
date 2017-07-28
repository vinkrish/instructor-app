package com.aanglearning.instructorapp.calendar;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aanglearning.instructorapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.tabs;

public class CalendarActivity extends AppCompatActivity {
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;

    private CharSequence titles[]= {"Calendar","All Events"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        setUpViewPager();
    }

    private void setUpViewPager() {
        //viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, 2);
        viewPager.setAdapter(adapter);
    }
}
