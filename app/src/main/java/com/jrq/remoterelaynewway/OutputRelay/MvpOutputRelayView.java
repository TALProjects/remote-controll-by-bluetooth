package com.jrq.remoterelaynewway.OutputRelay;

import com.jrq.remoterelaynewway.Database.Model.OutputRelay;

import java.util.List;

/**
 * Created by jrq on 2016-09-15.
 */

public interface MvpOutputRelayView {
    public void refreshListView(List<OutputRelay> outputRelays);
    public void setStartContentView();
}
