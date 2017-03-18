package com.jrq.remoterelay.Calender.Presenter;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jrq.remoterelay.Calender.CalenderRelayRecyclerAdapter;
import com.jrq.remoterelay.Calender.MvpCalenderView;
import com.jrq.remoterelay.Calender.View.CalenderChooseFragment;
import com.jrq.remoterelay.Database.DatabaseHelper;
import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.RemoteApplication;
import com.jrq.remoterelay.Utils.BluetoothCommand;
import com.jrq.remoterelay.Utils.BluetoothService;
import com.jrq.remoterelay.Utils.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jrq on 2016-10-05.
 */

public class CalenderPresenter implements CalenderRelayRecyclerAdapter.CalenderRelayRecyclerWriterMessage, CalenderChooseFragment.ChangeDateInCalender {

    private static BluetoothDevice currentBluetoothDevice;
    private static BluetoothService bluetoothService;

    private static FragmentActivity contextView;
    private static MvpCalenderView mvpView;
    private EventBus eventBus;

    private CalenderPresenter calenderPresenter;
    private LastSendingMessage lastSendingMessage;
    private CalenderChooseFragment calenderChooseFragment;

    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    private List<CalenderRelay> calenderRelayList;
    private static int numericSetDc;

    private static Map<String, ArrayList<String>> mTimerProperties = new HashMap<>();
    private ArrayList<String> keysTimerProperties = new ArrayList<>();
    private String statusRelay;
    private String numberStatusRelay;

