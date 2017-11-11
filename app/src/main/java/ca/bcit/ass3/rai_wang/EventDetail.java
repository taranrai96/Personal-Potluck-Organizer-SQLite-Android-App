/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 5:12 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 09/11/17 4:33 PM
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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetail extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLiteOpenHelper helper;
    private int eventIdNumber;
    private String eventTableName;
    private int detailIdNumber;
    private String detailIdIntent;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        eventIdNumber = getIntent().getExtras().getInt("event_id");
        Button goToEventMaster = (Button) findViewById(R.id.go_to_event_master);
        goToEventMaster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToEventMaster = new Intent(EventDetail.this,EventMaster.class);
                startActivityForResult(goToEventMaster, 1);
            }
        });
        Button addDetailButton = (Button) findViewById(R.id.add_detail_button);
        addDetailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToAddDetailEvent = new Intent(EventDetail.this,AddDetailEvent.class);
                goToAddDetailEvent.putExtra("event_id",eventIdNumber);
                startActivityForResult(goToAddDetailEvent, 1);
            }
        });
        helper = new PotluckDbHelper(this);
        try {
            db = helper.getReadableDatabase();
            TableLayout tableLayout = (TableLayout) findViewById(R.id.event_detail_table);
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
            nameHeading_tv.setText(getResources().getString(R.string.event_detail_title1));

            TextView dateHeading_tv = new TextView(this);
            dateHeading_tv.setTextSize(20);
            dateHeading_tv.setPadding(15,10,15,15);
            dateHeading_tv.setGravity(Gravity.CENTER);
            dateHeading_tv.setTypeface(null, Typeface.BOLD);
            dateHeading_tv.setText(getResources().getString(R.string.event_detail_title2));

            TextView timeHeading_tv = new TextView(this);
            timeHeading_tv.setTextSize(20);
            timeHeading_tv.setPadding(15,10,15,15);
            timeHeading_tv.setGravity(Gravity.CENTER);
            timeHeading_tv.setTypeface(null, Typeface.BOLD);
            timeHeading_tv.setText(getResources().getString(R.string.event_detail_title3));

            TextView updateHeading_tv = new TextView(this);
            updateHeading_tv.setTextSize(20);
            updateHeading_tv.setPadding(15,10,15,15);
            updateHeading_tv.setGravity(Gravity.CENTER);
            updateHeading_tv.setTypeface(null, Typeface.BOLD);

            TextView deleteHeading_tv = new TextView(this);
            deleteHeading_tv.setTextSize(20);
            deleteHeading_tv.setPadding(15,10,15,15);
            deleteHeading_tv.setGravity(Gravity.CENTER);
            deleteHeading_tv.setTypeface(null, Typeface.BOLD);

            headingRow.addView(nameHeading_tv);
            headingRow.addView(dateHeading_tv);
            headingRow.addView(timeHeading_tv);
            headingRow.addView(updateHeading_tv);
            headingRow.addView(deleteHeading_tv);
            tableLayout.addView(headingRow,0);

            count = 1;
            cursor = db.rawQuery("SELECT Name FROM EVENT_MASTER WHERE _eventId = " + eventIdNumber, null);
            cursor.moveToFirst();
            eventTableName = cursor.getString(0);
            TextView eventMasterTableName_tv = (TextView) findViewById(R.id.event_master_table_name);
            eventMasterTableName_tv.setText(eventTableName);
            cursor = db.rawQuery("SELECT _detailId, itemName, itemUnit, itemQuantity FROM EVENT_DETAIL WHERE _eventId = " + eventIdNumber, null);
            findViewById(R.id.details_linear_layout).findViewById(R.id.event_detail_table);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10,10,10,10);
                    row.setLayoutParams(lp);

                    final TextView itemId_tv = new TextView(this);

                    TextView itemName_tv = new TextView(this);
                    itemName_tv.setPadding(15,15,15,15);
                    itemName_tv.setBackgroundResource(R.drawable.border);
                    itemName_tv.setClickable(true);
                    itemName_tv.setTypeface(null, Typeface.BOLD);
                    itemName_tv.setPaintFlags(itemName_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    itemName_tv.setTextColor(getResources().getColor(R.color.linkColor));

                    TextView itemUnit_tv = new TextView(this);
                    itemUnit_tv.setPadding(15,15,15,15);
                    itemUnit_tv.setBackgroundResource(R.drawable.border);

                    TextView itemQuantity_tv = new TextView(this);
                    itemQuantity_tv.setPadding(15,15,15,15);
                    itemQuantity_tv.setBackgroundResource(R.drawable.border);

                    itemId_tv.setText(cursor.getString(0));
                    itemName_tv.setText(cursor.getString(1));
                    itemUnit_tv.setText(cursor.getString(2));
                    itemQuantity_tv.setText(cursor.getString(3));

                    itemName_tv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            detailIdIntent = itemId_tv.getText().toString();
                            detailIdNumber = Integer.parseInt(detailIdIntent);
                            Intent goToContribution = new Intent(EventDetail.this,Contribution.class);
                            goToContribution.putExtra("event_id",eventIdNumber);
                            goToContribution.putExtra("detail_id",detailIdNumber);
                            startActivity(goToContribution);
                        }
                    });

                    final int delete_id = cursor.getInt(0);
                    final ImageView deleteImage = new ImageView(this);
                    deleteImage.setImageResource(R.drawable.deletebutton);
                    deleteImage.setPadding(10,0,10,0);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String deleteEventQuery = "DELETE FROM EVENT_DETAIL WHERE _detailId = " + delete_id;
                            db.execSQL(deleteEventQuery);
                            Toast eventDeleted = Toast.makeText(EventDetail.this, getResources().getString(R.string.detail_delete_success), Toast.LENGTH_SHORT);
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
                            Intent goToUpdateMasterEvent = new Intent(EventDetail.this, UpdateDetailEvent.class);
                            goToUpdateMasterEvent.putExtra("detail_id",update_id);
                            startActivityForResult(goToUpdateMasterEvent, 1);
                        }
                    });

                    row.addView(itemName_tv);
                    row.addView(itemUnit_tv);
                    row.addView(itemQuantity_tv);
                    row.addView(updateImage);
                    row.addView(deleteImage);
                    tableLayout.addView(row,count);
                    cursor.moveToNext();
                    count++;
                 }
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetail] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        menu.findItem(R.id.addEventMaster).setVisible(false);
        menu.findItem(R.id.searchEvent).setVisible(false);
        menu.findItem(R.id.addPledge).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
