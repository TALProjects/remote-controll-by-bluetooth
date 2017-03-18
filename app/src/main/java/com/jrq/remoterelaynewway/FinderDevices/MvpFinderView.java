package com.jrq.remoterelaynewway.FinderDevices;

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Created by jrq on 2016-09-15.
 */

public interface MvpFinderView  {
    public void refreshListView(PairedDevicesRecyclerAdapter findedDevices, ArrayList<DevicesProperties> devicesProperties);
    public void dissableSearchButton(boolean disabled);
    public void showLoadingProgressBar(boolean enable);
}
