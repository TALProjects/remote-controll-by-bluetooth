package com.jrq.remoterelay.Utils;

/**
 * Created by jrq on 2016-09-18.
 */

public class BluetoothCommand {

    public static String[] MEM_ON = {"@MEM1ON\r","@MEM2ON\r","@MEM3ON\r","@MEM4ON\r","@MEM5ON\r","@MEM6ON\r","@MEM7ON\r","@MEM8ON\r" };
    public static String[] MEM_OFF = {"@MEM1OFF\r","@MEM2OFF\r","@MEM3OFF\r","@MEM4OFF\r","@MEM5OFF\r","@MEM6OFF\r","@MEM7OFF\r","@MEM8OFF\r" };
    public static String[] MEM_AUTO = {"@MEM1AUTO\r","@MEM2AUTO\r","@MEM3AUTO\r","@MEM4AUTO\r","@MEM5AUTO\r","@MEM6AUTO\r","@MEM7AUTO\r","@MEM8AUTO\r" };

    public static String initSetTime = "$SETDC\r";
    public static String changeStatusRelay = "$DCPARAM\r";

    public static String getStatusRelayFromDevice = "@DCPARAM?\r\n";
    public static String getThermostatFromDevice = "@TERMOPARAM?\r\n";

    public static String initDuringTime = "$SETTIMER\r\n";
    public static String initThermostat = "$TERMOPARAM\r";

    public static String setTimeBuilder(String numberOutput, String numberDay, String hourRunning) {
        StringBuilder setTime = new StringBuilder();
        setTime.append("0");
        setTime.append(numberOutput);
        setTime.append(" ");
        setTime.append(numberDay);
        setTime.append(" ");

        String[] splitHourRunning = hourRunning.split(";");
        for(int i=0; i < splitHourRunning.length; i++) {
            if(splitHourRunning[i].equals("--:--")) {
                splitHourRunning[i] = "99 99";
            }
            setTime.append(splitHourRunning[i].replace(":", " "));
            setTime.append(" ");
        }
        setTime.append("\r");

        return setTime.toString();
    }

    public static String setStatusRelayBuilder(String numberOutput, String status) {
        StringBuilder setStatus = new StringBuilder();
        setStatus.append("0");
        status = status.toUpperCase();
        setStatus.append(numberOutput);

        switch (status) {
            case "DOBA":
                status = "03";
                break;
            case "TYDZIEŃ":
                status = "02";
                break;
            case "OFF":
                status = "01";
                break;
        }
        setStatus.append(" ");
        setStatus.append(status);
        setStatus.append("\r");

        return setStatus.toString();
    }

    public static String setDuringTimeBuilder(String numberOuput, String value) {
        StringBuilder setDuring = new StringBuilder();
        setDuring.append("0");
        setDuring.append(numberOuput);
        setDuring.append(" ");

        setDuring.append(value);
        setDuring.append("\r");

        return setDuring.toString();
    }

    public static String setThermostatBuilder(String numberOutput, String status, String Temp, String minTemp,  String hyst) {
        StringBuilder setThermostat = new StringBuilder();
        setThermostat.append("0");
        setThermostat.append(numberOutput);
        setThermostat.append(" ");

        if(status.equals("wyłącz")) {
            status = "01";
        } else if(status.equals("grzanie")) {
            status = "02";
        } else {
            status = "03";
        }

        setThermostat.append(status);
        setThermostat.append(" ");
        int tempLenght = Temp.length();

//        switch (tempLenght) {
//            case 5:
//                setThermostat.append(Temp);
//                break;
//            default:
//                setThermostat.append(Temp.contains("-") ? "-" : "+");
//                setThermostat.append(Temp);
//                break;
//        }
        setThermostat.append(Temp);
        setThermostat.append(" ");
        setThermostat.append(minTemp);
        setThermostat.append(" ");
        setThermostat.append(hyst);

        setThermostat.append("\r");

        return setThermostat.toString();
    }
}