    public void attachView(MvpCalenderView mvpView, FragmentActivity context) {
        this.contextView = context;
        this.mvpView = mvpView;

        lastSendingMessage = new CalenderPresenter.LastSendingMessage();
        calenderPresenter = this;

        ((RemoteApplication) context.getApplication())
                .getComponent()
                .inject(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage event){
        if(event.getStatus().equals("RECEIVE_DATA")) {
            if(lastSendingMessage.message != null) {
                if (lastSendingMessage.message.equals("dccalenderparam")) {
                    databaseHelper.updateAllCalenderRelays(currentBluetoothDevice.getAddress(), event.getMessage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<CalenderRelay>() {
                                @Override
                                public void onCompleted() {
                                    lastSendingMessage.message = "setdc";
                                    getSettingsFromCalenderRelay();
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(CalenderRelay calenderRelay) {
                                }
                            });
                } else if (lastSendingMessage.message.contains("setdc")) {
                    databaseHelper.updateCalenderRelays(currentBluetoothDevice.getAddress(), Integer.toString(numericSetDc), event.getMessage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<CalenderRelay>() {
                                @Override
                                public void onCompleted() {
                                    if (numericSetDc != 8) {
                                        getSettingsFromCalenderRelay();
                                    } else {
                                        numericSetDc = 1;
                                        getCalenderRelays(currentBluetoothDevice.getAddress(), calenderPresenter);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(CalenderRelay calenderRelay) {
                                }
                            });
                } else if (lastSendingMessage.message.contains("sethourscalender")) {
                    if (event.getMessage().contains("OK")) {
                        databaseHelper.updateCalenderRelays(currentBluetoothDevice.getAddress(), numberStatusRelay, lastSendingMessage.data)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<CalenderRelay>() {

                                    @Override
                                    public void onCompleted() {
                                        if(keysTimerProperties.size() > 0) {
                                            prepareFrameToSetCalender(mTimerProperties.get(keysTimerProperties.get(0)), keysTimerProperties.get(0), lastSendingMessage.nameRelay);
                                            mTimerProperties.remove(keysTimerProperties.get(0));
                                            keysTimerProperties.remove(0);
                                        } else {
                                            if(statusRelay != null && numberStatusRelay != null) {
                                                lastSendingMessage.message = "setstatusrelaydc";
                                                bluetoothService.write("$DCPARAM\r");
                                            } else {
                                                mSubscription = databaseHelper.getCalenderRelays(currentBluetoothDevice.getAddress())
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Subscriber<List<CalenderRelay>>() {
                                                            @Override
                                                            public void onCompleted() {

                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {}

                                                            @Override
                                                            public void onNext(List<CalenderRelay> calenderRelays) {
                                                                calenderRelayList = calenderRelays;
                                                                mvpView.refreshListView(calenderRelayList);
                                                                lastSendingMessage.message = "";
                                                            }
                                                        });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }

                                    @Override
                                    public void onNext(CalenderRelay calenderRelay) {
                                    }
                                });
                    }
                } else if(lastSendingMessage.message.contains("setcalenderhoursdc")) {
                    if (event.getMessage().contains("OK")) {
                        lastSendingMessage.message = "sethourscalender";
                        bluetoothService.write(lastSendingMessage.data);
                    }
                } else if(lastSendingMessage.message.contains("setstatusrelaydc")) {
                    if (event.getMessage().contains("OK")) {
                        lastSendingMessage.message = "setstatusrelays";
                        bluetoothService.write(BluetoothCommand.setStatusRelayBuilder(numberStatusRelay, statusRelay));
                    }
                }  else if(lastSendingMessage.message.contains("setstatusrelays")) {
                    if (event.getMessage().contains("OK")) {
                        databaseHelper.setCalenderStatusRelays(currentBluetoothDevice.getAddress(), numberStatusRelay, statusRelay)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<CalenderRelay>() {
                                    @Override
                                    public void onCompleted() {
                                        mSubscription = databaseHelper.getCalenderRelays(currentBluetoothDevice.getAddress())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Subscriber<List<CalenderRelay>>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {}

                                                    @Override
                                                    public void onNext(List<CalenderRelay> calenderRelays) {
                                                        calenderRelayList = calenderRelays;
                                                        mvpView.refreshListView(calenderRelayList);
                                                        statusRelay = null;
                                                    }
                                                });
                                        lastSendingMessage.message = "";
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }

                                    @Override
                                    public void onNext(CalenderRelay calenderRelay) {
                                    }
                                });
                    }
                }
            }
        }
    }

    private void getSettingsFromCalenderRelay() {
        if(lastSendingMessage.message.equals("dccalenderparam")) {
            lastSendingMessage.message = "setdc" + 1;
            bluetoothService.write("@SETDC" + 1 + "?\r");
        } else {
            numericSetDc++;
            lastSendingMessage.message = "setdc" + numericSetDc;
            bluetoothService.write("@SETDC" + numericSetDc + "?\r");
        }
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

    public void startBluetoothServices() {
        onStartBluetoothServices(contextView);
    }

    public void unBindServices() {
        unBindWithBluetoothService(contextView);
    }

    public void prepareDateForMacBluetooth() {
        insertDateForMacBluetooth(currentBluetoothDevice.getName(), currentBluetoothDevice.getAddress(), 8, this);
    }

    private void insertDateForMacBluetooth(final String nameBluetooth, final String macBluetooth, final int countRelays, final CalenderRelayRecyclerAdapter.CalenderRelayRecyclerWriterMessage presenter) {
        databaseHelper.checkExistMacBluetoothCalender(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CalenderRelay>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<CalenderRelay> calenderRelays) {
                        if(calenderRelays.size() > 0) {
                            getInfoOutputRelayFromDevice();
                        } else {
                            List<CalenderRelay> calenderRelayss = new ArrayList<>();
                            CalenderRelay calenderRelay;
                            for(int i = 1; i < countRelays + 1; i++) {

                                calenderRelay = new CalenderRelay();

                                calenderRelay.setNumberRelay(Integer.toString(i));
                                calenderRelay.setName(nameBluetooth);
                                calenderRelay.setMacBluetooth(macBluetooth);
                                calenderRelay.setStatus("off");

                                calenderRelay.setOnOneDoba("--:--");
                                calenderRelay.setOnTwoDoba("--:--");
                                calenderRelay.setOnThreeDoba("--:--");
                                calenderRelay.setOnFourDoba("--:--");
                                calenderRelay.setOnFiveDoba("--:--");
                                calenderRelay.setOnSixDoba("--:--");
                                calenderRelay.setOnSevenDoba("--:--");
                                calenderRelay.setOnEightDoba("--:--");

                                calenderRelay.setOffOneDoba("--:--");
                                calenderRelay.setOffTwoDoba("--:--");
                                calenderRelay.setOffThreeDoba("--:--");
                                calenderRelay.setOffFourDoba("--:--");
                                calenderRelay.setOffFiveDoba("--:--");
                                calenderRelay.setOffSixDoba("--:--");
                                calenderRelay.setOffSevenDoba("--:--");
                                calenderRelay.setOffEightDoba("--:--");

                                calenderRelay.setOnOneMonday("--:--");
                                calenderRelay.setOnTwoMonday("--:--");
                                calenderRelay.setOnThreeMonday("--:--");
                                calenderRelay.setOnFourMonday("--:--");
                                calenderRelay.setOnFiveMonday("--:--");
                                calenderRelay.setOnSixMonday("--:--");
                                calenderRelay.setOnSevenMonday("--:--");
                                calenderRelay.setOnEightMonday("--:--");

                                calenderRelay.setOffOneMonday("--:--");
                                calenderRelay.setOffTwoMonday("--:--");
                                calenderRelay.setOffThreeMonday("--:--");
                                calenderRelay.setOffFourMonday("--:--");
                                calenderRelay.setOffFiveMonday("--:--");
                                calenderRelay.setOffSixMonday("--:--");
                                calenderRelay.setOffSevenMonday("--:--");
                                calenderRelay.setOffEightMonday("--:--");

                                calenderRelay.setOnOneTuesday("--:--");
                                calenderRelay.setOnTwoTuesday("--:--");
                                calenderRelay.setOnThreeTuesday("--:--");
                                calenderRelay.setOnFourTuesday("--:--");
                                calenderRelay.setOnFiveTuesday("--:--");
                                calenderRelay.setOnSixTuesday("--:--");
                                calenderRelay.setOnSevenTuesday("--:--");
                                calenderRelay.setOnEightTuesday("--:--");

                                calenderRelay.setOffOneTuesday("--:--");
                                calenderRelay.setOffTwoTuesday("--:--");
                                calenderRelay.setOffThreeTuesday("--:--");
                                calenderRelay.setOffFourTuesday("--:--");
                                calenderRelay.setOffFiveTuesday("--:--");
                                calenderRelay.setOffSixTuesday("--:--");
                                calenderRelay.setOffSevenTuesday("--:--");
                                calenderRelay.setOffEightTuesday("--:--");

                                calenderRelay.setOnOneWednesday("--:--");
                                calenderRelay.setOnTwoWednesday("--:--");
                                calenderRelay.setOnThreeWednesday("--:--");
                                calenderRelay.setOnFourWednesday("--:--");
                                calenderRelay.setOnFiveWednesday("--:--");
                                calenderRelay.setOnSixWednesday("--:--");
                                calenderRelay.setOnSevenWednesday("--:--");
                                calenderRelay.setOnEightWednesday("--:--");

                                calenderRelay.setOffOneWednesday("--:--");
                                calenderRelay.setOffTwoWednesday("--:--");
                                calenderRelay.setOffThreeWednesday("--:--");
                                calenderRelay.setOffFourWednesday("--:--");
                                calenderRelay.setOffFiveWednesday("--:--");
                                calenderRelay.setOffSixWednesday("--:--");
                                calenderRelay.setOffSevenWednesday("--:--");
                                calenderRelay.setOffEightWednesday("--:--");

                                calenderRelay.setOnOneThursday("--:--");
                                calenderRelay.setOnTwoThursday("--:--");
                                calenderRelay.setOnThreeThursday("--:--");
                                calenderRelay.setOnFourThursday("--:--");
                                calenderRelay.setOnFiveThursday("--:--");
                                calenderRelay.setOnSixThursday("--:--");
                                calenderRelay.setOnSevenThursday("--:--");
                                calenderRelay.setOnEightThursday("--:--");

                                calenderRelay.setOffOneThursday("--:--");
                                calenderRelay.setOffTwoThursday("--:--");
                                calenderRelay.setOffThreeThursday("--:--");
                                calenderRelay.setOffFourThursday("--:--");
                                calenderRelay.setOffFiveThursday("--:--");
                                calenderRelay.setOffSixThursday("--:--");
                                calenderRelay.setOffSevenThursday("--:--");
                                calenderRelay.setOffEightThursday("--:--");

                                calenderRelay.setOnOneFriday("--:--");
                                calenderRelay.setOnTwoFriday("--:--");
                                calenderRelay.setOnThreeFriday("--:--");
                                calenderRelay.setOnFourFriday("--:--");
                                calenderRelay.setOnFiveFriday("--:--");
                                calenderRelay.setOnSixFriday("--:--");
                                calenderRelay.setOnSevenFriday("--:--");
                                calenderRelay.setOnEightFriday("--:--");

                                calenderRelay.setOffOneFriday("--:--");
                                calenderRelay.setOffTwoFriday("--:--");
                                calenderRelay.setOffThreeFriday("--:--");
                                calenderRelay.setOffFourFriday("--:--");
                                calenderRelay.setOffFiveFriday("--:--");
                                calenderRelay.setOffSixFriday("--:--");
                                calenderRelay.setOffSevenFriday("--:--");
                                calenderRelay.setOffEightFriday("--:--");

                                calenderRelay.setOnOneSaturday("--:--");
                                calenderRelay.setOnTwoSaturday("--:--");
                                calenderRelay.setOnThreeSaturday("--:--");
                                calenderRelay.setOnFourSaturday("--:--");
                                calenderRelay.setOnFiveSaturday("--:--");
                                calenderRelay.setOnSixSaturday("--:--");
                                calenderRelay.setOnSevenSaturday("--:--");
                                calenderRelay.setOnEightSaturday("--:--");

                                calenderRelay.setOffOneSaturday("--:--");
                                calenderRelay.setOffTwoSaturday("--:--");
                                calenderRelay.setOffThreeSaturday("--:--");
                                calenderRelay.setOffFourSaturday("--:--");
                                calenderRelay.setOffFiveSaturday("--:--");
                                calenderRelay.setOffSixSaturday("--:--");
                                calenderRelay.setOffSevenSaturday("--:--");
                                calenderRelay.setOffEightSaturday("--:--");

                                calenderRelay.setOnOneSunday("--:--");
                                calenderRelay.setOnTwoSunday("--:--");
                                calenderRelay.setOnThreeSunday("--:--");
                                calenderRelay.setOnFourSunday("--:--");
                                calenderRelay.setOnFiveSunday("--:--");
                                calenderRelay.setOnSixSunday("--:--");
                                calenderRelay.setOnSevenSunday("--:--");
                                calenderRelay.setOnEightSunday("--:--");

                                calenderRelay.setOffOneSunday("--:--");
                                calenderRelay.setOffTwoSunday("--:--");
                                calenderRelay.setOffThreeSunday("--:--");
                                calenderRelay.setOffFourSunday("--:--");
                                calenderRelay.setOffFiveSunday("--:--");
                                calenderRelay.setOffSixSunday("--:--");
                                calenderRelay.setOffSevenSunday("--:--");
                                calenderRelay.setOffEightSunday("--:--");

                                calenderRelayss.add(calenderRelay);
                            }

                            mSubscription =  databaseHelper.setCalenderRelays(calenderRelayss)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<CalenderRelay>() {
                                        @Override
                                        public void onCompleted() {
                                            getCalenderRelays(currentBluetoothDevice.getAddress(), calenderPresenter);
                                        }

                                        @Override
                                        public void onError(Throwable e) {}

                                        @Override
                                        public void onNext(CalenderRelay outputRelays) {}
                                    });
                        }
                    }
                });
    }

