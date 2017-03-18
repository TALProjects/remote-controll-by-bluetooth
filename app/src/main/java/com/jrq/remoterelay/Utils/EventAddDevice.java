package com.jrq.remoterelay.Utils;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Created by jrq on 2016-09-14.
 */

public class EventAddDevice {
    private final String message;
    private final ArrayList<BluetoothDevice> bluetoothDevices;

    public EventAddDevice(String message, ArrayList<BluetoothDevice> bluetoothDevices) {
        this.message = message;
        this.bluetoothDevices = bluetoothDevices;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<BluetoothDevice> getBluetoothDevices() {
        return bluetoothDevices;
    }
}
