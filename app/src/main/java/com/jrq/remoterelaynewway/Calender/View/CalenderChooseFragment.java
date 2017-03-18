package com.jrq.remoterelaynewway.Calender.View;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;

import com.gc.materialdesign.views.ButtonRectangle;
import com.jrq.remoterelaynewway.Calender.CalenderRelayRecyclerAdapter;
import com.jrq.remoterelaynewway.Calender.MvpCalenderChooseView;
import com.jrq.remoterelaynewway.Calender.Presenter.CalenderChoosePresenter;
import com.jrq.remoterelaynewway.Calender.RecyclerHorizontalViewAdapter;
import com.jrq.remoterelaynewway.Calender.RecyclerVerticalViewAdapter;
import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.CalenderRelay;
import com.jrq.remoterelaynewway.R;
import com.jrq.remoterelaynewway.RemoteApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-10-24.
 */

public class CalenderChooseFragment extends DialogFragment implements RecyclerHorizontalViewAdapter.ChangeItemInRecycler, MvpCalenderChooseView {
    private static CalenderChooseFragment calenderChooseFragment;
    private static Application application;
    private static ArrayList<CalenderRelay> calenderRelayArrayList;
    private static String macBluetoothAddress;
    private static CalenderRelay calenderRelayList;
    private static int mPosition;

    @Inject CalenderChoosePresenter calenderChoosePresenter;
    private static ChangeDateInCalender mChangeDateInCalender;

    @BindView(R.id.calender_horizontal_recycler_view) RecyclerView recyclerHorizontalView;
    @BindView(R.id.calender_vertical_recycler_view) RecyclerView recyclerVerticalView;

    @BindView(R.id.offbutton) ButtonRectangle offButton;
    @BindView(R.id.dobabutton)ButtonRectangle dobaButton;
    @BindView(R.id.weekbutton) ButtonRectangle weekButton;

    @BindView(R.id.saveButtonCalender) ButtonRectangle saveButton;
    @BindView(R.id.cancelButtonCalender) ButtonRectangle cancelButton;

    RecyclerHorizontalViewAdapter recyclerHorizontalAdapter;
    RecyclerVerticalViewAdapter recyclerVerticalAdapter;

    public static CalenderChooseFragment newInstance(int position, Application app, String macBluetoothAddr, ChangeDateInCalender changeDateInCalender) {
        application = app;
        macBluetoothAddress = macBluetoothAddr;
        mPosition = position;
        mChangeDateInCalender = changeDateInCalender;

        if(calenderChooseFragment == null) {
            calenderChooseFragment = new CalenderChooseFragment();
        }
        return calenderChooseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calender_choose_fragment, container, false);
        ButterKnife.bind(this, v);

        ((RemoteApplication) application)
                .getComponent()
                .inject(this);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        recyclerVerticalView.setItemViewCacheSize(4);
        recyclerHorizontalView.setHasFixedSize(true);
        recyclerVerticalView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);

        recyclerHorizontalView.setLayoutManager(linearLayoutManager);
        recyclerVerticalView.setLayoutManager(gridLayoutManager);

        recyclerHorizontalAdapter = new RecyclerHorizontalViewAdapter(this);
        recyclerHorizontalView.setAdapter(recyclerHorizontalAdapter);

        recyclerVerticalAdapter = new RecyclerVerticalViewAdapter(calenderRelayList, "Doba", getActivity(), calenderChoosePresenter, getActivity().getSupportFragmentManager());
        recyclerVerticalView.setAdapter(recyclerVerticalAdapter);

        changeItemInHorizontalRecycler(0);

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeDateInCalender.changeStatusRelay(Integer.toString(mPosition + 1), "off");
            }
        });

        dobaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeDateInCalender.changeStatusRelay(Integer.toString(mPosition + 1), "doba");
            }
        });

        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeDateInCalender.changeStatusRelay(Integer.toString(mPosition + 1), "tydzień");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recyclerVerticalAdapter.changeDoba == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourDoba, "Doba");
                    recyclerVerticalAdapter.changeDoba = false;
                }

                if(recyclerVerticalAdapter.changeMonday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourMonday, "Monday");
                    recyclerVerticalAdapter.changeMonday = false;
                }

                if(recyclerVerticalAdapter.changeTuesday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourTuesday, "Tuesday");
                    recyclerVerticalAdapter.changeTuesday = false;
                }

                if(recyclerVerticalAdapter.changeWednesday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourWednesday, "Wednesday");
                    recyclerVerticalAdapter.changeWednesday = false;
                }

                if(recyclerVerticalAdapter.changeThursday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourThursday, "Thursday");
                    recyclerVerticalAdapter.changeThursday = false;
                }

                if(recyclerVerticalAdapter.changeFriday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourFriday, "Friday");
                    recyclerVerticalAdapter.changeFriday = false;
                }

                if(recyclerVerticalAdapter.changeSaturday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourSaturday, "Saturday");
                    recyclerVerticalAdapter.changeSaturday = false;
                }

                if(recyclerVerticalAdapter.changeSunday == true) {
                    mChangeDateInCalender.addToListForChangeCalender(Integer.toString(mPosition + 1), recyclerVerticalAdapter.hourSunday, "Sunday");
                    recyclerVerticalAdapter.changeSunday = false;
                }
                mChangeDateInCalender.changeDateInCalender(Integer.toString(mPosition + 1));

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void changeItemInHorizontalRecycler(int position) {
        recyclerVerticalAdapter.changeDayOfWeek(position);
        recyclerVerticalAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshListView(CalenderRelay calenderRelays, String weekDays) {
        calenderRelayList = calenderRelays;
        recyclerVerticalAdapter = (RecyclerVerticalViewAdapter) recyclerVerticalView.getAdapter();
        recyclerVerticalAdapter.setCalenderRelays(calenderRelayList, weekDays);
        recyclerVerticalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        calenderChoosePresenter.attachView(this, getActivity());
        calenderChoosePresenter.getCalenderRelays(macBluetoothAddress, calenderChoosePresenter, mPosition);
    }

    public interface ChangeDateInCalender {
        public void changeDateInCalender(String numberRelay);
        public void changeStatusRelay(String numberRelay, String status);
        public void addToListForChangeCalender(String numberRelay, ArrayList<String> timerProperties, String week);
    }
}
