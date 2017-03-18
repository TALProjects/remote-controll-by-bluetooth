package com.jrq.remoterelay.Injection.Components;

import com.jrq.remoterelay.Calender.Presenter.CalenderChoosePresenter;
import com.jrq.remoterelay.Calender.Presenter.CalenderPresenter;
import com.jrq.remoterelay.Calender.View.CalenderChooseFragment;
import com.jrq.remoterelay.Calender.View.CalenderFragment;
import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.FinderDevices.View.FinderDevicesFragment;
import com.jrq.remoterelay.Injection.Modules.AppModule;
import com.jrq.remoterelay.MainActivity;
import com.jrq.remoterelay.OutputRelay.Model.OutputRelayModel;
import com.jrq.remoterelay.OutputRelay.Presenter.OutputRelayControlPresenter;
import com.jrq.remoterelay.OutputRelay.View.OutputRelayControlFragment;
import com.jrq.remoterelay.Settings.Presenter.SettingsRelayPresenter;
import com.jrq.remoterelay.Settings.View.SettingsRelayFragment;
import com.jrq.remoterelay.Thermostat.Model.ThermostatModel;
import com.jrq.remoterelay.Thermostat.Presenter.ThermostatPresenter;
import com.jrq.remoterelay.Thermostat.View.ThermostatFragment;

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
