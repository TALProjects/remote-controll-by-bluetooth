package com.jrq.remoterelay.Settings.Presenter;

import android.support.v4.app.FragmentActivity;

import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.Database.Model.TimeConnectRelay;
import com.jrq.remoterelay.RemoteApplication;
import com.jrq.remoterelay.Settings.View.SettingsRelayFragment;

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
