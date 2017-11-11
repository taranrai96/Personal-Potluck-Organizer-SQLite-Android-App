/*
 * Created by Taran Rai & Benjamin Wang on 10/11/17 9:06 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 10/11/17 9:06 PM
 */

package ca.bcit.ass3.rai_wang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventSearch extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private int count;
    private SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        helper = new PotluckDbHelper(this);
        db = helper.getReadableDatabase();

        Button searchEventButton = (Button) findViewById(R.id.search_event_button);
        searchEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }
}
