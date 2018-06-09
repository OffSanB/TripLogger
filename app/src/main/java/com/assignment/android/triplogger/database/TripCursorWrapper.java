package com.assignment.android.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.assignment.android.triplogger.database.TripDbSchema.TripTable;
import com.assignment.android.triplogger.Trip;

import java.util.Date;
import java.util.UUID;

public class TripCursorWrapper extends CursorWrapper
{
    public TripCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Trip getTrip(){
        String uuidString =
                getString(getColumnIndex(TripTable.Cols.UUID));
        String title =
                getString(getColumnIndex(TripTable.Cols.TITLE));
        long date =
                getLong(getColumnIndex(TripTable.Cols.DATE));
        int type =
                getInt(getColumnIndex(TripTable.Cols.TYPE));
        String dest =
                getString(getColumnIndex(TripTable.Cols.DEST));
        String comment =
                getString(getColumnIndex(TripTable.Cols.COMMENT));
        String gps =
                getString(getColumnIndex(TripTable.Cols.GPS));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setmTitle(title);
        trip.setmDate(new Date(date));
        trip.setTripType(type);
        trip.setmDest(dest);
        trip.setmComment(comment);
        trip.setGPS(gps);

        return trip;
    }

}
