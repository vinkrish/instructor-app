package com.aanglearning.instructorapp.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aanglearning.instructorapp.dashboard.Attendance.AttendanceFragment;
import com.aanglearning.instructorapp.dashboard.Home.HomeFragment;
import com.aanglearning.instructorapp.dashboard.Homework.HomeworkFragment;
import com.aanglearning.instructorapp.model.Attendance;

/**
 * Created by Vinay on 22-02-2017.
 */

class DashboardPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Home", "Attendance", "Homework" };

    DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return AttendanceFragment.newInstance();
            case 2:
                return HomeworkFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
