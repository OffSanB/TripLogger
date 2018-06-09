package com.assignment.android.triplogger.database;

import com.assignment.android.triplogger.database.TripDbSchema.TripTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TripDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME="tripDatabase.db";

    public TripDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TripTable.NAME
                + " (" +
        " _id integer primary key autoincrement, "
                +
        TripTable.Cols.UUID + ", " +
                TripTable.Cols.TITLE + ", " +
                TripTable.Cols.DATE + ", " +
                TripTable.Cols.TYPE +", "+
                TripTable.Cols.DEST + ", "+
                TripTable.Cols.COMMENT+", "+
                TripTable.Cols.GPS+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
