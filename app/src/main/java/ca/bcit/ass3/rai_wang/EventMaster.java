/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 5:10 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 09/11/17 5:10 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EventMaster extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private int count;
    private Button updatEventButton;
    String eventIdIntent;
    int eventIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SQLiteOpenHelper helper = new PotluckDbHelper(this);

        try {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.event_master_table);
            /*Add Heading Row*/
            TableRow headingRow = new TableRow(this);
            TableRow.LayoutParams headingLp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            headingRow.setLayoutParams(headingLp);
            TextView nameHeading_tv = new TextView(this);
            nameHeading_tv.setTextSize(20);
            nameHeading_tv.setPadding(15,10,15,15);
            nameHeading_tv.setGravity(Gravity.CENTER);
            nameHeading_tv.setTypeface(null, Typeface.BOLD);
            nameHeading_tv.setText(getResources().getString(R.string.event_master_title1));
            TextView dateHeading_tv = new TextView(this);
            dateHeading_tv.setTextSize(20);
            dateHeading_tv.setPadding(15,10,15,15);
            dateHeading_tv.setGravity(Gravity.CENTER);
            dateHeading_tv.setTypeface(null, Typeface.BOLD);
            dateHeading_tv.setText(getResources().getString(R.string.event_master_title2));
            TextView timeHeading_tv = new TextView(this);
            timeHeading_tv.setTextSize(20);
            timeHeading_tv.setPadding(15,10,15,15);
            timeHeading_tv.setGravity(Gravity.CENTER);
            timeHeading_tv.setTypeface(null, Typeface.BOLD);
            timeHeading_tv.setText(getResources().getString(R.string.event_master_title3));
            TextView updateHeading_tv = new TextView(this);
            updateHeading_tv.setTextSize(20);
            updateHeading_tv.setPadding(15,10,15,15);
            updateHeading_tv.setGravity(Gravity.CENTER);
            updateHeading_tv.setTypeface(null, Typeface.BOLD);
            //updateHeading_tv.setText("UP");
            TextView deleteHeading_tv = new TextView(this);
            deleteHeading_tv.setTextSize(20);
            deleteHeading_tv.setPadding(15,10,15,15);
            deleteHeading_tv.setGravity(Gravity.CENTER);
            deleteHeading_tv.setTypeface(null, Typeface.BOLD);
            //deleteHeading_tv.setText("DE");

            headingRow.addView(nameHeading_tv);
            headingRow.addView(dateHeading_tv);
            headingRow.addView(timeHeading_tv);
            headingRow.addView(deleteHeading_tv);
            headingRow.addView(updateHeading_tv);
            tableLayout.addView(headingRow,0);
            count = 1;
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("SELECT _eventId, name, date, time FROM EVENT_MASTER", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);

                    final TextView eventId_tv = new TextView(this);

                    TextView eventName_tv = new TextView(this);
                    eventName_tv.setPadding(15,15,15,15);
                    eventName_tv.setGravity(Gravity.CENTER);
                    eventName_tv.setBackgroundResource(R.drawable.border);
                    eventName_tv.setClickable(true);
                    eventName_tv.setTypeface(null, Typeface.BOLD);
                    eventName_tv.setPaintFlags(eventName_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    eventName_tv.setTextColor(getResources().getColor(R.color.linkColor));

                    TextView eventDate_tv = new TextView(this);
                    eventDate_tv.setPadding(15,15,15,15);
                    eventDate_tv.setGravity(Gravity.CENTER);
                    eventDate_tv.setBackgroundResource(R.drawable.border);

                    TextView eventTime_tv = new TextView(this);
                    eventTime_tv.setPadding(15,15,15,15);
                    eventTime_tv.setGravity(Gravity.CENTER);
                    eventTime_tv.setBackgroundResource(R.drawable.border);

                    eventId_tv.setText(cursor.getString(0));
                    eventName_tv.setText(cursor.getString(1));
                    eventDate_tv.setText(cursor.getString(2));
                    eventTime_tv.setText(cursor.getString(3));

                    eventName_tv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            eventIdIntent = eventId_tv.getText().toString();
                            eventIdNumber = Integer.parseInt(eventIdIntent);
                            Intent goToDetails = new Intent(EventMaster.this,EventDetail.class);
                            goToDetails.putExtra("event_id",eventIdNumber);
                            startActivity(goToDetails);
                        }
                    });

                    final int delete_id = cursor.getInt(0);
                    final ImageView deleteImage = new ImageView(this);
                    deleteImage.setImageResource(R.drawable.deletebutton);
                    deleteImage.setPadding(10,0,10,0);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String deleteEventQuery = "DELETE FROM EVENT_MASTER WHERE _eventId = " + delete_id;
                            db.execSQL(deleteEventQuery);
                            Toast eventDeleted = Toast.makeText(EventMaster.this, getResources().getString(R.string.event_delete_success), Toast.LENGTH_SHORT);
                            eventDeleted.show();
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    final int update_id = cursor.getInt(0);
                    final ImageView updateImage = new ImageView(this);
                    updateImage.setImageResource(R.drawable.updatebutton);
                    updateImage.setPadding(10,0,0,0);
                    updateImage.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent goToUpdateMasterEvent = new Intent(EventMaster.this, UpdateMasterEvent.class);
                            goToUpdateMasterEvent.putExtra("event_id",update_id);
                            startActivityForResult(goToUpdateMasterEvent, 1);
                        }
                    });

                    row.addView(eventName_tv);
                    row.addView(eventDate_tv);
                    row.addView(eventTime_tv);
                    row.addView(updateImage);
                    row.addView(deleteImage);
                    tableLayout.addView(row,count);
                    cursor.moveToNext();
                    count++;
                }
            }

        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity/getEventMaster] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addEventMaster:
                startActivityForResult(new Intent(this, AddMasterEvent.class), 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 || requestCode == 0) {
            finish();
            startActivity(getIntent());
        }
    }

}
