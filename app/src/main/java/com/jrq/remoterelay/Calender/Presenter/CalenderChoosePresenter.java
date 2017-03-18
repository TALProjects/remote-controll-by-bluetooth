package com.jrq.remoterelay.Calender.Presenter;

import android.support.v4.app.FragmentActivity;

import com.jrq.remoterelay.Calender.MvpCalenderChooseView;
import com.jrq.remoterelay.Calender.RecyclerHorizontalViewAdapter;
import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.RemoteApplication;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-10-24.
 */

public class CalenderChoosePresenter implements RecyclerHorizontalViewAdapter.ChangeItemInRecycler{

    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    private CalenderRelay calenderRelay;
    private int positionWeek;
    private String dayOfTheWeek = "Doba";

    String[] daysWeek = {"Doba", "Monday", "Tuesday", "Weednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static FragmentActivity contextView;
    private static MvpCalenderChooseView mvpView;

    private CalenderChoosePresenter calenderPresenter;
    private int position;

    public void attachView(MvpCalenderChooseView mvpView, FragmentActivity context) {
        this.contextView = context;
        this.mvpView = mvpView;

        calenderPresenter = this;

        ((RemoteApplication) context.getApplication())
                .getComponent()
                .inject(this);
    }

    @Override
    public void changeItemInHorizontalRecycler(int position) {
        this.position = position;
    }

    public void getCalenderRelays(final String macBluetooth, final CalenderChoosePresenter presenter, int nameRelay) {
        mSubscription = databaseHelper.getCalenderRelay(macBluetooth, Integer.toString(nameRelay + 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CalenderRelay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(CalenderRelay calenderRelays) {
                        calenderRelay = calenderRelays;
                        mvpView.refreshListView(calenderRelay, daysWeek[position]);
                    }
                });
    }


}
