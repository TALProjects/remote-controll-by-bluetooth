package com.jrq.remoterelaynewway.Utils;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Created by jrq on 2016-09-14.
 */

public class EventCurrentDevice {
    private final String message;
    private final BluetoothDevice bluetoothDevice;

    public EventCurrentDevice(String message, BluetoothDevice bluetoothDevice) {
        this.message = message;
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getMessage() {
        return message;
    }

    public BluetoothDevice getBluetoothDevices() {
        return bluetoothDevice;
    }
}
