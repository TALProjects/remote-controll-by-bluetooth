package com.jrq.remoterelay.Database;

import android.database.Cursor;
import android.util.Log;

import com.jrq.remoterelay.Database.Model.CalenderRelay;
import com.jrq.remoterelay.Database.Model.OutputRelay;
import com.jrq.remoterelay.Database.Model.ThermostatRelay;
import com.jrq.remoterelay.Database.Model.TimeConnectRelay;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jrq on 2016-09-16.
 */

public class DatabaseHelper {
    private final BriteDatabase mDb;

    private final HashMap<Integer, String> mapDays = new HashMap<Integer, String>() {{
        put(0, "on_one_relay");
        put(1, "off_one_relay");
        put(2, "on_two_relay");
        put(3, "off_two_relay");
        put(4, "on_three_relay");
        put(5, "off_three_relay");
        put(6, "on_four_relay");
        put(7, "off_four_relay");
        put(8, "on_five_relay");
        put(9, "off_five_relay");
        put(10, "on_six_relay");
        put(11, "off_six_relay");
        put(12, "on_seven_relay");
        put(13, "off_seven_relay");
        put(14, "on_eight_relay");
        put(15, "off_eight_relay");
    }};

    @Inject
    public DatabaseHelper(DatabaseOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<OutputRelay> setOutputRelays(final Collection<OutputRelay> newOutputRelays) {
        return Observable.create(new Observable.OnSubscribe<OutputRelay>() {
            @Override
            public void call(Subscriber<? super OutputRelay> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    for (OutputRelay outputRelay : newOutputRelays) {
                        long result = mDb.insert(Db.RelayOutputTable.TABLE_NAME,
                                Db.RelayOutputTable.toContentValues(outputRelay));

                        if (result >= 0)  {
                            subscriber.onNext(outputRelay);
                        }
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } catch(Exception e) {

                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<OutputRelay>> getOutputRelays(String macBluetooth) {
        return mDb.createQuery(Db.RelayOutputTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayOutputTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" )
                .mapToList(new Func1<Cursor, OutputRelay>() {
                    @Override
                    public OutputRelay call(Cursor cursor) {
                        return Db.RelayOutputTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<OutputRelay>> checkExistMacBluetooth(String macBluetooth) {
        return mDb.createQuery(Db.RelayOutputTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayOutputTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" + " limit 1")
                .mapToList(new Func1<Cursor, OutputRelay>() {
                    @Override
                    public OutputRelay call(Cursor cursor) {
                        return Db.RelayOutputTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<OutputRelay>> updateOutputRelays(String macBluetooth, String nameRelay, String status) {
        String query;

        if(status.equals("Auto"))  {
            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 0, off_relay = 0, auto_relay = 1 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like "  + "'" + nameRelay + "'";
        } else if(status.equals("Off")) {
            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 0, off_relay = 1, auto_relay = 0 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like "  + "'" + nameRelay + "'";
        } else if(status.equals("On")) {
            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 1, off_relay = 0, auto_relay = 0 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like "  + "'" + nameRelay + "'";
        } else {
            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 1, off_relay = 0, auto_relay = 0 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like "  + "'" + nameRelay + "'";
        }

        return mDb.createQuery(Db.RelayOutputTable.TABLE_NAME, query)
                .mapToList(new Func1<Cursor, OutputRelay>() {
                    @Override
                    public OutputRelay call(Cursor cursor) {
                        return Db.RelayOutputTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<OutputRelay> updateAllOutputRelays(final String macBluetooth, final String receivedMessage) {
        return Observable.create(new Observable.OnSubscribe<OutputRelay>() {
            @Override
            public void call(Subscriber<? super OutputRelay> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                String query;
                String[] splitReceiveMessage = receivedMessage.split("\r\n");
                String mem = splitReceiveMessage[0];
                mem = mem.substring(14, mem.length()).trim();
                char[] memSplit = mem.toCharArray();
                try {
                    for (int i = 1; i < memSplit.length + 1; i++) {
                        char statusChar = memSplit[memSplit.length - i];
                        if (statusChar == '2') {
                            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 0, off_relay = 0, auto_relay = 1 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + Integer.toString(i) + "'";
                        } else if (statusChar == '0') {
                            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 0, off_relay = 1, auto_relay = 0 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + Integer.toString(i) + "'";
                        } else {
                            query = "UPDATE " + Db.RelayOutputTable.TABLE_NAME + " SET on_relay = 1, off_relay = 0, auto_relay = 0 where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + Integer.toString(i) + "'";
                        }

                        mDb.execute(query);
                    }
                    subscriber.onCompleted();
                    transaction.markSuccessful();
                } catch (Exception e) {

                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<ThermostatRelay> setThermotatRelay(final Collection<ThermostatRelay> newThermostatRelays) {
        return Observable.create(new Observable.OnSubscribe<ThermostatRelay>() {
            @Override
            public void call(Subscriber<? super ThermostatRelay> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    for (ThermostatRelay thermostatRelay : newThermostatRelays) {
                        long result = mDb.insert(Db.RelayThermostatTable.TABLE_NAME,
                                Db.RelayThermostatTable.toContentValues(thermostatRelay));

                        if (result >= 0)  {
                            subscriber.onNext(thermostatRelay);
                        }
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } catch(Exception e) {

                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<ThermostatRelay>> getThermostatRelays(String macBluetooth) {
        return mDb.createQuery(Db.RelayThermostatTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayThermostatTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" )
                .mapToList(new Func1<Cursor, ThermostatRelay>() {
                    @Override
                    public ThermostatRelay call(Cursor cursor) {
                        return Db.RelayThermostatTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<ThermostatRelay>> checkExistMacBluetoothThermostat(String macBluetooth) {
        return mDb.createQuery(Db.RelayThermostatTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayThermostatTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" + " limit 1")
                .mapToList(new Func1<Cursor, ThermostatRelay>() {
                    @Override
                    public ThermostatRelay call(Cursor cursor) {
                        return Db.RelayThermostatTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<ThermostatRelay>> updateThermostatRelays(String macBluetooth, String nameRelay, String temp, String minTemp, String hysteresis, String status) {
        String query = "UPDATE " + Db.RelayThermostatTable.TABLE_NAME + " SET temp_relay = " + temp + ", mintemp_relay = " + minTemp + ", hysteresis_relay = " + hysteresis + ", status = " + "'" + status + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like "  + "'" + nameRelay + "'";

        return mDb.createQuery(Db.RelayThermostatTable.TABLE_NAME, query)
                .mapToList(new Func1<Cursor, ThermostatRelay>() {
                    @Override
                    public ThermostatRelay call(Cursor cursor) {
                        return Db.RelayThermostatTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<ThermostatRelay> updateAllThermostatRelays(final String macBluetooth, final String receivedMessage) {
        return Observable.create(new Observable.OnSubscribe<ThermostatRelay>() {
            @Override
            public void call(Subscriber<? super ThermostatRelay> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                String query;

                String relay;
                String statusRelay;
                String temp;
                String minTemp;
                String hysteresis;

                String[] splitReceiveMessage = receivedMessage.split("\r\n");

                try {
                    for (int i = 0; i < splitReceiveMessage.length - 1; i++) {

                        String[] termoParamSplit = splitReceiveMessage[i].split(" ");

                        relay = termoParamSplit[0].substring(1,2);
                        statusRelay = termoParamSplit[1];
                        temp = termoParamSplit[2];
                        minTemp = termoParamSplit[3];
                        hysteresis = termoParamSplit[4];

                        if(statusRelay.equals("01")) {
                            statusRelay = "wyłącz";
                        } else if(statusRelay.equals("02")) {
                            statusRelay = "grzanie";
                        } else {
                            statusRelay = "chłodzenie";
                        }

                        query = "UPDATE " + Db.RelayThermostatTable.TABLE_NAME + " SET temp_relay = " + "'" + temp + "'" +
                                ", mintemp_relay = " + "'" + minTemp + "'" +", hysteresis_relay = " + "'" + hysteresis + "'"
                                + ", status = " + "'" + statusRelay + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + relay + "'";

                        mDb.execute(query);
                    }

                    subscriber.onCompleted();
                    transaction.markSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    transaction.end();
                }
            }
        });
    }


    public Observable<CalenderRelay> setCalenderRelays(final Collection<CalenderRelay> newCalenderRelays) {
        return Observable.create(new Observable.OnSubscribe<CalenderRelay>() {
            @Override
            public void call(Subscriber<? super CalenderRelay> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    for (CalenderRelay calenderRelay : newCalenderRelays) {
                        long result = mDb.insert(Db.RelayCalenderTable.TABLE_NAME,
                                Db.RelayCalenderTable.toContentValues(calenderRelay));

                        if (result >= 0)  {
                            subscriber.onNext(calenderRelay);
                        }
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } catch(Exception e) {
                } finally {
                    transaction.end();
                }
            }
        });
    }



    public Observable<List<CalenderRelay>> getCalenderRelays(String macBluetooth) {
        return mDb.createQuery(Db.RelayCalenderTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayCalenderTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" )
                .mapToList(new Func1<Cursor, CalenderRelay>() {
                    @Override
                    public CalenderRelay call(Cursor cursor) {
                        return Db.RelayCalenderTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<CalenderRelay> getCalenderRelay(String macBluetooth, String nameRelay) {
        return mDb.createQuery(Db.RelayCalenderTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayCalenderTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + nameRelay + "'")
                .mapToOne(new Func1<Cursor, CalenderRelay>() {
                    @Override
                    public CalenderRelay call(Cursor cursor) {
                        return Db.RelayCalenderTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<List<CalenderRelay>> checkExistMacBluetoothCalender(String macBluetooth) {
        return mDb.createQuery(Db.RelayCalenderTable.TABLE_NAME,
                "SELECT * FROM " + Db.RelayCalenderTable.TABLE_NAME + " where mac_address like " + "'" + macBluetooth + "'" + " limit 1")
                .mapToList(new Func1<Cursor, CalenderRelay>() {
                    @Override
                    public CalenderRelay call(Cursor cursor) {
                        return Db.RelayCalenderTable.parseCursor(cursor);
                    }
                });
    }

    public Observable<CalenderRelay> updateAllCalenderRelays(final String macBluetooth, final String receivedMessage) {
        return Observable.create(new Observable.OnSubscribe<CalenderRelay>() {
            @Override
            public void call(Subscriber<? super CalenderRelay> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();

                String[] splitReceiveMessage = receivedMessage.split("\r\n");
                String query = "";

                try {
                    for (int i = 0; i < splitReceiveMessage.length - 1; i++) {
                        String[] params = splitReceiveMessage[i].split(" ");
                        String numberRelay = params[0].substring(1,2);
                        String status = params[1];

                                if (status.equals("01")) {
                                    query = "UPDATE " + Db.RelayCalenderTable.TABLE_NAME + " SET status_relay = " + "'" + "off" + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + numberRelay + "'";
                                } else if (status.equals("03")) {
                                    query = "UPDATE " + Db.RelayCalenderTable.TABLE_NAME + " SET status_relay = " + "'" + "doba" + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + numberRelay + "'";
                                } else if (status.equals("02")) {
                                    query = "UPDATE " + Db.RelayCalenderTable.TABLE_NAME + " SET status_relay = " + "'" + "tydzień" + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + numberRelay + "'";
                                }
                        mDb.execute(query);
                    }

                    subscriber.onCompleted();
                    transaction.markSuccessful();
                } catch (Exception e) {

                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<CalenderRelay> setCalenderStatusRelays(final String macBluetooth, final String numberRelay, final String status) {
        return Observable.create(new Observable.OnSubscribe<CalenderRelay>() {
            @Override
            public void call(Subscriber<? super CalenderRelay> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {

                    String query = "UPDATE " + Db.RelayCalenderTable.TABLE_NAME + " SET status_relay = " + "'" + status + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + numberRelay + "'";
                    mDb.execute(query);

                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } catch(Exception e) {
                    Log.d("pcz", e.toString());
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<CalenderRelay> updateCalenderRelays(final String macBluetooth, final String numberRelay, final String receivedMessage) {
        return Observable.create(new Observable.OnSubscribe<CalenderRelay>() {
            @Override
            public void call(Subscriber<? super CalenderRelay> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    String[] calenderParam = receivedMessage.split("\r\n");
                    Arrays.copyOf(calenderParam, calenderParam.length-1);
                    for (String param : calenderParam) {
                        String[] hours = param.split(" ");
                        if(hours[0].equals("OK")) {
                            break;
                        }
                            String status = "";
                            switch (hours[1]) {
                                case "01":
                                    status = "Monday";
                                    break;
                                case "02":
                                    status = "Tuesday";
                                    break;
                                case "03":
                                    status = "Wednesday";
                                    break;
                                case "04":
                                    status = "Thursday";
                                    break;
                                case "05":
                                    status = "Friday";
                                    break;
                                case "06":
                                    status = "Saturday";
                                    break;
                                case "00":
                                    status = "Sunday";
                                    break;
                                case "07":
                                    status = "Doba";
                                    break;
                            }

                            String query = "";
                            int k = 0;
                            for (int i = 2; i < hours.length - 1; i++) {
                                try {
                                    i++;
                                    if (hours[i - 1].equals("99") && hours[i].equals("99")) {
                                        hours[i - 1] = "--";
                                        hours[i] = "--";
                                    }
                                    query = "UPDATE " + Db.RelayCalenderTable.TABLE_NAME + " SET " + status.toLowerCase() + "_" + mapDays.get(k) +  " = " + "'" + hours[i - 1] + ":" + hours[i] + "'" + " where mac_address like " + "'" + macBluetooth + "'" + " and number_relay like " + "'" + numberRelay + "'";
                                    k++;
                                    mDb.execute(query);
                                } catch (Exception ex) {
                                }
                            }
                        }

                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } catch (Exception e) {
                } finally {
                    transaction.end();
                }
            }
        });
    }


    public Observable<TimeConnectRelay> updateTimeConnectRelays(int time) {
        String query = "UPDATE " + Db.RelayTimeConnect.TABLE_NAME + " SET time = " + time  + " where id = " + 1;

        return mDb.createQuery(Db.RelayTimeConnect.TABLE_NAME, query)
                .mapToOne(new Func1<Cursor, TimeConnectRelay>() {
                    @Override
                    public TimeConnectRelay call(Cursor cursor) {
                        return Db.RelayTimeConnect.parseCursor(cursor);
                    }
                });
    }

    public Observable<TimeConnectRelay> getTimeConnectRelay() {
        return mDb.createQuery(Db.RelayTimeConnect.TABLE_NAME,
                "SELECT * FROM " + Db.RelayTimeConnect.TABLE_NAME)
                .mapToOne(new Func1<Cursor, TimeConnectRelay>() {
                    @Override
                    public TimeConnectRelay call(Cursor cursor) {
                        return Db.RelayTimeConnect.parseCursor(cursor);
                    }
                });
    }
}
