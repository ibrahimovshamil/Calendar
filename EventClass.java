package com.ibrahimovsh95.shamil.calendar;
import android.database.DatabaseErrorHandler;
import android.location.Location;


import java.util.Date;

/**
 * Created by Eren on 11.12.2016.
 */

public class EventClass {

    String type;
    Date date;
    //Time beging;
    Date ending;

   // Location location;

    public EventClass(String type, Date date, Date ending ){
        this.type = type;
        this.date = date;
        //this.beging = beging;
        this.ending = ending;

        //this.location = location;
    }

    public void setEventType(String type){

        this.type = type;
    }

    /*public void setBeginingtime(Time beging){

        this.beging = beging;
    }*/

    public void setEndingTime(Date ending){

        this.ending = ending;
    }

   /* public void setLocation(Location location) {
        this.location = location;
    }*/

    public void setDate (Date date)
    {
        this.date = date;
    }
    public String getEventType(){
        return type;
    }

   /* public Time getBeging(){
        return beging;
    }*/

    public Date getDate(){return date;}

   /* public Location getLocation(){
        return location;
    }
*/
    public Date getEnding(){
        return ending;
    }

    public int getDay()
    {
        return date.getDay();
    }
    public String toString (String str)
    {
        return "" + getEventType() + getDate() + getEnding();
    }
}