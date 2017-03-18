package com.jrq.remoterelay.Calender;

import com.jrq.remoterelay.Database.Model.CalenderRelay;

import java.util.List;

/**
 * Created by jrq on 2016-10-05.
 */

public interface MvpCalenderView {
    public void refreshListView(List<CalenderRelay> calenderRelays);
}
