package com.ibrahimovsh95.shamil.calendar;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MyService extends Service  {

    Timer timing;
    Handler helper;

    final static long scheduleTime = 1000;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();

        timing = new Timer();
        helper= new Handler(Looper.getMainLooper());


        timing.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                callMethod();
            }

            private void callMethod() {
                helper.post(new Runnable() {
                    public void run()
                    {
                        Calendar c = Calendar.getInstance();
                        Date cd = c.getTime(); // current date
                        Date date = new Date(cd.getYear(), cd.getMonth(), cd.getDay(), cd.getHours(), cd.getMinutes(), 0);
                        Date olddate = new Date(1995, 14,10,00,0,0);

                        ParseQuery query = new ParseQuery("events");

                        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                        query.whereEqualTo("eventday", date.getDay());
                        query.whereEqualTo("startM", date.getMonth());
                        query.whereEqualTo("startY", date.getYear());
                        query.whereEqualTo("startHour" ,date.getHours());
                        query.whereEqualTo("startMin", date.getMinutes());

                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null)
                                {
                                    /*AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                                    audio.setRingerMode(0);*/
                                    for(ParseObject obje : list)
                                    {
                                        //Date dl = obje.getDate("date");
                                        Log.i("OLDUAQ", "BURDAYIIMMM");
                                    }
                                }
                                else
                                    Log.i("Query", "olmadi");
                            }
                        });


                        //Log.i("Timeparse", date + "");
                        //Log.i("Time time", "" + cd);



                       /*if(date.compareTo(cd) == 0)
                        {
                            Log.i("equal", "yes");
                        }else
                            Log.i("equal", "no");*/
                    }
                });
            }

        }, 0, scheduleTime);
    }

    @Override
    public void onDestroy()
    {
        timing.cancel();
        super.onDestroy();
    }
}