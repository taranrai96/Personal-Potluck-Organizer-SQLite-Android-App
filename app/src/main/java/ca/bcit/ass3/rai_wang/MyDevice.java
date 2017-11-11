/*
 * Created by Taran Rai & Benjamin Wang on 11/11/17 12:23 AM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 11/11/17 12:23 AM
 */

package ca.bcit.ass3.rai_wang;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        TextView manufacturer = (TextView) findViewById(R.id.manufacturer);
        manufacturer.setText(getResources().getString(R.string.manufacturer_tag) + " " + Build.MANUFACTURER);

        TextView model = (TextView) findViewById(R.id.model);
        model.setText(getResources().getString(R.string.model_tag) + " " + Build.MODEL);

        TextView version = (TextView) findViewById(R.id.version);
        version.setText(getResources().getString(R.string.version_tag) + " " + Build.VERSION.SDK_INT);

        TextView version_rel = (TextView) findViewById(R.id.version_rel);
        version_rel.setText(getResources().getString(R.string.version_release_tag) + " " + Build.VERSION.RELEASE);

        TextView serial_number = (TextView) findViewById(R.id.serial_number);
        serial_number.setText(getResources().getString(R.string.serial_number_tag) +  " " + Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
    }
}
