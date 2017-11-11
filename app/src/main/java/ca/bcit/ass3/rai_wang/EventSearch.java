/*
 * Created by Taran Rai & Benjamin Wang on 10/11/17 9:06 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 10/11/17 9:06 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EventSearch extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private int count;
    private SQLiteOpenHelper helper;
    String eventIdIntent;
    int eventIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        helper = new PotluckDbHelper(this);
        db = helper.getReadableDatabase();

        Button searchEventButton = (Button) findViewById(R.id.search_event_button);
        searchEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText searchEventEntry_et = (EditText) findViewById(R.id.search_event_entry);
                String searchEventEntry = searchEventEntry_et.getText().toString();

                cursor = db.rawQuery("SELECT _eventId, Name, Date, Time FROM EVENT_MASTER " +
                        "WHERE Name LIKE \'" + searchEventEntry + "%\'", null);

                TableLayout tableLayout = (TableLayout) findViewById(R.id.event_search_table);
                tableLayout.removeAllViews();
                /*Add Heading Row*/
                TableRow headingRow = new TableRow(EventSearch.this);
                TableRow.LayoutParams headingLp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                headingRow.setLayoutParams(headingLp);
                TextView nameHeading_tv = new TextView(EventSearch.this);
                nameHeading_tv.setTextSize(getResources().getInteger(R.integer.text_size_20));
                nameHeading_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                nameHeading_tv.setGravity(Gravity.CENTER);
                nameHeading_tv.setTypeface(null, Typeface.BOLD);
                nameHeading_tv.setText(getResources().getString(R.string.event_master_title1));
                TextView dateHeading_tv = new TextView(EventSearch.this);
                dateHeading_tv.setTextSize(getResources().getInteger(R.integer.text_size_20));
                dateHeading_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                dateHeading_tv.setGravity(Gravity.CENTER);
                dateHeading_tv.setTypeface(null, Typeface.BOLD);
                dateHeading_tv.setText(getResources().getString(R.string.event_master_title2));
                TextView timeHeading_tv = new TextView(EventSearch.this);
                timeHeading_tv.setTextSize(getResources().getInteger(R.integer.text_size_20));
                timeHeading_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                timeHeading_tv.setGravity(Gravity.CENTER);
                timeHeading_tv.setTypeface(null, Typeface.BOLD);
                timeHeading_tv.setText(getResources().getString(R.string.event_master_title3));
                TextView updateHeading_tv = new TextView(EventSearch.this);
                updateHeading_tv.setTextSize(getResources().getInteger(R.integer.text_size_20));
                updateHeading_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                updateHeading_tv.setGravity(Gravity.CENTER);
                updateHeading_tv.setTypeface(null, Typeface.BOLD);
                TextView deleteHeading_tv = new TextView(EventSearch.this);
                deleteHeading_tv.setTextSize(getResources().getInteger(R.integer.text_size_20));
                deleteHeading_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                deleteHeading_tv.setGravity(Gravity.CENTER);
                deleteHeading_tv.setTypeface(null, Typeface.BOLD);

                headingRow.addView(nameHeading_tv);
                headingRow.addView(dateHeading_tv);
                headingRow.addView(timeHeading_tv);
                headingRow.addView(deleteHeading_tv);
                headingRow.addView(updateHeading_tv);
                tableLayout.addView(headingRow,getResources().getInteger(R.integer.number_0));
                count = getResources().getInteger(R.integer.number_1);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        TableRow row = new TableRow(EventSearch.this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(lp);

                        final TextView eventId_tv = new TextView(EventSearch.this);

                        TextView eventName_tv = new TextView(EventSearch.this);
                        eventName_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                        eventName_tv.setGravity(Gravity.CENTER);
                        eventName_tv.setBackgroundResource(R.drawable.border);
                        eventName_tv.setClickable(true);
                        eventName_tv.setTypeface(null, Typeface.BOLD);
                        eventName_tv.setPaintFlags(eventName_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        eventName_tv.setTextColor(getResources().getColor(R.color.linkColor));

                        TextView eventDate_tv = new TextView(EventSearch.this);
                        eventDate_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                        eventDate_tv.setGravity(Gravity.CENTER);
                        eventDate_tv.setBackgroundResource(R.drawable.border);

                        TextView eventTime_tv = new TextView(EventSearch.this);
                        eventTime_tv.setPadding(getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15),getResources().getInteger(R.integer.padding_15));
                        eventTime_tv.setGravity(Gravity.CENTER);
                        eventTime_tv.setBackgroundResource(R.drawable.border);

                        eventId_tv.setText(cursor.getString(getResources().getInteger(R.integer.number_0)));
                        eventName_tv.setText(cursor.getString(getResources().getInteger(R.integer.number_1)));
                        eventDate_tv.setText(cursor.getString(getResources().getInteger(R.integer.number_2)));
                        eventTime_tv.setText(cursor.getString(getResources().getInteger(R.integer.number_3)));

                        eventName_tv.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                eventIdIntent = eventId_tv.getText().toString();
                                eventIdNumber = Integer.parseInt(eventIdIntent);
                                Intent goToDetails = new Intent(EventSearch.this,EventDetail.class);
                                goToDetails.putExtra("event_id",eventIdNumber);
                                startActivity(goToDetails);
                            }
                        });

                        final int delete_id = cursor.getInt(getResources().getInteger(R.integer.number_0));
                        final ImageView deleteImage = new ImageView(EventSearch.this);
                        deleteImage.setImageResource(R.drawable.deletebutton);
                        deleteImage.setPadding(getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.number_0),getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.number_0));
                        deleteImage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                String deleteEventQuery = "DELETE FROM EVENT_MASTER WHERE _eventId = " + delete_id;
                                db.execSQL(deleteEventQuery);
                                Toast eventDeleted = Toast.makeText(EventSearch.this, getResources().getString(R.string.event_delete_success), Toast.LENGTH_SHORT);
                                eventDeleted.show();
                                finish();
                                startActivity(getIntent());
                            }
                        });

                        final int update_id = cursor.getInt(getResources().getInteger(R.integer.number_0));
                        final ImageView updateImage = new ImageView(EventSearch.this);
                        updateImage.setImageResource(R.drawable.updatebutton);
                        updateImage.setPadding(getResources().getInteger(R.integer.padding_10),getResources().getInteger(R.integer.number_0),getResources().getInteger(R.integer.number_0),getResources().getInteger(R.integer.number_0));
                        updateImage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent goToUpdateMasterEvent = new Intent(EventSearch.this, UpdateMasterEvent.class);
                                goToUpdateMasterEvent.putExtra("event_id",update_id);
                                startActivityForResult(goToUpdateMasterEvent, getResources().getInteger(R.integer.number_1));
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
            }
        });
    }
}
