package com.example.findmylocation;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!hasGPS()){
            Log.d("GPS","The Hardware does't have GPS.");
        }

        // Enables Always-on
        setAmbientEnabled();
    }

    private boolean hasGPS() {
    return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }
}
