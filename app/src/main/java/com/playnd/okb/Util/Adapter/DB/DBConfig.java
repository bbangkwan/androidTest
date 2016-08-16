package com.playnd.okb.Util.Adapter.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ByeongKwan on 2016-08-01.
 */
public class DBConfig extends SQLiteOpenHelper {

    public DBConfig(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_user_info = "CREATE TABLE bankHistory ";
        sql_user_info += "(seq INTEGER PRIMARY KEY AUTOINCREMENT, hkd_fee INTEGER, korea_fee INTEGER, latitude Text, longtitude Text, address Text, memo Text, useType Text, regDate DATETIME DEFAULT CURRENT_TIMESTAMP, updDate DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(sql_user_info);
        //String sql_push_history = "CREATE TABLE PushHitory (idx INTEGER primary key autoincrement, pushTitle Text, pushContent Text, pushType Text);";
        //db.execSQL(sql_push_history);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dic");
        onCreate(db);
    }
}