package com.jrq.remoterelaynewway.Database.Model;

/**
 * Created by jrq on 2016-09-18.
 */

public class ThermostatRelay {
    public String getHysteresisTemp() {
        return hysteresisTemp;
    }

    public void setHysteresisTemp(String hysteresisTemp) {
        this.hysteresisTemp = hysteresisTemp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberRelay() {
        return numberRelay;
    }

    public void setNumberRelay(int numberRelay) {
        this.numberRelay = numberRelay;
    }

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

    private String nameBluetooth;
    private String macBluetooth;
    private int numberRelay;

    private String status;
    private String minTemp;
    private String temp;
    private String hysteresisTemp;
}
