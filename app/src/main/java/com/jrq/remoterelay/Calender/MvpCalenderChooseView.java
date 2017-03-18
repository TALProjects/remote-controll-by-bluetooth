package com.jrq.remoterelay.Calender;

import com.jrq.remoterelay.Database.Model.CalenderRelay;

/**
 * Created by jrq on 2016-10-29.
 */

public interface MvpCalenderChooseView {
    public void refreshListView(CalenderRelay calenderRelays, String weekDays);
}
