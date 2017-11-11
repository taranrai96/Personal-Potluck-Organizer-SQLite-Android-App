/*
 * Created by Taran Rai & Benjamin Wang on 10/11/17 5:13 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 10/11/17 5:13 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Contribution extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private int count;
    private int eventIdNumber;
    private int detailIdNumber;
    private SQLiteOpenHelper helper;
    private String detailTableName;
    private String eventTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new PotluckDbHelper(this);
        eventIdNumber = getIntent().getExtras().getInt("event_id");
        detailIdNumber = getIntent().getExtras().getInt("detail_id");

        try {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.contribution_table);
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
            nameHeading_tv.setText(getResources().getString(R.string.contribution_title1));

            TextView quantityHeading_tv = new TextView(this);
            quantityHeading_tv.setTextSize(20);
            quantityHeading_tv.setPadding(15,10,15,15);
            quantityHeading_tv.setGravity(Gravity.CENTER);
            quantityHeading_tv.setTypeface(null, Typeface.BOLD);
            quantityHeading_tv.setText(getResources().getString(R.string.contribution_title2));

            TextView dateHeading_tv = new TextView(this);
            dateHeading_tv.setTextSize(20);
            dateHeading_tv.setPadding(15,10,15,15);
            dateHeading_tv.setGravity(Gravity.CENTER);
            dateHeading_tv.setTypeface(null, Typeface.BOLD);
            dateHeading_tv.setText(getResources().getString(R.string.contribution_title3));

            TextView deleteHeading_tv = new TextView(this);
            deleteHeading_tv.setTextSize(20);
            deleteHeading_tv.setPadding(15,10,15,15);
            deleteHeading_tv.setGravity(Gravity.CENTER);
            deleteHeading_tv.setTypeface(null, Typeface.BOLD);

            headingRow.addView(nameHeading_tv);
            headingRow.addView(quantityHeading_tv);
            headingRow.addView(dateHeading_tv);
            headingRow.addView(deleteHeading_tv);
            tableLayout.addView(headingRow,0);
            count = 1;
            db = helper.getReadableDatabase();

            cursor = db.rawQuery("SELECT itemName FROM EVENT_DETAIL WHERE _detailId = " + detailIdNumber, null);
            cursor.moveToFirst();
            detailTableName = cursor.getString(0);
            cursor = db.rawQuery("SELECT Name FROM EVENT_MASTER WHERE _eventId = " + eventIdNumber, null);
            cursor.moveToFirst();
            eventTableName = cursor.getString(0);
            TextView eventDetailTableName_tv = (TextView) findViewById(R.id.event_detail_table_name);
            eventDetailTableName_tv.setText(detailTableName + " - " + eventTableName);

            cursor = db.rawQuery("SELECT c._contributionId, c.Name, c.Quantity, c.Date FROM CONTRIBUTION c " +
                    "INNER JOIN EVENT_DETAIL ed ON c._detailId = ed._detailId " +
                    "INNER JOIN EVENT_MASTER em ON ed._eventId = em._eventId " +
                    "WHERE c._detailId = " + detailIdNumber + " AND " +
                    "ed._eventId = " + eventIdNumber, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10,10,10,10);
                    row.setLayoutParams(lp);

                    TextView contName_tv = new TextView(this);
                    contName_tv.setPadding(15,15,15,15);
                    contName_tv.setBackgroundResource(R.drawable.border);

                    TextView contQuantity_tv = new TextView(this);
                    contQuantity_tv.setPadding(15,15,15,15);
                    contQuantity_tv.setBackgroundResource(R.drawable.border);

                    TextView contDate_tv = new TextView(this);
                    contDate_tv.setPadding(15,15,15,15);
                    contDate_tv.setBackgroundResource(R.drawable.border);

                    contName_tv.setText(cursor.getString(1));
                    contQuantity_tv.setText(cursor.getString(2));
                    contDate_tv.setText(cursor.getString(3));

                    final int delete_id = cursor.getInt(0);
                    final ImageView deleteImage = new ImageView(this);
                    deleteImage.setImageResource(R.drawable.deletebutton);
                    deleteImage.setPadding(10,0,10,0);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String deleteEventQuery = "DELETE FROM CONTRIBUTION WHERE _contributionId = " + delete_id;
                            db.execSQL(deleteEventQuery);
                            Toast eventDeleted = Toast.makeText(Contribution.this, getResources().getString(R.string.contribution_delete_success), Toast.LENGTH_SHORT);
                            eventDeleted.show();
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    row.addView(contName_tv);
                    row.addView(contQuantity_tv);
                    row.addView(contDate_tv);
                    row.addView(deleteImage);
                    tableLayout.addView(row,count);
                    cursor.moveToNext();
                    count++;
                }
            }

    } catch (SQLiteException sqlex) {
            String msg = "[Contribution] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        menu.findItem(R.id.addEventMaster).setVisible(false);
        menu.findItem(R.id.searchEvent).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPledge:
                Intent goToAddContribution = new Intent (Contribution.this, AddContribution.class);
                goToAddContribution.putExtra("event_id",eventIdNumber);
                goToAddContribution.putExtra("detail_id",detailIdNumber);
                startActivityForResult(goToAddContribution, 1);
                return true;
            case R.id.device_info:
                startActivity(new Intent(this, MyDevice.class));
                return true;
            case R.id.about_info:
                startActivity(new Intent(this, About.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }
}
