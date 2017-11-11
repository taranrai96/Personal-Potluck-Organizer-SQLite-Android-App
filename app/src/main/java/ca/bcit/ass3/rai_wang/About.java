/*
 * Created by Taran Rai & Benjamin Wang on 11/11/17 12:24 AM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 11/11/17 12:24 AM
 */

package ca.bcit.ass3.rai_wang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView app_name = (TextView) findViewById(R.id.app_name);
        app_name.setText(getResources().getString(R.string.application_name_tag) + " " + getResources().getString(R.string.app_name));
        TextView created_by = (TextView) findViewById(R.id.created_by);
        created_by.setText(getResources().getString(R.string.created_by));
        TextView copyright_info = (TextView) findViewById(R.id.copyright_info);
        copyright_info.setText(getResources().getString(R.string.copyright_info));

    }
}
