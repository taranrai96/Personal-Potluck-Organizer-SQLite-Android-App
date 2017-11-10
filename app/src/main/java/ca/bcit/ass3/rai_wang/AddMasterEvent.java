/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 9:27 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 09/11/17 9:27 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMasterEvent extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_master_event);
        helper = new PotluckDbHelper(this);
        Button submitEvent = (Button) findViewById(R.id.event_submit_button);
        submitEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    db = helper.getReadableDatabase();
                    EditText eventName_et = (EditText) findViewById(R.id.event_name);
                    String eventName = eventName_et.getText().toString();
                    EditText eventDate_et = (EditText) findViewById(R.id.event_date);
                    String eventDate = eventDate_et.getText().toString();
                    EditText eventTime_et = (EditText) findViewById(R.id.event_time);
                    String eventTime = eventTime_et.getText().toString();

                    String insertEventQuery = "INSERT INTO EVENT_MASTER(Name, Date, Time) VALUES (\'" + eventName + "\',\'" + eventDate + "\',\'" + eventTime + "\')";
                    db.execSQL(insertEventQuery);
                    Toast t = Toast.makeText(AddMasterEvent.this, getResources().getString(R.string.event_add_success), Toast.LENGTH_SHORT);
                    t.show();
                    finish();
                    startActivity(getIntent());

                } catch (SQLiteException sqlex) {
                    String msg = "[AddMasterEvent] DB unavailable";
                    msg += "\n\n" + sqlex.toString();
                    Toast t = Toast.makeText(AddMasterEvent.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        Button showMasterButton = (Button) findViewById(R.id.goto_master_event);
        showMasterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToEventDetails = new Intent(AddMasterEvent.this, EventMaster.class);
                startActivity(goToEventDetails);
            }
        });
    }
}
