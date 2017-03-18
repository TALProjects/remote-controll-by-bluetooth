package com.jrq.remoterelaynewway.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jrq.remoterelaynewway.Calender.View.CalenderFragment;
import com.jrq.remoterelaynewway.FinderDevices.View.FinderDevicesFragment;
import com.jrq.remoterelaynewway.OutputRelay.View.OutputRelayControlFragment;
import com.jrq.remoterelaynewway.Settings.View.SettingsRelayFragment;
import com.jrq.remoterelaynewway.Thermostat.View.ThermostatFragment;

/* jswiech */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private CharSequence titles[];
    private int numberOfTabs;

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs) {
        super(fragmentManager);
        this.titles = titles;
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new FinderDevicesFragment();
        } else if(position == 1){
            return new OutputRelayControlFragment();
        } else if(position == 2){
            return new ThermostatFragment();
        } else if(position == 3){
            return new CalenderFragment();
        } else {
            return new SettingsRelayFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}