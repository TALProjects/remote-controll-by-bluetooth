package com.jrq.remoterelaynewway.Calender;

import com.jrq.remoterelaynewway.Database.Model.CalenderRelay;
import com.jrq.remoterelaynewway.Database.Model.ThermostatRelay;

import java.util.List;

/**
 * Created by jrq on 2016-10-05.
 */

public interface MvpCalenderView {
    public void refreshListView(List<CalenderRelay> calenderRelays);
}
