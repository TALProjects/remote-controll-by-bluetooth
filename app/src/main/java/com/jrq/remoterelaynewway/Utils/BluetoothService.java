package com.jrq.remoterelaynewway.Utils;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by jrq on 2016-09-13.
 */

public class BluetoothService extends Service {

    public static final int STATE_NONE = 1;       // we're doing nothing
    public static final int STATE_LISTEN = 2;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 3; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 4;  // now connected to a remote device
    public static final int STATE_STOPED = 5;

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothThread mBluetoothThread;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothDevice mBluetoothDeviceForClock;
    private ArrayList<BluetoothDevice> mBluetoothDeviceList = new ArrayList<BluetoothDevice>();

    private LocalBroadcastManager localBroadcastManager;
    private EventBus eventBus;

    private int statusBluetooth;
    private boolean mStoppingConnection;
    private boolean D = true;
    private String TAG = "RELAY";

    private IBinder mBinder = new MyBinder();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BluetoothService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(eventBus == null || !eventBus.isRegistered(this)) {
            eventBus = EventBus.getDefault();
            eventBus.register(this);
        }

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filterAdapter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        filterAdapter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filterAdapter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filterAdapter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filterAdapter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        this.registerReceiver(StatusBluetoothReceiver, filterAdapter);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiverFromClock, new IntentFilter("com.jrq.remoterelaynewway.Utils.BluetoothService"));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    public void onSearchBluetoothDevices() {

        IntentFilter Filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Filter.addAction(BluetoothDevice.ACTION_UUID);
        Filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        Filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(ActionFoundReceiver, Filter);

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mBluetoothDeviceList.clear();
        mBluetoothAdapter.startDiscovery();
    }

    public void onCancelDiscovery() {
        mBluetoothAdapter.cancelDiscovery();
    }

    public void onConnectBluetoothDevices(int position) {
        connect(mBluetoothDeviceList.get(position));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBluetoothService event){
        if(event.getMessage().equals("START_FIND")) {
            onSearchBluetoothDevices();
        } else if(event.getMessage().equals("DISCONNECT")) {
            disconnect();
        }
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String tAction = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(tAction)) {
                    BluetoothDevice tDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    mBluetoothDeviceList.add(tDevice);
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(tAction)) {
                                unregisterReceiver(ActionFoundReceiver);
                                eventBus.post(new EventAddDevice("FINISHED_FIND", mBluetoothDeviceList));
                }
                }
             catch (Exception ex) {
            }
        }
    };

    private BroadcastReceiver mMessageReceiverFromClock = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("END_CLOCK") != null) {
                mBluetoothDeviceForClock = mBluetoothDevice;
                disconnect();
            }
        }
    };

    private final BroadcastReceiver StatusBluetoothReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                eventBus.post(new EventCurrentDevice("CHANGE_CURRENT_DEVICE", getBluetoothDevice()));
                eventBus.post(new EventBluetoothService("CONNECT_BLUETOOTH"));
                Log.d("RELAY", "ACTION_ACL_CONNECTED");
                setState(STATE_CONNECTED);
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                Log.d("RELAY", "ACTION_ACL_DISCONNECTED_REQUESTED");
                setState(STATE_STOPED);
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                eventBus.post(new EventBluetoothService("DISCONNECT_BLUETOOTH"));
                mBluetoothDevice = null;
                Log.d("RELAY", "ACTION_ACL_DISCONNECTED");
                setState(STATE_STOPED);
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_OFF) {

                    mBluetoothDevice = null;
                    setState(STATE_STOPED);
                } else if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_ON) {

                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDED:
                        break;
                }
            }
        }
    };

    public Set<BluetoothDevice> getPairedDevices() {
        return mBluetoothAdapter.getBondedDevices();
    }

    public ArrayList<BluetoothDevice> getBluetoothDeviceList() {
        return mBluetoothDeviceList;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    private void setState(int status) {
        statusBluetooth = status;
    }

    public synchronized int getState() {
        return statusBluetooth;
    }

    private class BluetoothThread extends Thread {

        private BluetoothSocket mSocket;
        private InputStream mInStream;
        private OutputStream mOutStream;

        public BluetoothThread(BluetoothDevice pDevice) {
            BluetoothSocket tSocket = null;
            try {
                Method m = pDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
                tSocket = (BluetoothSocket) m.invoke(pDevice, 1);
                mSocket = tSocket;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (D)
                    Log.i(TAG, "Connecting to socket");
                mBluetoothAdapter.cancelDiscovery();
                mSocket.connect();
            } catch (IOException e) {
                // If the user didn't cancel the connection then it has failed (timeout)
                if (!mStoppingConnection) {
                    if (D)
                        Log.e(TAG, "Cound not connect to socket");
                    e.printStackTrace();
                    try {
                        mSocket.close();
                    } catch (IOException e1) {
                        if (D)
                            Log.e(TAG, "Cound not close the socket");
                        e1.printStackTrace();
                    }
                    disconnect();
                }
                return;
            }
            setState(STATE_CONNECTED);
            try {
                mInStream = mSocket.getInputStream();
                mOutStream = mSocket.getOutputStream();


            } catch (IOException e) {
                disconnect();
                e.printStackTrace();
                return;
            }

            byte[] tBuffer = new byte[8092];
            byte tTemporaryByte;
            int tBytes;
            String tInput;

            while (true) {
                try {
                    tBytes = 0;
                    while (true) {
                        tTemporaryByte = (byte) mInStream.read();
                        tBuffer[tBytes++] = tTemporaryByte;
                        tInput = new String(tBuffer, "UTF-8").substring(0, tBytes);
\                        if (tInput.contains("OK\r\n") || tInput.contains("ERROR\r\n")) {
                            break;
                        }
                    }
                    if (tBytes > 0) {
                        if (!tInput.equals("0")) {

                            eventBus.post(new EventMessage("RECEIVE_DATA", tInput));

                            tInput = "";
                            tBytes = 0;
                            tBuffer = new byte[tBuffer.length];
                        }
                    }
                } catch (IOException e) {
                    if (!mStoppingConnection) {
                        if (D)
                            Log.e(TAG, "Failed to read");
                        e.printStackTrace();
                        disconnect();
                    }
                    break;
                }
            }
        }

        public void cancel() {
            try {
                if (mInStream != null) {
                    mInStream.close();
                }
                if (mOutStream != null) {
                    mOutStream.close();
                }
                if (mSocket != null) {
                    mSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean write(String pOut) {
            if (mOutStream == null) {
                return false;
            }
            if (D)
                Log.v(TAG, "Write: " + pOut);
            try {
                if (pOut != null) {
                    mOutStream.write(pOut.getBytes("UTF-8"));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean write(String out) {
        BluetoothThread r;
        synchronized (this) {
            if (statusBluetooth != STATE_CONNECTED) {
                return false;
            }
            r = mBluetoothThread;
        }
        return r.write(out);
    }

    public synchronized void connect(BluetoothDevice device) {
        Log.i(TAG, "Connecting to " + device.getName());
        mBluetoothDevice = device;
        mStoppingConnection = false;

        if (mBluetoothThread != null) {
            mBluetoothThread.cancel();
            mBluetoothThread = null;
        }
        setState(STATE_CONNECTING);
        mBluetoothThread = new BluetoothThread(device);
        mBluetoothThread.start();
    }

    public synchronized void disconnect() {
        if (!mStoppingConnection) {
            mStoppingConnection = true;
            if (D)
                Log.i(TAG, "Stop");
            if (mBluetoothThread != null) {
                mBluetoothThread.cancel();
                mBluetoothThread = null;
            }
            setState(STATE_NONE);
        }
    }
}