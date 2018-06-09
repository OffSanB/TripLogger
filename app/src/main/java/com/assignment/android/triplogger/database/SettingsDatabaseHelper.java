package com.assignment.android.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.assignment.android.triplogger.database.SettingsDbSchema.SettingsTable;

public class SettingsDatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="settings.db";
    private static final int VERSION = 1;

    public SettingsDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + SettingsTable.NAME
                + " (" +
                " _id integer primary key autoincrement, "
                +
                SettingsTable.Cols.UUID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER +", "+
                SettingsTable.Cols.COMMENT+
                ")"
        );
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){}
}
