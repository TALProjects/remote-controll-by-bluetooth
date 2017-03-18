package com.jrq.remoterelaynewway.Injection.Modules;

import android.app.Application;
import android.content.Context;

import com.jrq.remoterelaynewway.Calender.Presenter.CalenderChoosePresenter;
import com.jrq.remoterelaynewway.Calender.Presenter.CalenderPresenter;
import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.DatabaseOpenHelper;
import com.jrq.remoterelaynewway.FinderDevices.Presenter.FinderDevicesPresenter;
import com.jrq.remoterelaynewway.OutputRelay.Model.OutputRelayModel;
import com.jrq.remoterelaynewway.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelaynewway.RemoteApplication;
import com.jrq.remoterelaynewway.Settings.Presenter.SettingsRelayPresenter;
import com.jrq.remoterelaynewway.Thermostat.Model.ThermostatModel;
import com.jrq.remoterelaynewway.Thermostat.Presenter.ThermostatPresenter;
import com.jrq.remoterelaynewway.Utils.GeoLocationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jrq on 2016-09-12.
 */

@Module
public class AppModule {
    private final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    Context provideContext() {
        return app;
    }

    @Provides
    Application provideApplication(){
        return app;
    }

    @Provides
    @Singleton
    FinderDevicesPresenter provideFinderDevicesPresenter() {
        return new FinderDevicesPresenter();
    }

    @Provides
    @Singleton
    OutputRelayControlPresenter provideOutputRelayControlPresenter() {
        return new OutputRelayControlPresenter();
    }

    @Provides
    @Singleton
    OutputRelayModel provideOutputRelayModel() {
        return new OutputRelayModel();
    }

    @Provides
    @Singleton
    DatabaseOpenHelper provideDatabaseOpenHelper(Context context) {
        return new DatabaseOpenHelper(context);
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(DatabaseOpenHelper databaseOpenHelper) {
        return new DatabaseHelper(databaseOpenHelper);
    }

    @Provides
    @Singleton
    GeoLocationService provideGeoLocationService(Context context) {
        return new GeoLocationService(context);
    }

    @Provides
    @Singleton
    ThermostatPresenter provideThermostatPresenter() {
        return new ThermostatPresenter();
    }

    @Provides
    @Singleton
    ThermostatModel provideThermostatModel(Context context) {
        return new ThermostatModel(context);
    }

    @Provides
    @Singleton
    CalenderPresenter provideCalenderPresenter() {
        return new CalenderPresenter();
    }

    @Provides
    @Singleton
    CalenderChoosePresenter provideCalenderChoosePresenter() {
        return new CalenderChoosePresenter();
    }

    @Provides
    @Singleton
    SettingsRelayPresenter provideSettingsRelayPresenter() {
        return new SettingsRelayPresenter();
    }

}
