/*
 * Created by Taran Rai & Benjamin Wang on 10/11/17 8:06 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 10/11/17 8:06 PM
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

public class AddContribution extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private int detailIdNumber;
    private int eventIdNumber;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contribution);
        helper = new PotluckDbHelper(this);
        detailIdNumber = getIntent().getExtras().getInt("detail_id");
        eventIdNumber = getIntent().getExtras().getInt("event_id");

        Button contributionSubmitButton = (Button) findViewById(R.id.contribution_submit_button);
        contributionSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    db = helper.getReadableDatabase();
                    EditText contName_et = (EditText) findViewById(R.id.contribution_name);
                    String contName = contName_et.getText().toString();

                    EditText contQuantity_et = (EditText) findViewById(R.id.contribution_quantity);
                    String contQuantity = contQuantity_et.getText().toString();

                    EditText contDate_et = (EditText) findViewById(R.id.contribution_date);
                    String contDate = contDate_et.getText().toString();

                    //cursor = db.rawQuery("SELECT " +
                    //        "CASE WHEN (sum(c.Quantity) < ", null);


                    String insertQuery = "INSERT INTO CONTRIBUTION(Name, Quantity, Date, _detailId) VALUES (\'" + contName + "\',\'" + contQuantity + "\',\'" + contDate + "\',\'" + detailIdNumber + "\')";
                    db.execSQL(insertQuery);
                    Toast t = Toast.makeText(AddContribution.this, getResources().getString(R.string.contribution_add_success), Toast.LENGTH_SHORT);
                    t.show();

                } catch (SQLiteException sqlex) {
                    String msg = "[AddMasterEvent] DB unavailable";
                    msg += "\n\n" + sqlex.toString();
                    Toast t = Toast.makeText(AddContribution.this, msg, Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

    }
}
