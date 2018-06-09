package com.assignment.android.triplogger;

import android.support.v4.app.Fragment;

public class MainActivity extends SuperActivity {
    protected Fragment createFragment(){
        return new TriplistFragment();
    }
}
