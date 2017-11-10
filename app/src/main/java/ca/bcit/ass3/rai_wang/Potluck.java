/*
 * Created by Taran Rai & Benjamin Wang on 09/11/17 5:12 PM
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 02/11/17 3:51 PM
 */

package ca.bcit.ass3.rai_wang;

public class Potluck {
    /* Event_Master */
    private int _eventId;
    private String _name;
    private String _date;
    private String _time;

    /*Event_Detail*/
    private int _detailId;
    private int _itemName;
    private String _itemUnit;
    private int _itemQuantity;
    /*_eventId is foreign key in this table*/

    /*Contribution*/
    private int _contributionId;
    private String _ContributionName; /*Name in database*/
    private String _quantity;
    private String _ContributionDate; /*Date in database*/
    /*_detailId is foreign key in this table*/

    public static final Potluck[] PotluckEvents = {
            new Potluck("Halloween Party", "Oct 30, 2017", "6:30 PM"),
            new Potluck("Christmas Party","December 20, 2017","12:30 PM"),
            new Potluck("New Year Eve","December 31, 2017","8:00 PM"),
    };

    public Potluck(String name, String date, String time) {
        _name = name;
        _date = date;
        _time = time;
    }

    public String getName() {return _name;}

    public String getDate() {return _date;}

    public String getTime() {return _time;}

    public String toString() {return _name;}

}
