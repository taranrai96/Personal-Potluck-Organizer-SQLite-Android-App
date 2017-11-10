/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 9:14 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 09/11/17 9:14 PM
 */

package ca.bcit.ass3.rai_wang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMasterEvent extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private Cursor cursor;
    private int selected_event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_master_event);
        helper = new PotluckDbHelper(this);
        selected_event_id = getIntent().getExtras().getInt("event_id");
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT Name, Date, Time FROM EVENT_MASTER WHERE _eventId = " + selected_event_id, null);
        cursor.moveToFirst();
        EditText eventName_et = (EditText) findViewById(R.id.event_name);
        eventName_et.setText(cursor.getString(0));
        EditText eventDate_et = (EditText) findViewById(R.id.event_date);
        eventDate_et.setText(cursor.getString(1));
        EditText eventTime_et = (EditText) findViewById(R.id.event_time);
        eventTime_et.setText(cursor.getString(2));

        Button updateEvent = (Button) findViewById(R.id.event_update_button);
        updateEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    db = helper.getReadableDatabase();

                    EditText eventName_et = (EditText) findViewById(R.id.event_name);
                    String eventName = eventName_et.getText().toString();
                    EditText eventDate_et = (EditText) findViewById(R.id.event_date);
                    String eventDate = eventDate_et.getText().toString();
                    EditText eventTime_et = (EditText) findViewById(R.id.event_time);
                    String eventTime = eventTime_et.getText().toString();

                    String updateEventQuery = "UPDATE EVENT_MASTER SET Name = \'" + eventName + "\', Date = \'" + eventDate + "\', Time = \'" + eventTime + "\' WHERE _eventId = " + selected_event_id;
                    db.execSQL(updateEventQuery);
                    Toast t = Toast.makeText(UpdateMasterEvent.this, getResources().getString(R.string.event_update_success), Toast.LENGTH_SHORT);
                    t.show();
                    finish();
                    startActivity(getIntent());

                } catch (SQLiteException sqlex) {
                    String msg = "[AddMasterEvent] DB unavailable";
                    msg += "\n\n" + sqlex.toString();
                    Toast t = Toast.makeText(UpdateMasterEvent.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
