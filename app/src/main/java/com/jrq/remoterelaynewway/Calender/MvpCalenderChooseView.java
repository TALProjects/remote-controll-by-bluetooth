package com.jrq.remoterelaynewway.Calender;

import com.jrq.remoterelaynewway.Database.Model.CalenderRelay;

import java.util.List;

/**
 * Created by jrq on 2016-10-29.
 */

public interface MvpCalenderChooseView {
    public void refreshListView(CalenderRelay calenderRelays, String weekDays);
}
