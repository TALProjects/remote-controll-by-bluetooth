package com.jrq.remoterelay.Thermostat.Presenter;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.Database.Model.ThermostatRelay;
import com.jrq.remoterelay.RemoteApplication;
import com.jrq.remoterelay.Thermostat.Model.ThermostatModel;
import com.jrq.remoterelay.Thermostat.MvpThermostatView;
import com.jrq.remoterelay.Thermostat.ThermostatRecyclerAdapter;
import com.jrq.remoterelay.Utils.BluetoothCommand;
import com.jrq.remoterelay.Utils.BluetoothService;
import com.jrq.remoterelay.Utils.EventBluetoothService;
import com.jrq.remoterelay.Utils.EventMessage;
import com.jrq.remoterelay.Utils.GeoLocationService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-09-18.
 */

public class ThermostatPresenter implements ThermostatRecyclerAdapter.WriterToDevice {

    private static FragmentActivity contextView;
    private static MvpThermostatView mvpView;
    private EventBus eventBus;

    @Inject DatabaseHelper databaseHelper;
    @Inject GeoLocationService geoLocationService;
    @Inject ThermostatModel thermostatModel;

    private static BluetoothDevice currentBluetoothDevice;
    private static BluetoothService bluetoothService;

    private ThermostatPresenter thermostatPresenter;
    private Subscription mSubscription;

    private LastSendingMessage lastSendingMessage;

    public void attachView(MvpThermostatView mvpView, FragmentActivity context) {
        this.contextView = context;
        this.mvpView = mvpView;

        lastSendingMessage = new LastSendingMessage();
        thermostatPresenter = this;

        ((RemoteApplication) context.getApplication())
                .getComponent()
                .inject(this);
    }

