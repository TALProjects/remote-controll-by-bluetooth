package com.jrq.remoterelaynewway.FinderDevices.Presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jrq.remoterelaynewway.Dialogs.ConnectionDialogFragment;
import com.jrq.remoterelaynewway.FinderDevices.DevicesProperties;
import com.jrq.remoterelaynewway.FinderDevices.MvpFinderView;
import com.jrq.remoterelaynewway.FinderDevices.PairedDevicesRecyclerAdapter;
import com.jrq.remoterelaynewway.R;
import com.jrq.remoterelaynewway.Utils.BluetoothService;
import com.jrq.remoterelaynewway.Utils.EventAddDevice;
import com.jrq.remoterelaynewway.Utils.EventBluetoothService;
import com.jrq.remoterelaynewway.Utils.EventCurrentDevice;
import com.jrq.remoterelaynewway.Utils.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.security.AccessController.getContext;

/**
 * Created by jrq on 2016-09-13.
 */

public class FinderDevicesPresenter implements PairedDevicesRecyclerAdapter.PairedDevicesRecyclerCaller, ConnectionDialogFragment.ConnectionToDevice {

    private EventBus eventBus;
    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static BluetoothDevice currentBluetoothDevice;
    private static BluetoothService bluetoothService;
    private static FragmentActivity contextView;
    private static MvpFinderView mvpView;

    private static final int REQUEST_ENABLE_BT = 3;

    private static ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>();
    private static ArrayList<DevicesProperties> mainDeviceProperties = new ArrayList<>();

    public void attachView(MvpFinderView mvpView, FragmentActivity context) {
        this.contextView = context;
        this.mvpView = mvpView;

        if(eventBus == null || !eventBus.isRegistered(this)) {
            eventBus = EventBus.getDefault();
            eventBus.register(this);
        }

        onStartBluetoothServices(contextView);
    }

    public void detachView() {

        eventBus.unregister(this);
        unBindWithBluetoothService(contextView);
        contextView = null;
    }

    private void onStartBluetoothServices(FragmentActivity context) {
        Intent intent = new Intent(context, BluetoothService.class);
        bindWithBluetoothService(intent, context);
    }

