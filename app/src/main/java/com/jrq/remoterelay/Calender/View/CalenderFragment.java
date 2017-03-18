package com.jrq.remoterelay.Calender.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jrq.remoterelay.Calender.CalenderRecyclerView;
import com.jrq.remoterelay.Calender.CalenderRelayRecyclerAdapter;
import com.jrq.remoterelay.Calender.MvpCalenderView;
import com.jrq.remoterelay.Calender.Presenter.CalenderPresenter;
import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.R;
import com.jrq.remoterelay.RemoteApplication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-09-12.
 */
public class CalenderFragment extends Fragment implements MvpCalenderView {

    @BindView(R.id.calender_recycler_view)
    public RecyclerView recyclerView;

    @Inject
    CalenderPresenter calenderPresenter;

    public static CalenderRelayRecyclerAdapter calenderRecyclerAdapter;

    public static CalenderRecyclerView calenderRecyclerView;
    private static List<CalenderRelay> calenderRelayList;
    private static boolean isStartContentView = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true) {
            calenderPresenter.registerEventBus(true);
            if(isStartContentView == false) {
                calenderPresenter.startBluetoothServices();
                isStartContentView = true;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((RemoteApplication) this.getActivity().getApplication())
                .getComponent()
                .inject(this);

        calenderPresenter.attachView(this, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calender_fragment, container, false);
        ButterKnife.bind(this, v);

        calenderRelayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isStartContentView == true) {
            SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
            Gson gson = new Gson();
            String json = prefs.getString("calenderProperties", "");

            Type type = new TypeToken<List<CalenderRelay>>() {}.getType();
            ArrayList<CalenderRelay> tDeviceProperties = new ArrayList<CalenderRelay>();
            tDeviceProperties = gson.fromJson(json, type);
            if (tDeviceProperties != null) {
                refreshListView(tDeviceProperties);
            } else {
                calenderRelayList.clear();
                refreshListView(calenderRelayList);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(calenderRelayList);
            editor.putString("calenderProperties", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    @Override
    public void refreshListView(List<CalenderRelay> recyclerViewsRelays) {
        if(calenderRelayList.size() > 0) {
            calenderRelayList = recyclerViewsRelays;
            calenderRecyclerAdapter = (CalenderRelayRecyclerAdapter) recyclerView.getAdapter();
            calenderRecyclerAdapter.setCalenderRelays(calenderRelayList);
            calenderRecyclerAdapter.notifyDataSetChanged();
        } else {
            calenderRelayList = recyclerViewsRelays;
            calenderRecyclerAdapter = new CalenderRelayRecyclerAdapter(getActivity(), calenderPresenter, calenderRelayList);
            recyclerView.setAdapter(calenderRecyclerAdapter);
        }
    }
}
