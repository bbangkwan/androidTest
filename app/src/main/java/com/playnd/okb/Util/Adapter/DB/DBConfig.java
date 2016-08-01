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
        String sql_user_info = "CREATE TABLE UserInfo ";
        sql_user_info += "(loginUserId Text primary key, password Text, userId Text, userName Text, userHandphone Text, userEmail Text, userStatus Text, userType Text, ";
        sql_user_info += "couponCount INTEGER, myOrderEnd INTEGER, myOrderIng INTEGER, ordercount INTEGER, point INTEGER, regDate DATETIME DEFAULT CURRENT_TIMESTAMP);";
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