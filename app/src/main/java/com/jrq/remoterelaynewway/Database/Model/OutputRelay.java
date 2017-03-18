package com.jrq.remoterelaynewway.Database.Model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;

/**
 * Created by jrq on 2016-09-16.
 */

public class OutputRelay{

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public int on;
    public int off;
    public int auto;

    public String getMacBluetooth() {
        return macBluetooth;
    }

    public void setMacBluetooth(String macBluetooth) {
        this.macBluetooth = macBluetooth;
    }

    public String getNameBluetooth() {
        return nameBluetooth;
    }

    public void setNameBluetooth(String nameBluetooth) {
        this.nameBluetooth = nameBluetooth;
    }

    public String nameBluetooth;
    public String macBluetooth;

    public int getNumberRelay() {
        return numberRelay;
    }

    public void setNumberRelay(int numberRelay) {
        this.numberRelay = numberRelay;
    }

    public int numberRelay;
}
