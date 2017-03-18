package com.jrq.remoterelaynewway.Injection.Components;

import android.app.Application;
import android.content.Context;

import com.jrq.remoterelaynewway.Calender.Presenter.CalenderChoosePresenter;
import com.jrq.remoterelaynewway.Calender.Presenter.CalenderPresenter;
import com.jrq.remoterelaynewway.Calender.View.CalenderChooseFragment;
import com.jrq.remoterelaynewway.Calender.View.CalenderFragment;
import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.FinderDevices.View.FinderDevicesFragment;
import com.jrq.remoterelaynewway.Injection.Modules.AppModule;
import com.jrq.remoterelaynewway.MainActivity;
import com.jrq.remoterelaynewway.OutputRelay.Model.OutputRelayModel;
import com.jrq.remoterelaynewway.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelaynewway.OutputRelay.View.OutputRelayControlFragment;
import com.jrq.remoterelaynewway.Settings.Presenter.SettingsRelayPresenter;
import com.jrq.remoterelaynewway.Settings.View.SettingsRelayFragment;
import com.jrq.remoterelaynewway.Thermostat.Model.ThermostatModel;
import com.jrq.remoterelaynewway.Thermostat.Presenter.ThermostatPresenter;
import com.jrq.remoterelaynewway.Thermostat.View.ThermostatFragment;
import com.jrq.remoterelaynewway.Thermostat.WeatherInfo.Model.Main;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jrq on 2016-09-12.
 */
@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(FinderDevicesFragment devicesFragment);
    void inject(OutputRelayControlFragment outputFragment);
    void inject(DatabaseHelper databaseHelper);
    void inject(OutputRelayControlPresenter outputRelayControlPresenter);
    void inject(OutputRelayModel outputRelayModel);
    void inject(ThermostatFragment thermostatFragment);
    void inject(ThermostatPresenter thermostatPresenter);
    void inject(ThermostatModel thermostatModel);
    void inject(CalenderFragment calenderFragment);
    void inject(CalenderPresenter calenderPresenter);
    void inject(CalenderChooseFragment calenderChooseFragment);
    void inject(CalenderChoosePresenter calenderChoosePresenter);
    void inject(SettingsRelayPresenter settingsRelayPresenter);
    void inject(SettingsRelayFragment settingsRelayFragment);
}
