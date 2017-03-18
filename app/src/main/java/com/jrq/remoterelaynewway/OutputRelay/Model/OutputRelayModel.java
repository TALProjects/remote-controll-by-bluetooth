package com.jrq.remoterelaynewway.OutputRelay.Model;

import android.util.Log;

import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelaynewway.RemoteApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-09-16.
 */

public class OutputRelayModel {

    Subscription mSubscription;

    @Inject
    DatabaseHelper databaseHelper;

    @Inject
    OutputRelayControlPresenter outputRelayControlPresenter;

    public OutputRelayModel() {

    }
}
