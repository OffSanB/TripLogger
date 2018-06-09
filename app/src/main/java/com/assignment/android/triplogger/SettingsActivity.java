package com.assignment.android.triplogger;

import android.support.v4.app.Fragment;

public class SettingsActivity extends SuperActivity {

    @Override
    protected Fragment createFragment(){
        return new SettingsFragment();
    }

}
