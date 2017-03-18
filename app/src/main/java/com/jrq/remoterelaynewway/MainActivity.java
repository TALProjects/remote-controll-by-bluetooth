package com.jrq.remoterelaynewway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.TimeConnectRelay;
import com.jrq.remoterelaynewway.FinderDevices.DevicesProperties;
import com.jrq.remoterelaynewway.FinderDevices.PairedDevicesRecyclerAdapter;
import com.jrq.remoterelaynewway.Utils.BluetoothService;
import com.jrq.remoterelaynewway.Utils.CustomViewPager;
import com.jrq.remoterelaynewway.Utils.EventAddDevice;
import com.jrq.remoterelaynewway.Utils.EventBluetoothService;
import com.jrq.remoterelaynewway.Utils.EventCurrentDevice;
import com.jrq.remoterelaynewway.Utils.EventMessage;
import com.jrq.remoterelaynewway.Utils.SlidingTabLayout;
import com.jrq.remoterelaynewway.Utils.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.security.AccessController.getContext;

/**
 * Created by jrq on 2016-09-05.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.pager)
    CustomViewPager customViewPager;

    @BindView(R.id.tabs)
    SlidingTabLayout slidingTabLayout;

    private ViewPagerAdapter adapter;

    private CharSequence titlesOfTabs[] = {"Urządzenia", "Wyjścia", "Termostat", "Kalendarz", "Ustawienia"};
    private int numberOfTabs = 5;
    private EventBus eventBus;

    private Timer timer;

    @Inject
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        ((RemoteApplication) getApplication())
                .getComponent()
                .inject(this);

        registerEventBus(true);

        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        setSupportActionBar(toolbar);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titlesOfTabs, numberOfTabs);
        customViewPager.setAdapter(adapter);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(customViewPager);

        startService(BluetoothService.getStartIntent(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerEventBus(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        databaseHelper.getTimeConnectRelay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TimeConnectRelay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(TimeConnectRelay timeConnect) {
                        timer = new java.util.Timer();
                        timer.schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        eventBus.post(new EventBluetoothService("DISCONNECT"));
                                    }
                                },
                                timeConnect.getTime() * 1000
                        );
                    }
                });


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(timer != null) {
            timer.cancel();
        }
    }

    public void registerEventBus(boolean enable) {
        if(enable == true) {
            eventBus = EventBus.getDefault();
            eventBus.register(this);
        } else {
            if(eventBus != null) {
                eventBus.unregister(this);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBluetoothService event) {
        if (event.getMessage().equals("DISCONNECT_BLUETOOTH")) {
            customViewPager.setCurrentItem(0);
            customViewPager.setPagingEnabled(false);
        } else if (event.getMessage().equals("CONNECT_BLUETOOTH")) {
            customViewPager.setPagingEnabled(true);
        }
    }
}