    public void getCalenderRelays(final String macBluetooth, final CalenderRelayRecyclerAdapter.CalenderRelayRecyclerWriterMessage presenter) {
        mSubscription = databaseHelper.getCalenderRelays(macBluetooth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CalenderRelay>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<CalenderRelay> calenderRelays) {
                        calenderRelayList = calenderRelays;
                        mvpView.refreshListView(calenderRelayList);
                    }
                });
    }

    public void getInfoOutputRelayFromDevice() {
        lastSendingMessage.message = "dccalenderparam";
        lastSendingMessage.nameRelay = "all";
        lastSendingMessage.data = "";

        bluetoothService.write("@DCPARAM?\r");
    }

    public void detachView() {
        eventBus.unregister(this);
        unBindWithBluetoothService(contextView);
        contextView = null;
    }

    @Override
    public void showCalenderChooseFragment(View v, int position, String numberRealy) {
        FragmentManager manager = contextView.getSupportFragmentManager();
        calenderChooseFragment = CalenderChooseFragment.newInstance(position, contextView.getApplication(), currentBluetoothDevice.getAddress(), this);
        calenderChooseFragment.show(manager, "calender");
        this.numberStatusRelay = numberRealy;
    }

    @Override
    public void changeDateInCalender(final String numberRelay) {
        keysTimerProperties.addAll(mTimerProperties.keySet());
        if(keysTimerProperties.size() > 0) {
            prepareFrameToSetCalender(mTimerProperties.get(keysTimerProperties.get(0)), keysTimerProperties.get(0), numberRelay);
            mTimerProperties.remove(keysTimerProperties.get(0));
            keysTimerProperties.remove(0);
        } else if(statusRelay != null) {
            lastSendingMessage.message = "setstatusrelaydc";
            bluetoothService.write("$DCPARAM\r");
        }
    }

    private void prepareFrameToSetCalender(ArrayList<String> hourList, String keys, String numberRelay) {
        String status = "";
        StringBuilder messageHour = new StringBuilder();
            for(int i = 0; i < hourList.size(); i++) {
                messageHour.append(hourList.get(i));
                messageHour.append(";");

                switch (keys) {
                    case "Monday":
                        status = "01";
                        break;
                    case "Tuesday":
                        status = "02";
                        break;
                    case "Wednesday":
                        status = "03";
                        break;
                    case "Thursday":
                        status = "04";
                        break;
                    case "Friday":
                        status = "05";
                        break;
                    case "Saturday":
                        status = "06";
                        break;
                    case "Sunday":
                        status = "00";
                        break;
                    case "Doba":
                        status = "07";
                        break;
                }
            }
            try {
                lastSendingMessage.message = "setcalenderhoursdc";
                lastSendingMessage.data = BluetoothCommand.setTimeBuilder(numberRelay, status, messageHour.toString());
                lastSendingMessage.nameRelay = numberRelay;
                bluetoothService.write("$SETDC\r");
            } catch (Exception ex) {

            }
    }

    @Override
    public void changeStatusRelay(String numberRelay, String status) {
        this.statusRelay = status;
        this.numberStatusRelay = numberRelay;
    }

    @Override
    public void addToListForChangeCalender(String numberRelay, ArrayList<String> timerProperties, String week) {
        mTimerProperties.put(week, timerProperties);
    }

    private static class LastSendingMessage {
        private String message;
        private String nameRelay;
        private String data;
    }
}
