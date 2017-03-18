package com.jrq.remoterelaynewway.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.jrq.remoterelaynewway.Database.Model.CalenderRelay;
import com.jrq.remoterelaynewway.Database.Model.OutputRelay;
import com.jrq.remoterelaynewway.Database.Model.ThermostatRelay;
import com.jrq.remoterelaynewway.Database.Model.TimeConnectRelay;


/**
 * Created by jrq on 2016-09-16.
 */

public class Db {

    public Db() { }

    public static class RelayOutputTable {
        public static final String TABLE_NAME = "relay_output";

        public static final String COLUMN_ON = "on_relay";
        public static final String COLUMN_OFF = "off_relay";
        public static final String COLUMN_AUTO = "auto_relay";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "mac_address";

        public static final String COLUMN_NAME_RELAY = "number_relay";

        public static final String CREATE =
           "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_RELAY + " INTEGER NOT NULL, " +
                    COLUMN_ADDRESS + " TEXT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ON + " INTEGER NOT NULL, " +
                    COLUMN_AUTO + " INTEGER NOT NULL, " +
                    COLUMN_OFF + " INTEGER NOT NULL ); ";


        public static ContentValues toContentValues(OutputRelay outputRelay) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_RELAY, outputRelay.getNumberRelay());
            values.put(COLUMN_ADDRESS, outputRelay.getMacBluetooth());
            values.put(COLUMN_NAME, outputRelay.getNameBluetooth());
            values.put(COLUMN_ON, outputRelay.getOn());
            values.put(COLUMN_OFF, outputRelay.getOff());
            values.put(COLUMN_AUTO, outputRelay.getAuto());
            return values;
        }

        public static OutputRelay parseCursor(Cursor cursor) {
            OutputRelay outputRelay = new OutputRelay();
            outputRelay.setNumberRelay(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_RELAY)));
            outputRelay.setMacBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
            outputRelay.setNameBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            outputRelay.setAuto(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AUTO)));
            outputRelay.setOn(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ON)));
            outputRelay.setOff(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_OFF)));
            return outputRelay;
        }
    }

    public static class RelayThermostatTable {
        public static final String TABLE_NAME = "relay_thermostat";

        public static final String COLUMN_TEMP = "temp_relay";
        public static final String COLUMN_MINTEMP = "mintemp_relay";
        public static final String COLUMN_HYSTERESIS = "hysteresis_relay";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "mac_address";
        public static final String COLUMN_STATUS = "status";

        public static final String COLUMN_NAME_RELAY = "number_relay";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_RELAY + " INTEGER NOT NULL, " +
                        COLUMN_STATUS + " TEXT NOT NULL, " +
                        COLUMN_ADDRESS + " TEXT NOT NULL, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_TEMP + " TEXT NOT NULL, " +
                        COLUMN_MINTEMP + " TEXT NOT NULL, " +
                        COLUMN_HYSTERESIS + " TEXT NOT NULL ); ";


        public static ContentValues toContentValues(ThermostatRelay thermostatRelay) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_RELAY, thermostatRelay.getNumberRelay());
            values.put(COLUMN_ADDRESS, thermostatRelay.getMacBluetooth());
            values.put(COLUMN_NAME, thermostatRelay.getNameBluetooth());
            values.put(COLUMN_TEMP, thermostatRelay.getTemp());
            values.put(COLUMN_MINTEMP, thermostatRelay.getMinTemp());
            values.put(COLUMN_HYSTERESIS, thermostatRelay.getHysteresisTemp());
            values.put(COLUMN_STATUS, thermostatRelay.getStatus());
            return values;
        }

        public static ThermostatRelay parseCursor(Cursor cursor) {
            ThermostatRelay thermostatRelay = new ThermostatRelay();
            thermostatRelay.setNumberRelay(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_RELAY)));
            thermostatRelay.setMacBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
            thermostatRelay.setNameBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            thermostatRelay.setTemp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEMP)));
            thermostatRelay.setMinTemp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MINTEMP)));
            thermostatRelay.setHysteresisTemp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HYSTERESIS)));
            thermostatRelay.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));
            return thermostatRelay;
        }
    }

    public static class RelayCalenderTable {
        public static final String TABLE_NAME = "relay_calender";

        public static final String COLUMN_DOBA_ON_ONE = "doba_on_one_relay";
        public static final String COLUMN_DOBA_ON_TWO = "doba_on_two_relay";
        public static final String COLUMN_DOBA_ON_THREE = "doba_on_three_relay";
        public static final String COLUMN_DOBA_ON_FOUR = "doba_on_four_relay";
        public static final String COLUMN_DOBA_ON_FIVE = "doba_on_five_relay";
        public static final String COLUMN_DOBA_ON_SIX = "doba_on_six_relay";
        public static final String COLUMN_DOBA_ON_SEVEN = "doba_on_seven_relay";
        public static final String COLUMN_DOBA_ON_EIGHT = "doba_on_eight_relay";

        public static final String COLUMN_MONDAY_ON_ONE = "monday_on_one_relay";
        public static final String COLUMN_MONDAY_ON_TWO = "monday_on_two_relay";
        public static final String COLUMN_MONDAY_ON_THREE = "monday_on_three_relay";
        public static final String COLUMN_MONDAY_ON_FOUR = "monday_on_four_relay";
        public static final String COLUMN_MONDAY_ON_FIVE = "monday_on_five_relay";
        public static final String COLUMN_MONDAY_ON_SIX = "monday_on_six_relay";
        public static final String COLUMN_MONDAY_ON_SEVEN = "monday_on_seven_relay";
        public static final String COLUMN_MONDAY_ON_EIGHT = "monday_on_eight_relay";

        public static final String COLUMN_TUESDAY_ON_ONE = "tuesday_on_one_relay";
        public static final String COLUMN_TUESDAY_ON_TWO = "tuesday_on_two_relay";
        public static final String COLUMN_TUESDAY_ON_THREE = "tuesday_on_three_relay";
        public static final String COLUMN_TUESDAY_ON_FOUR = "tuesday_on_four_relay";
        public static final String COLUMN_TUESDAY_ON_FIVE = "tuesday_on_five_relay";
        public static final String COLUMN_TUESDAY_ON_SIX = "tuesday_on_six_relay";
        public static final String COLUMN_TUESDAY_ON_SEVEN = "tuesday_on_seven_relay";
        public static final String COLUMN_TUESDAY_ON_EIGHT = "tuesday_on_eight_relay";

        public static final String COLUMN_WEDNESDAY_ON_ONE = "wednesday_on_one_relay";
        public static final String COLUMN_WEDNESDAY_ON_TWO = "wednesday_on_two_relay";
        public static final String COLUMN_WEDNESDAY_ON_THREE = "wednesday_on_three_relay";
        public static final String COLUMN_WEDNESDAY_ON_FOUR = "wednesday_on_four_relay";
        public static final String COLUMN_WEDNESDAY_ON_FIVE = "wednesday_on_five_relay";
        public static final String COLUMN_WEDNESDAY_ON_SIX = "wednesday_on_six_relay";
        public static final String COLUMN_WEDNESDAY_ON_SEVEN = "wednesday_on_seven_relay";
        public static final String COLUMN_WEDNESDAY_ON_EIGHT = "wednesday_on_eight_relay";

        public static final String COLUMN_THURSDAY_ON_ONE = "thursday_on_one_relay";
        public static final String COLUMN_THURSDAY_ON_TWO = "thursday_on_two_relay";
        public static final String COLUMN_THURSDAY_ON_THREE = "thursday_on_three_relay";
        public static final String COLUMN_THURSDAY_ON_FOUR = "thursday_on_four_relay";
        public static final String COLUMN_THURSDAY_ON_FIVE = "thursday_on_five_relay";
        public static final String COLUMN_THURSDAY_ON_SIX = "thursday_on_six_relay";
        public static final String COLUMN_THURSDAY_ON_SEVEN = "thursday_on_seven_relay";
        public static final String COLUMN_THURSDAY_ON_EIGHT = "thursday_on_eight_relay";

        public static final String COLUMN_FRIDAY_ON_ONE = "friday_on_one_relay";
        public static final String COLUMN_FRIDAY_ON_TWO = "friday_on_two_relay";
        public static final String COLUMN_FRIDAY_ON_THREE = "friday_on_three_relay";
        public static final String COLUMN_FRIDAY_ON_FOUR = "friday_on_four_relay";
        public static final String COLUMN_FRIDAY_ON_FIVE = "friday_on_five_relay";
        public static final String COLUMN_FRIDAY_ON_SIX = "friday_on_six_relay";
        public static final String COLUMN_FRIDAY_ON_SEVEN = "friday_on_seven_relay";
        public static final String COLUMN_FRIDAY_ON_EIGHT = "friday_on_eight_relay";

        public static final String COLUMN_SATURDAY_ON_ONE = "saturday_on_one_relay";
        public static final String COLUMN_SATURDAY_ON_TWO = "saturday_on_two_relay";
        public static final String COLUMN_SATURDAY_ON_THREE = "saturday_on_three_relay";
        public static final String COLUMN_SATURDAY_ON_FOUR = "saturday_on_four_relay";
        public static final String COLUMN_SATURDAY_ON_FIVE = "saturday_on_five_relay";
        public static final String COLUMN_SATURDAY_ON_SIX = "saturday_on_six_relay";
        public static final String COLUMN_SATURDAY_ON_SEVEN = "saturday_on_seven_relay";
        public static final String COLUMN_SATURDAY_ON_EIGHT = "saturday_on_eight_relay";

        public static final String COLUMN_SUNDAY_ON_ONE = "sunday_on_one_relay";
        public static final String COLUMN_SUNDAY_ON_TWO = "sunday_on_two_relay";
        public static final String COLUMN_SUNDAY_ON_THREE = "sunday_on_three_relay";
        public static final String COLUMN_SUNDAY_ON_FOUR = "sunday_on_four_relay";
        public static final String COLUMN_SUNDAY_ON_FIVE = "sunday_on_five_relay";
        public static final String COLUMN_SUNDAY_ON_SIX = "sunday_on_six_relay";
        public static final String COLUMN_SUNDAY_ON_SEVEN = "sunday_on_seven_relay";
        public static final String COLUMN_SUNDAY_ON_EIGHT = "sunday_on_eight_relay";

        public static final String COLUMN_DOBA_OFF_ONE = "doba_off_one_relay";
        public static final String COLUMN_DOBA_OFF_TWO = "doba_off_two_relay";
        public static final String COLUMN_DOBA_OFF_THREE = "doba_off_three_relay";
        public static final String COLUMN_DOBA_OFF_FOUR = "doba_off_four_relay";
        public static final String COLUMN_DOBA_OFF_FIVE = "doba_off_five_relay";
        public static final String COLUMN_DOBA_OFF_SIX = "doba_off_six_relay";
        public static final String COLUMN_DOBA_OFF_SEVEN = "doba_off_seven_relay";
        public static final String COLUMN_DOBA_OFF_EIGHT = "doba_off_eight_relay";

        public static final String COLUMN_MONDAY_OFF_ONE = "monday_off_one_relay";
        public static final String COLUMN_MONDAY_OFF_TWO = "monday_off_two_relay";
        public static final String COLUMN_MONDAY_OFF_THREE = "monday_off_three_relay";
        public static final String COLUMN_MONDAY_OFF_FOUR = "monday_off_four_relay";
        public static final String COLUMN_MONDAY_OFF_FIVE = "monday_off_five_relay";
        public static final String COLUMN_MONDAY_OFF_SIX = "monday_off_six_relay";
        public static final String COLUMN_MONDAY_OFF_SEVEN = "monday_off_seven_relay";
        public static final String COLUMN_MONDAY_OFF_EIGHT = "monday_off_eight_relay";

        public static final String COLUMN_TUESDAY_OFF_ONE = "tuesday_off_one_relay";
        public static final String COLUMN_TUESDAY_OFF_TWO = "tuesday_off_two_relay";
        public static final String COLUMN_TUESDAY_OFF_THREE = "tuesday_off_three_relay";
        public static final String COLUMN_TUESDAY_OFF_FOUR = "tuesday_off_four_relay";
        public static final String COLUMN_TUESDAY_OFF_FIVE = "tuesday_off_five_relay";
        public static final String COLUMN_TUESDAY_OFF_SIX = "tuesday_off_six_relay";
        public static final String COLUMN_TUESDAY_OFF_SEVEN = "tuesday_off_seven_relay";
        public static final String COLUMN_TUESDAY_OFF_EIGHT = "tuesday_off_eight_relay";

        public static final String COLUMN_WEDNESDAY_OFF_ONE = "wednesday_off_one_relay";
        public static final String COLUMN_WEDNESDAY_OFF_TWO = "wednesday_off_two_relay";
        public static final String COLUMN_WEDNESDAY_OFF_THREE = "wednesday_off_three_relay";
        public static final String COLUMN_WEDNESDAY_OFF_FOUR = "wednesday_off_four_relay";
        public static final String COLUMN_WEDNESDAY_OFF_FIVE = "wednesday_off_five_relay";
        public static final String COLUMN_WEDNESDAY_OFF_SIX = "wednesday_off_six_relay";
        public static final String COLUMN_WEDNESDAY_OFF_SEVEN = "wednesday_off_seven_relay";
        public static final String COLUMN_WEDNESDAY_OFF_EIGHT = "wednesday_off_eight_relay";

        public static final String COLUMN_THURSDAY_OFF_ONE = "thursday_off_one_relay";
        public static final String COLUMN_THURSDAY_OFF_TWO = "thursday_off_two_relay";
        public static final String COLUMN_THURSDAY_OFF_THREE = "thursday_off_three_relay";
        public static final String COLUMN_THURSDAY_OFF_FOUR = "thursday_off_four_relay";
        public static final String COLUMN_THURSDAY_OFF_FIVE = "thursday_off_five_relay";
        public static final String COLUMN_THURSDAY_OFF_SIX = "thursday_off_six_relay";
        public static final String COLUMN_THURSDAY_OFF_SEVEN = "thursday_off_seven_relay";
        public static final String COLUMN_THURSDAY_OFF_EIGHT = "thursday_off_eight_relay";

        public static final String COLUMN_FRIDAY_OFF_ONE = "friday_off_one_relay";
        public static final String COLUMN_FRIDAY_OFF_TWO = "friday_off_two_relay";
        public static final String COLUMN_FRIDAY_OFF_THREE = "friday_off_three_relay";
        public static final String COLUMN_FRIDAY_OFF_FOUR = "friday_off_four_relay";
        public static final String COLUMN_FRIDAY_OFF_FIVE = "friday_off_five_relay";
        public static final String COLUMN_FRIDAY_OFF_SIX = "friday_off_six_relay";
        public static final String COLUMN_FRIDAY_OFF_SEVEN = "friday_off_seven_relay";
        public static final String COLUMN_FRIDAY_OFF_EIGHT = "friday_off_eight_relay";

        public static final String COLUMN_SATURDAY_OFF_ONE = "saturday_off_one_relay";
        public static final String COLUMN_SATURDAY_OFF_TWO = "saturday_off_two_relay";
        public static final String COLUMN_SATURDAY_OFF_THREE = "saturday_off_three_relay";
        public static final String COLUMN_SATURDAY_OFF_FOUR = "saturday_off_four_relay";
        public static final String COLUMN_SATURDAY_OFF_FIVE = "saturday_off_five_relay";
        public static final String COLUMN_SATURDAY_OFF_SIX = "saturday_off_six_relay";
        public static final String COLUMN_SATURDAY_OFF_SEVEN = "saturday_off_seven_relay";
        public static final String COLUMN_SATURDAY_OFF_EIGHT = "saturday_off_eight_relay";

        public static final String COLUMN_SUNDAY_OFF_ONE = "sunday_off_one_relay";
        public static final String COLUMN_SUNDAY_OFF_TWO = "sunday_off_two_relay";
        public static final String COLUMN_SUNDAY_OFF_THREE = "sunday_off_three_relay";
        public static final String COLUMN_SUNDAY_OFF_FOUR = "sunday_off_four_relay";
        public static final String COLUMN_SUNDAY_OFF_FIVE = "sunday_off_five_relay";
        public static final String COLUMN_SUNDAY_OFF_SIX = "sunday_off_six_relay";
        public static final String COLUMN_SUNDAY_OFF_SEVEN = "sunday_off_seven_relay";
        public static final String COLUMN_SUNDAY_OFF_EIGHT = "sunday_off_eight_relay";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "mac_address";
        public static final String COLUMN_NAME_RELAY = "number_relay";
        public static final String COLUMN_STATUS = "status_relay";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_RELAY + " INTEGER NOT NULL, " +
                        COLUMN_ADDRESS + " TEXT NOT NULL, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_STATUS + " TEXT NOT NULL, " +

                        COLUMN_DOBA_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_DOBA_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_MONDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_TUESDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_WEDNESDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_WEDNESDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_THURSDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_THURSDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_FRIDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_FRIDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_SATURDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_SATURDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_SUNDAY_ON_ONE + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_TWO + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_THREE + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_FOUR + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_FIVE + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_SIX + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_SUNDAY_ON_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_DOBA_OFF_ONE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_TWO + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_THREE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_FOUR + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_FIVE + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_SIX + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_DOBA_OFF_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_MONDAY_OFF_ONE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_TWO + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_THREE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_FOUR + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_FIVE + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_SIX + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_MONDAY_OFF_EIGHT + " TEXT NOT NULL, " +

                        COLUMN_TUESDAY_OFF_ONE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_TWO + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_THREE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_FOUR + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_FIVE + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_SIX + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_SEVEN + " TEXT NOT NULL, " +
                        COLUMN_TUESDAY_OFF_EIGHT + " TEXT NOT NULL, " +

        COLUMN_WEDNESDAY_OFF_ONE + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_TWO + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_THREE + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_FOUR + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_FIVE + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_SIX + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_SEVEN + " TEXT NOT NULL, " +
        COLUMN_WEDNESDAY_OFF_EIGHT + " TEXT NOT NULL, " +

        COLUMN_THURSDAY_OFF_ONE + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_TWO + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_THREE + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_FOUR + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_FIVE + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_SIX + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_SEVEN + " TEXT NOT NULL, " +
        COLUMN_THURSDAY_OFF_EIGHT + " TEXT NOT NULL, " +

        COLUMN_FRIDAY_OFF_ONE + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_TWO + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_THREE + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_FOUR + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_FIVE + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_SIX + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_SEVEN + " TEXT NOT NULL, " +
        COLUMN_FRIDAY_OFF_EIGHT + " TEXT NOT NULL, " +

        COLUMN_SATURDAY_OFF_ONE + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_TWO + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_THREE + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_FOUR + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_FIVE + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_SIX + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_SEVEN + " TEXT NOT NULL, " +
        COLUMN_SATURDAY_OFF_EIGHT + " TEXT NOT NULL, " +

        COLUMN_SUNDAY_OFF_ONE + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_TWO + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_THREE + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_FOUR + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_FIVE + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_SIX + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_SEVEN + " TEXT NOT NULL, " +
        COLUMN_SUNDAY_OFF_EIGHT + " TEXT NOT NULL); ";


        public static ContentValues toContentValues(CalenderRelay calenderRelay) {
            ContentValues values = new ContentValues();

            values.put(COLUMN_NAME_RELAY, calenderRelay.getNumberRelay());
            values.put(COLUMN_NAME, calenderRelay.getName());
            values.put(COLUMN_ADDRESS, calenderRelay.getMacBluetooth());
            values.put(COLUMN_STATUS, calenderRelay.getStatus());

            values.put(COLUMN_DOBA_ON_ONE, calenderRelay.getOnOneDoba());
            values.put(COLUMN_DOBA_ON_TWO, calenderRelay.getOnTwoDoba());
            values.put(COLUMN_DOBA_ON_THREE, calenderRelay.getOnThreeDoba());
            values.put(COLUMN_DOBA_ON_FOUR, calenderRelay.getOnFourDoba());
            values.put(COLUMN_DOBA_ON_FIVE, calenderRelay.getOnFiveDoba());
            values.put(COLUMN_DOBA_ON_SIX, calenderRelay.getOnSixDoba());
            values.put(COLUMN_DOBA_ON_SEVEN, calenderRelay.getOnSevenDoba());
            values.put(COLUMN_DOBA_ON_EIGHT, calenderRelay.getOnEightDoba());

            values.put(COLUMN_MONDAY_ON_ONE, calenderRelay.getOnOneMonday());
            values.put(COLUMN_MONDAY_ON_TWO, calenderRelay.getOnTwoMonday());
            values.put(COLUMN_MONDAY_ON_THREE, calenderRelay.getOnThreeMonday());
            values.put(COLUMN_MONDAY_ON_FOUR, calenderRelay.getOnFourMonday());
            values.put(COLUMN_MONDAY_ON_FIVE, calenderRelay.getOnFiveMonday());
            values.put(COLUMN_MONDAY_ON_SIX, calenderRelay.getOnSixMonday());
            values.put(COLUMN_MONDAY_ON_SEVEN, calenderRelay.getOnSevenMonday());
            values.put(COLUMN_MONDAY_ON_EIGHT, calenderRelay.getOnEightMonday());

            values.put(COLUMN_TUESDAY_ON_ONE, calenderRelay.getOnOneTuesday());
            values.put(COLUMN_TUESDAY_ON_TWO, calenderRelay.getOnTwoTuesday());
            values.put(COLUMN_TUESDAY_ON_THREE, calenderRelay.getOnThreeTuesday());
            values.put(COLUMN_TUESDAY_ON_FOUR, calenderRelay.getOnFourTuesday());
            values.put(COLUMN_TUESDAY_ON_FIVE, calenderRelay.getOnFiveTuesday());
            values.put(COLUMN_TUESDAY_ON_SIX, calenderRelay.getOnSixTuesday());
            values.put(COLUMN_TUESDAY_ON_SEVEN, calenderRelay.getOnSevenTuesday());
            values.put(COLUMN_TUESDAY_ON_EIGHT, calenderRelay.getOnEightTuesday());

            values.put(COLUMN_WEDNESDAY_ON_ONE, calenderRelay.getOnOneWednesday());
            values.put(COLUMN_WEDNESDAY_ON_TWO, calenderRelay.getOnTwoWednesday());
            values.put(COLUMN_WEDNESDAY_ON_THREE, calenderRelay.getOnThreeWednesday());
            values.put(COLUMN_WEDNESDAY_ON_FOUR, calenderRelay.getOnFourWednesday());
            values.put(COLUMN_WEDNESDAY_ON_FIVE, calenderRelay.getOnFiveWednesday());
            values.put(COLUMN_WEDNESDAY_ON_SIX, calenderRelay.getOnSixWednesday());
            values.put(COLUMN_WEDNESDAY_ON_SEVEN, calenderRelay.getOnSevenWednesday());
            values.put(COLUMN_WEDNESDAY_ON_EIGHT, calenderRelay.getOnEightWednesday());

            values.put(COLUMN_THURSDAY_ON_ONE, calenderRelay.getOnOneThursday());
            values.put(COLUMN_THURSDAY_ON_TWO, calenderRelay.getOnTwoThursday());
            values.put(COLUMN_THURSDAY_ON_THREE, calenderRelay.getOnThreeThursday());
            values.put(COLUMN_THURSDAY_ON_FOUR, calenderRelay.getOnFourThursday());
            values.put(COLUMN_THURSDAY_ON_FIVE, calenderRelay.getOnFiveThursday());
            values.put(COLUMN_THURSDAY_ON_SIX, calenderRelay.getOnSixThursday());
            values.put(COLUMN_THURSDAY_ON_SEVEN, calenderRelay.getOnSevenThursday());
            values.put(COLUMN_THURSDAY_ON_EIGHT, calenderRelay.getOnEightThursday());

            values.put(COLUMN_FRIDAY_ON_ONE, calenderRelay.getOnOneFriday());
            values.put(COLUMN_FRIDAY_ON_TWO, calenderRelay.getOnTwoFriday());
            values.put(COLUMN_FRIDAY_ON_THREE, calenderRelay.getOnThreeFriday());
            values.put(COLUMN_FRIDAY_ON_FOUR, calenderRelay.getOnFourFriday());
            values.put(COLUMN_FRIDAY_ON_FIVE, calenderRelay.getOnFiveFriday());
            values.put(COLUMN_FRIDAY_ON_SIX, calenderRelay.getOnSixFriday());
            values.put(COLUMN_FRIDAY_ON_SEVEN, calenderRelay.getOnSevenFriday());
            values.put(COLUMN_FRIDAY_ON_EIGHT, calenderRelay.getOnEightFriday());

            values.put(COLUMN_SATURDAY_ON_ONE, calenderRelay.getOnOneSaturday());
            values.put(COLUMN_SATURDAY_ON_TWO, calenderRelay.getOnTwoSaturday());
            values.put(COLUMN_SATURDAY_ON_THREE, calenderRelay.getOnThreeSaturday());
            values.put(COLUMN_SATURDAY_ON_FOUR, calenderRelay.getOnFourSaturday());
            values.put(COLUMN_SATURDAY_ON_FIVE, calenderRelay.getOnFiveSaturday());
            values.put(COLUMN_SATURDAY_ON_SIX, calenderRelay.getOnSixSaturday());
            values.put(COLUMN_SATURDAY_ON_SEVEN, calenderRelay.getOnSevenSaturday());
            values.put(COLUMN_SATURDAY_ON_EIGHT, calenderRelay.getOnEightSaturday());

            values.put(COLUMN_SUNDAY_ON_ONE, calenderRelay.getOnOneSunday());
            values.put(COLUMN_SUNDAY_ON_TWO, calenderRelay.getOnTwoSunday());
            values.put(COLUMN_SUNDAY_ON_THREE, calenderRelay.getOnThreeSunday());
            values.put(COLUMN_SUNDAY_ON_FOUR, calenderRelay.getOnFourSunday());
            values.put(COLUMN_SUNDAY_ON_FIVE, calenderRelay.getOnFiveSunday());
            values.put(COLUMN_SUNDAY_ON_SIX, calenderRelay.getOnSixSunday());
            values.put(COLUMN_SUNDAY_ON_SEVEN, calenderRelay.getOnSevenSunday());
            values.put(COLUMN_SUNDAY_ON_EIGHT, calenderRelay.getOnEightSunday());

            values.put(COLUMN_DOBA_OFF_ONE, calenderRelay.getOffOneDoba());
            values.put(COLUMN_DOBA_OFF_TWO, calenderRelay.getOffTwoDoba());
            values.put(COLUMN_DOBA_OFF_THREE, calenderRelay.getOffThreeDoba());
            values.put(COLUMN_DOBA_OFF_FOUR, calenderRelay.getOffFourDoba());
            values.put(COLUMN_DOBA_OFF_FIVE, calenderRelay.getOffFiveDoba());
            values.put(COLUMN_DOBA_OFF_SIX, calenderRelay.getOffSixDoba());
            values.put(COLUMN_DOBA_OFF_SEVEN, calenderRelay.getOffSevenDoba());
            values.put(COLUMN_DOBA_OFF_EIGHT, calenderRelay.getOffEightDoba());

            values.put(COLUMN_MONDAY_OFF_ONE, calenderRelay.getOffOneMonday());
            values.put(COLUMN_MONDAY_OFF_TWO, calenderRelay.getOffTwoMonday());
            values.put(COLUMN_MONDAY_OFF_THREE, calenderRelay.getOffThreeMonday());
            values.put(COLUMN_MONDAY_OFF_FOUR, calenderRelay.getOffFourMonday());
            values.put(COLUMN_MONDAY_OFF_FIVE, calenderRelay.getOffFiveMonday());
            values.put(COLUMN_MONDAY_OFF_SIX, calenderRelay.getOffSixMonday());
            values.put(COLUMN_MONDAY_OFF_SEVEN, calenderRelay.getOffSevenMonday());
            values.put(COLUMN_MONDAY_OFF_EIGHT, calenderRelay.getOffEightMonday());

            values.put(COLUMN_TUESDAY_OFF_ONE, calenderRelay.getOffOneTuesday());
            values.put(COLUMN_TUESDAY_OFF_TWO, calenderRelay.getOffTwoTuesday());
            values.put(COLUMN_TUESDAY_OFF_THREE, calenderRelay.getOffThreeTuesday());
            values.put(COLUMN_TUESDAY_OFF_FOUR, calenderRelay.getOffFourTuesday());
            values.put(COLUMN_TUESDAY_OFF_FIVE, calenderRelay.getOffFiveTuesday());
            values.put(COLUMN_TUESDAY_OFF_SIX, calenderRelay.getOffSixTuesday());
            values.put(COLUMN_TUESDAY_OFF_SEVEN, calenderRelay.getOffSevenTuesday());
            values.put(COLUMN_TUESDAY_OFF_EIGHT, calenderRelay.getOffEightTuesday());

            values.put(COLUMN_WEDNESDAY_OFF_ONE, calenderRelay.getOffOneWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_TWO, calenderRelay.getOffTwoWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_THREE, calenderRelay.getOffThreeWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_FOUR, calenderRelay.getOffFourWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_FIVE, calenderRelay.getOffFiveWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_SIX, calenderRelay.getOffSixWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_SEVEN, calenderRelay.getOffSevenWednesday());
            values.put(COLUMN_WEDNESDAY_OFF_EIGHT, calenderRelay.getOffEightWednesday());

            values.put(COLUMN_THURSDAY_OFF_ONE, calenderRelay.getOffOneThursday());
            values.put(COLUMN_THURSDAY_OFF_TWO, calenderRelay.getOffTwoThursday());
            values.put(COLUMN_THURSDAY_OFF_THREE, calenderRelay.getOffThreeThursday());
            values.put(COLUMN_THURSDAY_OFF_FOUR, calenderRelay.getOffFourThursday());
            values.put(COLUMN_THURSDAY_OFF_FIVE, calenderRelay.getOffFiveThursday());
            values.put(COLUMN_THURSDAY_OFF_SIX, calenderRelay.getOffSixThursday());
            values.put(COLUMN_THURSDAY_OFF_SEVEN, calenderRelay.getOffSevenThursday());
            values.put(COLUMN_THURSDAY_OFF_EIGHT, calenderRelay.getOffEightThursday());

            values.put(COLUMN_FRIDAY_OFF_ONE, calenderRelay.getOffOneFriday());
            values.put(COLUMN_FRIDAY_OFF_TWO, calenderRelay.getOffTwoFriday());
            values.put(COLUMN_FRIDAY_OFF_THREE, calenderRelay.getOffThreeFriday());
            values.put(COLUMN_FRIDAY_OFF_FOUR, calenderRelay.getOffFourFriday());
            values.put(COLUMN_FRIDAY_OFF_FIVE, calenderRelay.getOffFiveFriday());
            values.put(COLUMN_FRIDAY_OFF_SIX, calenderRelay.getOffSixFriday());
            values.put(COLUMN_FRIDAY_OFF_SEVEN, calenderRelay.getOffSevenFriday());
            values.put(COLUMN_FRIDAY_OFF_EIGHT, calenderRelay.getOffEightFriday());

            values.put(COLUMN_SATURDAY_OFF_ONE, calenderRelay.getOffOneSaturday());
            values.put(COLUMN_SATURDAY_OFF_TWO, calenderRelay.getOffTwoSaturday());
            values.put(COLUMN_SATURDAY_OFF_THREE, calenderRelay.getOffThreeSaturday());
            values.put(COLUMN_SATURDAY_OFF_FOUR, calenderRelay.getOffFourSaturday());
            values.put(COLUMN_SATURDAY_OFF_FIVE, calenderRelay.getOffFiveSaturday());
            values.put(COLUMN_SATURDAY_OFF_SIX, calenderRelay.getOffSixSaturday());
            values.put(COLUMN_SATURDAY_OFF_SEVEN, calenderRelay.getOffSevenSaturday());
            values.put(COLUMN_SATURDAY_OFF_EIGHT, calenderRelay.getOffEightSaturday());

            values.put(COLUMN_SUNDAY_OFF_ONE, calenderRelay.getOffOneSunday());
            values.put(COLUMN_SUNDAY_OFF_TWO, calenderRelay.getOffTwoSunday());
            values.put(COLUMN_SUNDAY_OFF_THREE, calenderRelay.getOffThreeSunday());
            values.put(COLUMN_SUNDAY_OFF_FOUR, calenderRelay.getOffFourSunday());
            values.put(COLUMN_SUNDAY_OFF_FIVE, calenderRelay.getOffFiveSunday());
            values.put(COLUMN_SUNDAY_OFF_SIX, calenderRelay.getOffSixSunday());
            values.put(COLUMN_SUNDAY_OFF_SEVEN, calenderRelay.getOffSevenSunday());
            values.put(COLUMN_SUNDAY_OFF_EIGHT, calenderRelay.getOffEightSunday());

            return values;
        }

        public static CalenderRelay parseCursor(Cursor cursor) {
            CalenderRelay calenderRelay = new CalenderRelay();
            calenderRelay.setNumberRelay(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RELAY)));
            calenderRelay.setMacBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
            calenderRelay.setNameBluetooth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            calenderRelay.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));

			calenderRelay.setOnOneDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_ONE)));
            calenderRelay.setOnTwoDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_TWO)));
            calenderRelay.setOnThreeDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_THREE)));
            calenderRelay.setOnFourDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_FOUR)));
            calenderRelay.setOnFiveDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_FIVE)));
            calenderRelay.setOnSixDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_SIX)));
            calenderRelay.setOnSevenDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_SEVEN)));
            calenderRelay.setOnEightDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_ON_EIGHT)));

            calenderRelay.setOnOneMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_ONE)));
            calenderRelay.setOnTwoMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_TWO)));
            calenderRelay.setOnThreeMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_THREE)));
            calenderRelay.setOnFourMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_FOUR)));
            calenderRelay.setOnFiveMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_FIVE)));
            calenderRelay.setOnSixMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_SIX)));
            calenderRelay.setOnSevenMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_SEVEN)));
            calenderRelay.setOnEightMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_ON_EIGHT)));

         	calenderRelay.setOnOneTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_ONE)));
            calenderRelay.setOnTwoTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_TWO)));
            calenderRelay.setOnThreeTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_THREE)));
            calenderRelay.setOnFourTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_FOUR)));
            calenderRelay.setOnFiveTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_FIVE)));
            calenderRelay.setOnSixTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_SIX)));
            calenderRelay.setOnSevenTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_SEVEN)));
            calenderRelay.setOnEightTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_ON_EIGHT)));

          	calenderRelay.setOnOneWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_ONE)));
            calenderRelay.setOnTwoWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_TWO)));
            calenderRelay.setOnThreeWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_THREE)));
            calenderRelay.setOnFourWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_FOUR)));
            calenderRelay.setOnFiveWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_FIVE)));
            calenderRelay.setOnSixWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_SIX)));
            calenderRelay.setOnSevenWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_SEVEN)));
            calenderRelay.setOnEightWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_ON_EIGHT)));
			
			calenderRelay.setOnOneThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_ONE)));
            calenderRelay.setOnTwoThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_TWO)));
            calenderRelay.setOnThreeThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_THREE)));
            calenderRelay.setOnFourThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_FOUR)));
            calenderRelay.setOnFiveThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_FIVE)));
            calenderRelay.setOnSixThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_SIX)));
            calenderRelay.setOnSevenThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_SEVEN)));
            calenderRelay.setOnEightThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_ON_EIGHT)));

        	calenderRelay.setOnOneFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_ONE)));
            calenderRelay.setOnTwoFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_TWO)));
            calenderRelay.setOnThreeFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_THREE)));
            calenderRelay.setOnFourFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_FOUR)));
            calenderRelay.setOnFiveFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_FIVE)));
            calenderRelay.setOnSixFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_SIX)));
            calenderRelay.setOnSevenFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_SEVEN)));
            calenderRelay.setOnEightFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_ON_EIGHT)));
			
			calenderRelay.setOnOneSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_ONE)));
            calenderRelay.setOnTwoSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_TWO)));
            calenderRelay.setOnThreeSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_THREE)));
            calenderRelay.setOnFourSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_FOUR)));
            calenderRelay.setOnFiveSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_FIVE)));
            calenderRelay.setOnSixSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_SIX)));
            calenderRelay.setOnSevenSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_SEVEN)));
            calenderRelay.setOnEightSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_ON_EIGHT)));

    		calenderRelay.setOnOneSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_ONE)));
            calenderRelay.setOnTwoSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_TWO)));
            calenderRelay.setOnThreeSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_THREE)));
            calenderRelay.setOnFourSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_FOUR)));
            calenderRelay.setOnFiveSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_FIVE)));
            calenderRelay.setOnSixSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_SIX)));
            calenderRelay.setOnSevenSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_SEVEN)));
            calenderRelay.setOnEightSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_ON_EIGHT)));

            calenderRelay.setOffOneDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_ONE)));
            calenderRelay.setOffTwoDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_TWO)));
            calenderRelay.setOffThreeDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_THREE)));
            calenderRelay.setOffFourDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_FOUR)));
            calenderRelay.setOffFiveDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_FIVE)));
            calenderRelay.setOffSixDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_SIX)));
            calenderRelay.setOffSevenDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_SEVEN)));
            calenderRelay.setOffEightDoba(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOBA_OFF_EIGHT)));

            calenderRelay.setOffOneMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_ONE)));
            calenderRelay.setOffTwoMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_TWO)));
            calenderRelay.setOffThreeMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_THREE)));
            calenderRelay.setOffFourMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_FOUR)));
            calenderRelay.setOffFiveMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_FIVE)));
            calenderRelay.setOffSixMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_SIX)));
            calenderRelay.setOffSevenMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_SEVEN)));
            calenderRelay.setOffEightMonday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONDAY_OFF_EIGHT)));

         	calenderRelay.setOffOneTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_ONE)));
            calenderRelay.setOffTwoTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_TWO)));
            calenderRelay.setOffThreeTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_THREE)));
            calenderRelay.setOffFourTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_FOUR)));
            calenderRelay.setOffFiveTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_FIVE)));
            calenderRelay.setOffSixTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_SIX)));
            calenderRelay.setOffSevenTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_SEVEN)));
            calenderRelay.setOffEightTuesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TUESDAY_OFF_EIGHT)));

          	calenderRelay.setOffOneWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_ONE)));
            calenderRelay.setOffTwoWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_TWO)));
            calenderRelay.setOffThreeWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_THREE)));
            calenderRelay.setOffFourWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_FOUR)));
            calenderRelay.setOffFiveWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_FIVE)));
            calenderRelay.setOffSixWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_SIX)));
            calenderRelay.setOffSevenWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_SEVEN)));
            calenderRelay.setOffEightWednesday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEDNESDAY_OFF_EIGHT)));
			
			calenderRelay.setOffOneThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_ONE)));
            calenderRelay.setOffTwoThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_TWO)));
            calenderRelay.setOffThreeThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_THREE)));
            calenderRelay.setOffFourThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_FOUR)));
            calenderRelay.setOffFiveThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_FIVE)));
            calenderRelay.setOffSixThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_SIX)));
            calenderRelay.setOffSevenThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_SEVEN)));
            calenderRelay.setOffEightThursday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THURSDAY_OFF_EIGHT)));

        	calenderRelay.setOffOneFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_ONE)));
            calenderRelay.setOffTwoFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_TWO)));
            calenderRelay.setOffThreeFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_THREE)));
            calenderRelay.setOffFourFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_FOUR)));
            calenderRelay.setOffFiveFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_FIVE)));
            calenderRelay.setOffSixFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_SIX)));
            calenderRelay.setOffSevenFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_SEVEN)));
            calenderRelay.setOffEightFriday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRIDAY_OFF_EIGHT)));
			
			calenderRelay.setOffOneSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_ONE)));
            calenderRelay.setOffTwoSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_TWO)));
            calenderRelay.setOffThreeSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_THREE)));
            calenderRelay.setOffFourSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_FOUR)));
            calenderRelay.setOffFiveSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_FIVE)));
            calenderRelay.setOffSixSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_SIX)));
            calenderRelay.setOffSevenSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_SEVEN)));
            calenderRelay.setOffEightSaturday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SATURDAY_OFF_EIGHT)));

    		calenderRelay.setOffOneSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_ONE)));
            calenderRelay.setOffTwoSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_TWO)));
            calenderRelay.setOffThreeSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_THREE)));
            calenderRelay.setOffFourSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_FOUR)));
            calenderRelay.setOffFiveSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_FIVE)));
            calenderRelay.setOffSixSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_SIX)));
            calenderRelay.setOffSevenSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_SEVEN)));
            calenderRelay.setOffEightSunday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUNDAY_OFF_EIGHT)));

            return calenderRelay;
        }
    }

    public static class RelayTimeConnect {
        public static final String TABLE_NAME = "relay_timeconnect";
        public static final String COLUMN_TIME = "time";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                       "id INTEGER PRIMARY KEY, " +
                        COLUMN_TIME + " INTEGER DEFAULT 120); ";

        public static TimeConnectRelay parseCursor(Cursor cursor) {
            TimeConnectRelay timeConnectRelay = new TimeConnectRelay();
            timeConnectRelay.setTime(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
            return timeConnectRelay;
        }

        public static String createValue() {
           return  "INSERT INTO " + TABLE_NAME + " (id " + ", " + COLUMN_TIME + ")" + " VALUES (1, 120)";
        }
    }

}

