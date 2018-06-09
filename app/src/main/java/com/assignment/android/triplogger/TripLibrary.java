package com.assignment.android.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.assignment.android.triplogger.database.TripCursorWrapper;
import com.assignment.android.triplogger.database.TripDbSchema.TripTable;
import com.assignment.android.triplogger.database.TripDatabaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripLibrary {

    private static TripLibrary sTripLibrary;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TripLibrary get(Context context){
        if(sTripLibrary==null){
            sTripLibrary=new TripLibrary(context);
        }

        return sTripLibrary;
    }

    public TripLibrary(Context context){
        mContext =context.getApplicationContext();
        mDatabase = new TripDatabaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addTrip(Trip t){
        ContentValues values = getContentValues(t);
        mDatabase.insert(TripTable.NAME, null, values);
    }

    public void updateTrip(Trip trip){
        String uuidString = trip.getId().toString();
        ContentValues values = getContentValues(trip);

        mDatabase.update(TripTable.NAME, values,
                TripTable.Cols.UUID+
        " = ?", new String[] { uuidString });
    }


    public void deleteTrip(Trip t){
        String uuidString = t.getId().toString();
        mDatabase.delete(TripTable.NAME,TripTable.Cols.UUID+"=?",new String[] {uuidString});
    }

    public List<Trip> getTrips(){
        List<Trip> trips = new ArrayList<>();

        TripCursorWrapper cursor = queryTrips(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                trips.add(cursor.getTrip());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }

        return trips;
    }


    public Trip getTrip(UUID id){
        TripCursorWrapper cursor = queryTrips(
                TripTable.Cols.UUID+" = ?",
                new String[] { id.toString() }
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTrip();
        }finally{
            cursor.close();
        }
    }

    public File getPhotoFile(Trip trip){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir,trip.getPhotoFilename());
    }
    private TripCursorWrapper queryTrips(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(TripTable.NAME,
                null,whereClause,
                whereArgs,null,null,null);

        return new TripCursorWrapper(cursor);
     }

    private static ContentValues getContentValues(Trip t){
        ContentValues values = new ContentValues();
        values.put(TripTable.Cols.UUID,t.getId().toString());
        values.put(TripTable.Cols.TITLE,t.getTitle());
        values.put(TripTable.Cols.DATE, t.getDate().getTime());
        values.put(TripTable.Cols.TYPE, t.getTripType());
        values.put(TripTable.Cols.DEST, t.getDest());
        values.put(TripTable.Cols.COMMENT, t.getComment());
        values.put(TripTable.Cols.GPS, t.getGPS());

        return values;

    }
}
