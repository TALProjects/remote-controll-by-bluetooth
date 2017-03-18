package com.jrq.remoterelaynewway.OutputRelay.Presenter;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.test.AndroidTestRunner;
import android.util.Log;

import com.jrq.remoterelaynewway.Database.DatabaseHelper;
import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.FinderDevices.DevicesProperties;
import com.jrq.remoterelaynewway.FinderDevices.MvpFinderView;
import com.jrq.remoterelaynewway.FinderDevices.PairedDevicesRecyclerAdapter;
import com.jrq.remoterelaynewway.OutputRelay.Model.OutputRelayModel;
import com.jrq.remoterelaynewway.OutputRelay.MvpOutputRelayView;
import com.jrq.remoterelaynewway.OutputRelay.OutputRelayRecyclerAdapter;
import com.jrq.remoterelaynewway.OutputRelay.View.OutputRelayTimerFragment;
import com.jrq.remoterelaynewway.R;
import com.jrq.remoterelaynewway.RemoteApplication;
import com.jrq.remoterelaynewway.Utils.BluetoothCommand;
import com.jrq.remoterelaynewway.Utils.BluetoothService;
import com.jrq.remoterelaynewway.Utils.EventAddDevice;
import com.jrq.remoterelaynewway.Utils.EventBluetoothService;
import com.jrq.remoterelaynewway.Utils.EventCurrentDevice;
import com.jrq.remoterelaynewway.Utils.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-09-15.
 */

public class OutputRelayControlPresenter implements OutputRelayRecyclerAdapter.OutputRelayRecyclerWriterMessage, OutputRelayTimerFragment.ControlTimerFragmentListener {

    private static FragmentActivity contextView;
    private static MvpOutputRelayView mvpView;
    private EventBus eventBus;

    private static BluetoothDevice currentBluetoothDevice;
    private static BluetoothService bluetoothService;

    private Subscription mSubscription;

    private LastSendingMessage lastSendingMessage;

    private OutputRelayControlPresenter outputRelayControlPresenter;

    private List<OutputRelay> outputRelayList;

    @Inject
    DatabaseHelper databaseHelper;

    @Inject
    OutputRelayModel outputRelayModel;

    public void attachView(MvpOutputRelayView mvpView, FragmentActivity context, List<OutputRelay> outputRelayList) {
        this.contextView = context;
        this.mvpView = mvpView;
        this.outputRelayList = outputRelayList;

        lastSendingMessage = new LastSendingMessage();
        outputRelayControlPresenter = this;


        ((RemoteApplication) context.getApplication())
                .getComponent()
                .inject(this);
    }

    public void registerEventBus(boolean enable) {
        if(enable == true) {
            if(eventBus == null || !eventBus.isRegistered(this)) {
                eventBus = EventBus.getDefault();
                eventBus.register(this);
            }
        } else {
            if(eventBus != null) {
                eventBus.unregister(this);
            }
        }
    }

    public void detachView() {
    //    unBindWithBluetoothService(contextView);
    //    contextView = null;
    }

    public void onStartServices() {
        onStartBluetoothServices(contextView);
    }

    public void unBindServices() {
        unBindWithBluetoothService(contextView);
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

        public void onServiceDisconnected(ComponentName className) {}
    };

    public void prepareDateForMacBluetooth() {
        insertDateForMacBluetooth(currentBluetoothDevice.getName(), currentBluetoothDevice.getAddress(), 8, this);
    }

