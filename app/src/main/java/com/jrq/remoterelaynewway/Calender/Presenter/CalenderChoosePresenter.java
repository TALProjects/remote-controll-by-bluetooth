package com.jrq.remoterelaynewway.Calender.Presenter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.jrq.remoterelaynewway.Calender.CalenderRelayRecyclerAdapter;
import com.jrq.remoterelaynewway.Calender.MvpCalenderChooseView;
import com.jrq.remoterelaynewway.Calender.MvpCalenderView;
import com.jrq.remoterelaynewway.Calender.RecyclerHorizontalViewAdapter;
import com.jrq.remoterelaynewway.Calender.RecyclerVerticalViewAdapter;
import com.jrq.remoterelaynewway.Calender.View.CalenderChooseFragment;
import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.CalenderRelay;
import com.jrq.remoterelaynewway.RemoteApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