    public void startLoadingDate() {
        LocationManager locationManager = (LocationManager) contextView.getSystemService(Context.LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        ConnectivityManager connMgr = (ConnectivityManager) contextView.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(isGPSEnabled || isNetworkEnabled) {
            thermostatModel.startWeatherInfo(connMgr, geoLocationService);
        } else {
            NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
                    mobile != null && mobile.isConnectedOrConnecting();
            refreshWeatherInfo(isConnected, false);
        }
    }

    public void registerEventBus(boolean enable) {
        if(enable == true) {
            if(eventBus == null || !eventBus.isRegistered(this)) {
                eventBus = EventBus.getDefault();
                eventBus.register(this);
            };
        } else {
            if(eventBus != null) {
                eventBus.unregister(this);
            }
        }
    }

    public void startBluetoothServices() {
        onStartBluetoothServices(contextView);
    }

    public void detachView() {
        eventBus.unregister(this);
        unBindWithBluetoothService(contextView);
        contextView = null;
    }

    public void unBindServices() {
        unBindWithBluetoothService(contextView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBluetoothService event){
        if(event.getMessage().equals("DISCONNECT_BLUETOOTH")) {
            mvpView.setStartContentView();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(EventMessage event) {
        if(event.getStatus().equals("RECEIVE_DATA")) {
            if(lastSendingMessage.message != null) {
                if(lastSendingMessage.message.equals("wyłącz") || lastSendingMessage.message.equals("grzanie") || lastSendingMessage.message.equals("chłodzenie")) {
                    if(event.getMessage().contains("OK")) {
                        lastSendingMessage.message = "after" + lastSendingMessage.message;
                        bluetoothService.write(lastSendingMessage.data);
                    }
                } else if(lastSendingMessage.message.equals("afterchłodzenie") || lastSendingMessage.message.equals("aftergrzanie") || lastSendingMessage.message.equals("afterwyłącz")) {
                    if (event.getMessage().contains("OK")) {
                        databaseHelper.updateThermostatRelays(currentBluetoothDevice.getAddress(), lastSendingMessage.nameRelay,
                                lastSendingMessage.temperature, lastSendingMessage.minTemperature, lastSendingMessage.hyseteresis, lastSendingMessage.status)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<ThermostatRelay>>() {
                                    @Override
                                    public void onCompleted() {}

                                    @Override
                                    public void onError(Throwable e) {}

                                    @Override
                                    public void onNext(List<ThermostatRelay> thermostatRelays) {
                                        getThermostatRelays(currentBluetoothDevice.getAddress(), thermostatPresenter);
                                        lastSendingMessage.message = "";
                                    }
                                });
                    }
                } else if(lastSendingMessage.message.equals("termoparam")) {
                    databaseHelper.updateAllThermostatRelays(currentBluetoothDevice.getAddress(), event.getMessage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ThermostatRelay>() {
                                @Override
                                public void onCompleted() {
                                    getThermostatRelays(currentBluetoothDevice.getAddress(), thermostatPresenter);
                                    lastSendingMessage.message = "";
                                }

                                @Override
                                public void onError(Throwable e) {}

                                @Override
                                public void onNext(ThermostatRelay outputRelays) {
                                }
                            });
                   }
               }
            }
        }

    public void getThermostatRelays(final String macBluetooth, final ThermostatRecyclerAdapter.WriterToDevice presenter) {
        mSubscription = databaseHelper.getThermostatRelays(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ThermostatRelay>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<ThermostatRelay> thermostatRelays) {
                        mvpView.refreshListView(thermostatRelays);
                    }
                });
    }

    private void onStartBluetoothServices(FragmentActivity context) {
        Intent intent = new Intent(context, BluetoothService.class);
        bindWithBluetoothService(intent, context);
    }

    private boolean bindWithBluetoothService(Intent intent, FragmentActivity context) {
        boolean bind = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        return bind;
    }

    private void unBindWithBluetoothService(Context context) {
        if (bluetoothService != null) {
            contextView.unbindService(serviceConnection);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothService.MyBinder myBinder = (BluetoothService.MyBinder) service;
            bluetoothService = myBinder.getService();
            currentBluetoothDevice = bluetoothService.getBluetoothDevice();
            prepareDateForMacBluetooth();
        }

        public void onServiceDisconnected(ComponentName className) {
        }
    };

    public void prepareDateForMacBluetooth() {
        insertDateForMacBluetooth(currentBluetoothDevice.getName(), currentBluetoothDevice.getAddress(), 8, this);
    }

    private void insertDateForMacBluetooth(final String nameBluetooth, final String macBluetooth, final int countRelays, final ThermostatRecyclerAdapter.WriterToDevice presenter) {
        databaseHelper.checkExistMacBluetoothThermostat(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ThermostatRelay>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<ThermostatRelay> outputRelays) {
                        if(outputRelays.size() > 0) {
                      //      getThermostatRelays(macBluetooth, presenter);
                            getInfoThermostatRelayFromDevice();
                        } else {
                            List<ThermostatRelay> thermostatRelayss = new ArrayList<>();
                            ThermostatRelay thermostatRelay;
                            for(int i = 1; i < countRelays + 1; i++) {

                                thermostatRelay = new ThermostatRelay();
                                thermostatRelay.setNumberRelay(i);
                                thermostatRelay.setMacBluetooth(macBluetooth);
                                thermostatRelay.setNameBluetooth(nameBluetooth);
                                thermostatRelay.setTemp("0");
                                thermostatRelay.setMinTemp("0");
                                thermostatRelay.setHysteresisTemp("0");
                                thermostatRelay.setStatus("wyłącz");

                                thermostatRelayss.add(thermostatRelay);
                            }

                            mSubscription =  databaseHelper.setThermotatRelay(thermostatRelayss)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<ThermostatRelay>() {
                                        @Override
                                        public void onCompleted() {
                                        //    getThermostatRelays(macBluetooth, presenter);
                                            getInfoThermostatRelayFromDevice();
                                        }

                                        @Override
                                        public void onError(Throwable e) {}

                                        @Override
                                        public void onNext(ThermostatRelay outputRelays) {}
                                    });
                        }
                    }
                });
    }

    public void getInfoThermostatRelayFromDevice() {
        lastSendingMessage.message = "termoparam";
        lastSendingMessage.nameRelay = "all";
        lastSendingMessage.data = "";

        bluetoothService.write("@TERMOPARAM?\r");
    }

    @Override
    public void writeToDevice(String position, String status, String temperature, String minTemperature, String hysteresis) {
        lastSendingMessage.message = status;
        lastSendingMessage.nameRelay = position;

        lastSendingMessage.position = position;
        lastSendingMessage.status = status;
        lastSendingMessage.temperature = temperature;
        lastSendingMessage.minTemperature = minTemperature;
        lastSendingMessage.hyseteresis = hysteresis;

        lastSendingMessage.data = BluetoothCommand.setThermostatBuilder(position, status, temperature, minTemperature, hysteresis);
        bluetoothService.write(BluetoothCommand.initThermostat);
    }

    public void refreshWeatherInfo(boolean enables, boolean gps) {
        mvpView.refreshWeatherInfo(enables, gps);
    }

    public void setWeatherInfo(String city, String maxTemp, String minTemp, String currentTemp, String icon) {
        int idImageWeather = contextView.getResources().getIdentifier("im" + icon, "drawable", contextView.getPackageName()); //pobranie id rysunku
        mvpView.setWeatherInfo(city, maxTemp, minTemp, currentTemp, idImageWeather);
    }

    private static class LastSendingMessage {
        private String message;
        private String nameRelay;
        private String data;

        private String position;
        private String status;
        private String temperature;
        private String minTemperature;
        private String hyseteresis;
    }
}