    private void insertDateForMacBluetooth(final String nameBluetooth, final String macBluetooth, final int countRelays, final OutputRelayRecyclerAdapter.OutputRelayRecyclerWriterMessage presenter) {
        databaseHelper.checkExistMacBluetooth(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OutputRelay>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<OutputRelay> outputRelays) {
                        if(outputRelays.size() > 0) {
                            getInfoOutputRelayFromDevice();
                        } else {
                            List<OutputRelay> outputRelayss = new ArrayList<>();
                            OutputRelay outputRelay;
                            for(int i = 1; i < countRelays + 1; i++) {

                                outputRelay = new OutputRelay();
                                outputRelay.setNumberRelay(i);
                                outputRelay.setMacBluetooth(macBluetooth);
                                outputRelay.setNameBluetooth(nameBluetooth);
                                outputRelay.setOn(0);
                                outputRelay.setOff(1);
                                outputRelay.setAuto(0);

                                outputRelayss.add(outputRelay);
                            }

                            mSubscription =  databaseHelper.setOutputRelays(outputRelayss)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<OutputRelay>() {
                                        @Override
                                        public void onCompleted() {
                                            getInfoOutputRelayFromDevice();
                                        }

                                        @Override
                                        public void onError(Throwable e) {}

                                        @Override
                                        public void onNext(OutputRelay outputRelays) {}
                                    });
                        }
                    }
                });
    }

    public void getOutputRelays(final String macBluetooth, final OutputRelayRecyclerAdapter.OutputRelayRecyclerWriterMessage presenter) {
        mSubscription = databaseHelper.getOutputRelays(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OutputRelay>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<OutputRelay> outputRelays) {
                        outputRelayList = outputRelays;
                        mvpView.refreshListView(outputRelayList);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAddDevice event){
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCurrentDevice event){
        if(event.getMessage().equals("CHANGE_CURRENT_DEVICE")) {
            currentBluetoothDevice = event.getBluetoothDevices();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBluetoothService event){
        if(event.getMessage().equals("DISCONNECT_BLUETOOTH")) {
            mvpView.setStartContentView();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(EventMessage event){
        if(event.getStatus().equals("RECEIVE_DATA")) {
            if (lastSendingMessage.message != null) {
                if (lastSendingMessage.message.equals("Auto") || lastSendingMessage.message.equals("Off") || lastSendingMessage.message.equals("On") || lastSendingMessage.message.equals("OnTimerAfter")) {
                    if (event.getMessage().contains("OK")) {
                        databaseHelper.updateOutputRelays(currentBluetoothDevice.getAddress(), lastSendingMessage.nameRelay, lastSendingMessage.message)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<OutputRelay>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }

                                    @Override
                                    public void onNext(List<OutputRelay> outputRelays) {
                                        getOutputRelays(currentBluetoothDevice.getAddress(), outputRelayControlPresenter);
                                        lastSendingMessage.message = "";
                                    }
                                });
                    }
                } else if (lastSendingMessage.message.equals("OnTimer")) {
                    if (event.getMessage().contains("OK")) {
                        lastSendingMessage.message = "OnTimerAfter";
                        bluetoothService.write(lastSendingMessage.data);
                    }
                } else if (lastSendingMessage.message.equals("param")) {
                    databaseHelper.updateAllOutputRelays(currentBluetoothDevice.getAddress(), event.getMessage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<OutputRelay>() {
                                @Override
                                public void onCompleted() {
                                    getOutputRelays(currentBluetoothDevice.getAddress(), outputRelayControlPresenter);
                                    lastSendingMessage.message = "";
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(OutputRelay outputRelays) {
                                }
                            });
                }
            }
        }
    }

    @Override
    public void outputRelayRecyclerWriterMessage(String data, String status, String nameRelay) {
        lastSendingMessage.message = status;
        lastSendingMessage.nameRelay = nameRelay;
        lastSendingMessage.data = data;

        if(status.equals("OnTimer")) {
            bluetoothService.write("$SETTIMER\r");
        } else {
            bluetoothService.write(data);
        }
    }

    @Override
    public void outputRelayRecyclerShowChooseDuring(int position, String status) {
        FragmentManager manager = contextView.getSupportFragmentManager();
        OutputRelayTimerFragment controlFragment = OutputRelayTimerFragment.newInstance(this, position);
        controlFragment.show(manager, "control");
    }

    @Override
    public void onSetTimeDuring(int time, int position) {
        if(time == 0) {
            outputRelayRecyclerWriterMessage(BluetoothCommand.MEM_ON[position], "On", Integer.toString(position + 1));
        } else {
            outputRelayRecyclerWriterMessage(BluetoothCommand.setDuringTimeBuilder(String.valueOf(position + 1), String.valueOf(time)), "OnTimer", Integer.toString(position + 1));
        }
    }

    public void getInfoOutputRelayFromDevice() {
        lastSendingMessage.message = "param";
        lastSendingMessage.nameRelay = "all";
        lastSendingMessage.data = "";

        bluetoothService.write("@PARAM?\r");
    }

    private static class LastSendingMessage {
        private String message;
        private String nameRelay;
        private String data;
    }
}
