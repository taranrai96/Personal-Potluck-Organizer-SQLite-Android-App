/*
 * Created by Taran Rai & Benjamin Wang on 10/11/17 9:10 AM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 10/11/17 9:10 AM
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

public class UpdateDetailEvent extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private Cursor cursor;
    private int selected_detail_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail_event);
        helper = new PotluckDbHelper(this);
        selected_detail_id = getIntent().getExtras().getInt("detail_id");
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT itemName, itemUnit, itemQuantity FROM EVENT_DETAIL WHERE _detailId = " + selected_detail_id, null);
        cursor.moveToFirst();
        EditText itemName_et = (EditText) findViewById(R.id.details_item);
        itemName_et.setText(cursor.getString(0));
        EditText itemUnit_et = (EditText) findViewById(R.id.details_unit);
        itemUnit_et.setText(cursor.getString(1));
        EditText itemQuantity_et = (EditText) findViewById(R.id.details_quantity);
        itemQuantity_et.setText(cursor.getString(2));

        Button updateDetail = (Button) findViewById(R.id.details_update_button);
        updateDetail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    db = helper.getReadableDatabase();

                    EditText itemName_et = (EditText) findViewById(R.id.details_item);
                    String itemName = itemName_et.getText().toString();
                    EditText itemQuantity_et = (EditText) findViewById(R.id.details_quantity);
                    String itemQuantity = itemQuantity_et.getText().toString();
                    EditText itemUnit_et = (EditText) findViewById(R.id.details_unit);
                    String itemUnit = itemUnit_et.getText().toString();

                    String updateEventQuery = "UPDATE EVENT_DETAIL SET itemName = \'" + itemName + "\', itemUnit = \'" + itemUnit + "\', itemQuantity = \'" + itemQuantity + "\' WHERE _detailId = " + selected_detail_id;
                    db.execSQL(updateEventQuery);
                    Toast t = Toast.makeText(UpdateDetailEvent.this, getResources().getString(R.string.event_update_success), Toast.LENGTH_SHORT);
                    t.show();
                    finish();
                    startActivity(getIntent());

                } catch (SQLiteException sqlex) {
                    String msg = "[UpdateDetailEvent / updateDetail] DB unavailable";
                    msg += "\n\n" + sqlex.toString();
                    Toast t = Toast.makeText(UpdateDetailEvent.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }
}
