package com.assignment.android.triplogger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class RecordActivity extends SuperActivity{
    private int playServicesStatus;

    public static final String EXTRA_FRAGMENT_ID = "com.assignment.android.triplogger.recordFragment_id";
    public static final String EXTRA_TRIP_ID = "com.assignment.android.triplogger.trip_id";
    public static final String EXTRA_LOCATION_SERVICES_ID = "com.assignment.android.triplogger.locationservices_id";





    @Override
    protected Fragment createFragment() {
        int fragmentId = (int) getIntent().getIntExtra(EXTRA_FRAGMENT_ID, 0);
        UUID tripId = (UUID) getIntent().getSerializableExtra(EXTRA_TRIP_ID);

        if (fragmentId == 1) {
            return LogFragment.newInstance(tripId);
        } else if (fragmentId == 2) {
            return TripFragment.newInstance(tripId);

        } else {
            return null;
        }
    }
}

