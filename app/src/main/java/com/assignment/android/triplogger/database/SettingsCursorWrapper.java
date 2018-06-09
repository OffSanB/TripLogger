package com.assignment.android.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.assignment.android.triplogger.Settings;
import com.assignment.android.triplogger.database.SettingsDbSchema.SettingsTable;

import java.util.UUID;

public class SettingsCursorWrapper extends CursorWrapper
{
    public SettingsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Settings getSettings(){
        String uuidString =
                getString(getColumnIndex(SettingsTable.Cols.UUID));
        String name =
                getString(getColumnIndex(SettingsTable.Cols.NAME));
        String email =
                getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        int gender =
                getInt(getColumnIndex(SettingsTable.Cols.GENDER));
        String comment =
                getString(getColumnIndex(SettingsTable.Cols.COMMENT));

        Settings settings= new Settings(UUID.fromString(uuidString));
        settings.setName(name);
        settings.setEmail(email);
        settings.setGender(gender);
        settings.setComment(comment);

        return settings;
    }

}
