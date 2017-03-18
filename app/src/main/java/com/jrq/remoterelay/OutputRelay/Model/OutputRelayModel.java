package com.jrq.remoterelay.OutputRelay.Model;

import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.OutputRelay.Presenter.OutputRelayControlPresenter;

import javax.inject.Inject;

import rx.Subscription;

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
