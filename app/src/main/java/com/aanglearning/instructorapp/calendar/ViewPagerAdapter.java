package com.aanglearning.instructorapp.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private CharSequence titles[]= {"Calendar","All Events"};
    private Evnts evnts;

    ViewPagerAdapter(FragmentManager fm, Evnts evnts) {
        super(fm);
        this.evnts = evnts;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return CompactCalendarFragment.newInstance(evnts);
        } else {
            EventsFragment eventsFragment = new EventsFragment();
            return eventsFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}