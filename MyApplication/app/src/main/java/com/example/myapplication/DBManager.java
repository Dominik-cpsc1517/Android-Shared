package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper
{
    static final String TAG = "DBManager";

    static final String DB_NAME = "chatter.db";
    static final int DB_VERSION = 1;
    static final String LIST_TITLE = "ListName";
    static final String LIST_ID = BaseColumns._ID;
    static final String CONTENT = "content";
    static final String CONTENT_ID = BaseColumns._ID;
    static final String COMPLETED_FLAG = "data";


    public DBManager(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Called when db does not exists
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String sql = "create table " + LIST_TITLE + " (" + LIST_ID + " int primary key, "
                + CONTENT + " text, " + CONTENT_ID + " text, " + COMPLETED_FLAG + " text)";
        Log.d(TAG, "sql = " + sql);

        database.execSQL(sql);
    }

    //Called when version number changes
    @Override
    public void onUpgrade(SQLiteDatabase  database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + LIST_TITLE);
        Log.d(TAG, "in onUpgraded");

        onCreate(database);
    }
}