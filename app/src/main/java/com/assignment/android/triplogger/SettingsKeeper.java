package com.assignment.android.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.assignment.android.triplogger.database.SettingsCursorWrapper;
import com.assignment.android.triplogger.database.SettingsDatabaseHelper;
import com.assignment.android.triplogger.database.SettingsDbSchema;
import com.assignment.android.triplogger.database.SettingsDbSchema.SettingsTable;
import com.assignment.android.triplogger.database.TripDbSchema;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SettingsKeeper {

    public static SettingsKeeper settingsKeeper;

    private Context mcontext;
    private SQLiteDatabase sDatabase;

    public static SettingsKeeper get(Context context){
        if(settingsKeeper==null){
            settingsKeeper= new SettingsKeeper(context);
        }
        return settingsKeeper;
    }

    public SettingsKeeper(Context context){
        mcontext = context.getApplicationContext();
        sDatabase= new SettingsDatabaseHelper(mcontext).getWritableDatabase();

    }


    public void addSettings(Settings settings){
        ContentValues values = getContentValues(settings);
        sDatabase.insert(SettingsDbSchema.SettingsTable.NAME, null, values);
    }

    public void updateSettings(Settings settings){
        String uuidString = settings.getId().toString();
        ContentValues values = getContentValues(settings);

        sDatabase.update(SettingsDbSchema.SettingsTable.NAME, values,
                SettingsDbSchema.SettingsTable.Cols.UUID+
                        " = ?", new String[] { uuidString });
    }


    public void deleteSettings(Settings s){
        String uuidString = s.getId().toString();
        sDatabase.delete(SettingsDbSchema.SettingsTable.NAME, SettingsDbSchema.SettingsTable.Cols.UUID+"=?",new String[] {uuidString});
    }

    public Settings getSettings(){
        Settings s;
        SettingsCursorWrapper cursor = querySettings(null,null);
        try{
            cursor.moveToFirst();
            if(!cursor.isAfterLast()) {
                s=cursor.getSettings();
            }else {
                s=null;
            }
        }finally{
            cursor.close();
        }


       return s;
    }

    private SettingsCursorWrapper querySettings(String whereClause, String[] whereArgs){
        Cursor cursor = sDatabase.query(SettingsDbSchema.SettingsTable.NAME,
                null,whereClause,
                whereArgs,null,null,null);

        return new SettingsCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Settings t){
        ContentValues values = new ContentValues();
        values.put(SettingsTable.Cols.UUID,t.getId().toString());
        values.put(SettingsTable.Cols.NAME,t.getName());
        values.put(SettingsTable.Cols.EMAIL, t.getEmail());
        values.put(SettingsTable.Cols.GENDER, t.getGender());
        values.put(SettingsTable.Cols.COMMENT, t.getComment());
        return values;

    }
}
