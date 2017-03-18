package com.jrq.remoterelay.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jrq on 2016-09-16.
 */

@Singleton
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "relays.db";
    public static final int DATABASE_VERSION = 2;

    @Inject
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.RelayOutputTable.CREATE);
            db.execSQL(Db.RelayThermostatTable.CREATE);
            db.execSQL(Db.RelayCalenderTable.CREATE);
            db.execSQL(Db.RelayTimeConnect.CREATE);
            db.setTransactionSuccessful();
            db.endTransaction();
        } finally {
            db.beginTransaction();
            db.execSQL(Db.RelayTimeConnect.createValue());
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
