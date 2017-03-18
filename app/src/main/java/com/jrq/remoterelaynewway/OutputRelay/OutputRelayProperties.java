package com.jrq.remoterelaynewway.OutputRelay;

/**
 * Created by jrq on 2016-09-17.
 */

public class OutputRelayProperties {
    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
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

    public int getNumberRelay() {
        return numberRelay;
    }

    public void setNumberRelay(int numberRelay) {
        this.numberRelay = numberRelay;
    }

    private String nameBluetooth;
    private String macBluetooth;
    private int numberRelay;
    private int auto;
    private int on;
    private int off;


}
