/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 5:12 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 08/11/17 5:48 PM
 */

package ca.bcit.ass3.rai_wang;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PotluckDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Potluck.sqlite";
    private static final int DB_VERSION = 2;
    private Context context;

    public PotluckDbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(getCreateEventMasterTableSql());
                db.execSQL(getCreateEventDetailTableSql());
                db.execSQL(getCreateContributionTableSql());

                for (Potluck p : PotluckEvents) {
                    insertEventMaster(db, p);
                }
            }
            if (oldVersion < 2){

            }
        } catch (SQLException sqle) {
            String msg = "[PotluckDbHelper/updateMyDatabase/insertPotluck] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    private String getCreateEventMasterTableSql() {
        String sql = "";
        sql += "CREATE TABLE EVENT_MASTER (";
        sql += "_eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Date TEXT, ";
        sql += "Time TEXT);";

        return sql;
    }

    private String getCreateEventDetailTableSql() {
        String sql = "";
        sql += "CREATE TABLE EVENT_DETAIL (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "ItemName TEXT, ";
        sql += "ItemUnit TEXT, ";
        sql += "ItemQuantity INTEGER, ";
        sql += "_eventId INTEGER, ";
        sql += "FOREIGN KEY (_eventId) REFERENCES EVENT_MASTER(_eventId));";

        return sql;
    }

    private String getCreateContributionTableSql() {
        String sql = "";
        sql += "CREATE TABLE CONTRIBUTION (";
        sql += "_contributionId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Quantity TEXT, ";
        sql += "Date TEXT, ";
        sql += "_detailId INTEGER, ";
        sql += "FOREIGN KEY (_detailId) REFERENCES EVENT_DETAIL(_detailId));";

        return sql;
    }

    private void insertEventMaster(SQLiteDatabase db, Potluck potluck) { //to be updated
        ContentValues values = new ContentValues();
        values.put("Name", potluck.getName());
        values.put("Date", potluck.getDate());
        values.put("Time", potluck.getTime());

        db.insert("EVENT_MASTER", null, values);
    }

    private static final Potluck[] PotluckEvents = {
            new Potluck("Halloween Party", "Oct 30, 2017", "6:30 PM"),
            new Potluck("Christmas Party","December 20, 2017","12:30 PM"),
            new Potluck("New Year Eve","December 31, 2017","8:00 PM"),
    };
}