    private boolean bindWithBluetoothService(Intent intent, FragmentActivity context) {
        return context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindWithBluetoothService(Context context) {
            if (bluetoothService != null) {
                context.unbindService(serviceConnection);
            }
    }

    public void setDevicesPropertiesFromSharedPreferences(ArrayList<DevicesProperties> mainDeviceProperties) {
        this.mainDeviceProperties = mainDeviceProperties;
    }

    public boolean checkEnableBluetoothAdapter() {
         return bluetoothAdapter.isEnabled();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothService.MyBinder myBinder = (BluetoothService.MyBinder) service;
            bluetoothService = myBinder.getService();

            currentBluetoothDevice = bluetoothService.getBluetoothDevice();
        }

        public void onServiceDisconnected(ComponentName className) {
            bluetoothService = null;
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAddDevice event){
        if(event.getMessage().equals("ADD_DEVICE")) {
        } else if(event.getMessage().equals("FINISHED_FIND")) {
            mvpView.refreshListView(prepareDevicesProperties(event.getBluetoothDevices()), mainDeviceProperties);
            mvpView.dissableSearchButton(false);
            mvpView.showLoadingProgressBar(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBluetoothService event){
        if(event.getMessage().equals("DISCONNECT_BLUETOOTH")) {;
            for(DevicesProperties devicesProperties : mainDeviceProperties) {
                devicesProperties.setImageConnected(android.R.color.transparent);
            }

            mvpView.refreshListView(new PairedDevicesRecyclerAdapter(mainDeviceProperties, this), mainDeviceProperties);
            currentBluetoothDevice = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCurrentDevice event){
        if(event.getMessage().equals("CHANGE_CURRENT_DEVICE")) {
            currentBluetoothDevice = event.getBluetoothDevices();

            //Refresh RecyclerView

            int index = pairedDevices.indexOf(currentBluetoothDevice);
            DevicesProperties devicesProperties = mainDeviceProperties.get(index);
            devicesProperties.setImageConnected(R.drawable.greenaccept322);

            mainDeviceProperties.set(index, devicesProperties);
            mvpView.refreshListView(new PairedDevicesRecyclerAdapter(mainDeviceProperties, this), mainDeviceProperties);
        }
    }

    public void searchBluetoothDevices() {
        pairedDevices = null;
        mainDeviceProperties.clear();
        mvpView.refreshListView(new PairedDevicesRecyclerAdapter(mainDeviceProperties, this), mainDeviceProperties);
        eventBus.post(new EventBluetoothService("START_FIND"));

        mvpView.showLoadingProgressBar(true);
        mvpView.dissableSearchButton(true);
    }

    private PairedDevicesRecyclerAdapter prepareDevicesProperties(ArrayList<BluetoothDevice> bluetoothDevices) {
        final int[] icons = {R.drawable.circleblue3, R.drawable.circleblue3, R.drawable.circleblue3,
                R.drawable.circleblue3, R.drawable.circleblue3, R.drawable.circleblue3, R.drawable.circleblue3, R.drawable.circleblue3};
        final int[] imageLight = {R.drawable.ledgreen, R.drawable.darkgreenlight};

        Set<BluetoothDevice> pairedBluetoothDevices = bluetoothAdapter.getBondedDevices();
        pairedDevices = new ArrayList<>();
        mainDeviceProperties = new ArrayList<>();

        if (pairedBluetoothDevices.size() > 0) {
            for (BluetoothDevice device : pairedBluetoothDevices) {
                pairedDevices.add(device);
            }
        }

        for (BluetoothDevice pairedDevice : pairedDevices) {
            DevicesProperties deviceProperties = new DevicesProperties();
                if (currentBluetoothDevice != null && pairedDevice.getAddress().equals(currentBluetoothDevice.getAddress())) {
                    deviceProperties.setTitle(pairedDevice.getName());
                    deviceProperties.setThumbnail(icons[0]);
                    deviceProperties.setImageLight(imageLight[0]);
                    deviceProperties.setImageConnected(R.drawable.greenaccept322);
                } else {
                    deviceProperties.setTitle(pairedDevice.getName());
                    deviceProperties.setThumbnail(icons[0]);
                    deviceProperties.setImageConnected(android.R.color.transparent);
                    if (bluetoothDevices != null && bluetoothDevices.contains(pairedDevice)) {
                        deviceProperties.setImageLight(imageLight[0]);
                    } else {
                        deviceProperties.setImageLight(imageLight[1]);
                    }

            }
            mainDeviceProperties.add(deviceProperties);
        }
        return new PairedDevicesRecyclerAdapter(mainDeviceProperties, this);
    }

    @Override
    public void connectWithDevice(View v, int position) {
        FragmentManager manager = contextView.getSupportFragmentManager();
        ConnectionDialogFragment connectionAlertDialog = ConnectionDialogFragment.newInstance(position, this);
        connectionAlertDialog.show(manager, "connect");
    }

    @Override
    public void disconnectWithDevice(View w, int position) {
        FragmentManager manager = contextView.getSupportFragmentManager();
        ConnectionDialogFragment connectionAlertDialog = ConnectionDialogFragment.newInstance(position, this);
        connectionAlertDialog.show(manager, "disconnect");
    }

    public void connectionToDevice(int position) {
        List<BluetoothDevice> list = new ArrayList<>(pairedDevices);
        bluetoothService.connect(list.get(position));
    }

    public void disconnectOfDevice(int position) {
        mainDeviceProperties.get(position).setImageConnected(android.R.color.transparent);
        mvpView.refreshListView(new PairedDevicesRecyclerAdapter(mainDeviceProperties, this), mainDeviceProperties);
        bluetoothService.disconnect();
        currentBluetoothDevice = null;
    }

    public boolean checkConnection() {
        if(bluetoothService == null) {
            return false;
        }
        if(bluetoothService.getBluetoothDevice() == null) {
            return false;
        }

       return bluetoothService.getBluetoothDevice() == null ? false : true;
    }
}
