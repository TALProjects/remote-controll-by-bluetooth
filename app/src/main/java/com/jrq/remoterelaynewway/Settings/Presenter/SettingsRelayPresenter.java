package com.jrq.remoterelaynewway.Settings.Presenter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.Database.Model.TimeConnectRelay;
import com.jrq.remoterelaynewway.RemoteApplication;
import com.jrq.remoterelaynewway.Settings.View.SettingsRelayFragment;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-11-19.
 */

public class SettingsRelayPresenter {

    private static FragmentActivity contextView;
    private static SettingsRelayFragment settingsRelayFragment;
    private Subscription mSubscription;

    @Inject
    DatabaseHelper databaseHelper;

    public void attachView(FragmentActivity context, SettingsRelayFragment settingsRelayFragment) {
        this.contextView = context;
        this.settingsRelayFragment = settingsRelayFragment;

        ((RemoteApplication) context.getApplication())
                .getComponent()
                .inject(this);
    }

    public void saveTimeConnect(int time) {
        mSubscription =  databaseHelper.updateTimeConnectRelays(time)
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
                        settingsRelayFragment.setTimeContent(timeConnect.getTime());
                    }
                });
    }

    public void getTimeConnect() {
        mSubscription = databaseHelper.getTimeConnectRelay()
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
                        settingsRelayFragment.setTimeContent(timeConnect.getTime());
                    }
                });
    }
}
