/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 5:12 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 09/11/17 12:23 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDetailEvent extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private int eventIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_event);
        helper = new PotluckDbHelper(this);
        eventIdNumber = getIntent().getExtras().getInt("event_id");
        Button detailsSubmitButton = (Button) findViewById(R.id.details_submit_button);
        detailsSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    db = helper.getReadableDatabase();
                    EditText itemName_et = (EditText) findViewById(R.id.details_item);
                    String itemName = itemName_et.getText().toString();
                    EditText itemQuantity_et = (EditText) findViewById(R.id.details_quantity);
                    String itemQuantity = itemQuantity_et.getText().toString();
                    EditText itemUnit_et = (EditText) findViewById(R.id.details_unit);
                    String itemUnit = itemUnit_et.getText().toString();
                    String insertQuery = "INSERT INTO EVENT_DETAIL(ItemName, ItemUnit, ItemQuantity, _eventId) VALUES (\'" + itemName + "\',\'" + itemUnit + "\',\'" + itemQuantity + "\',\'" + eventIdNumber + "\')";
                    db.execSQL(insertQuery);
                    Toast t = Toast.makeText(AddDetailEvent.this, getResources().getString(R.string.detail_add_success), Toast.LENGTH_SHORT);
                    t.show();

                } catch (SQLiteException sqlex) {
                    String msg = "[AddMasterEvent] DB unavailable";
                    msg += "\n\n" + sqlex.toString();
                    Toast t = Toast.makeText(AddDetailEvent.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

        Button showDetailsButton = (Button) findViewById(R.id.goto_details_event);
        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToEventDetails = new Intent(AddDetailEvent.this, EventDetail.class);
                goToEventDetails.putExtra("event_id",eventIdNumber);
                startActivity(goToEventDetails);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null)
            db.close();
    }
}
