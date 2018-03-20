package com.ibrahimovsh95.shamil.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class EventClassActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    Calendar calendar;
    Button btn, btn2, btn3, btn4, btn5;
    int hourOfDay, minute;
    Date d, c, endDate;
    Time t1, t2, currentT;
    int year, month, date;
    EventClass e, e1 ;
    Calendar cll;

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_class2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cll = Calendar.getInstance();

        btn2 = (Button) findViewById(R.id.button3);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);

        btn = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button4);
        btn4 = (Button) findViewById(R.id.button5);
        btn5 = (Button) findViewById(R.id.location);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "oldu", Toast.LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(111);
                Toast.makeText(getApplicationContext(), "oldu 2", Toast.LENGTH_LONG).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(888);

            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(222);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i  = new Intent(getApplicationContext(), GeoMap.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, date);
        }

        if (id == 111)
        {
            return new TimePickerDialog(this, myDateListener1, hourOfDay, minute, true );
        }

        if (id == 888) {
            return new DatePickerDialog(this,
                    myDateListener2, year, month, date);
        }

        if (id == 222)
        {
            return new TimePickerDialog(this, myDateListener3, hourOfDay, minute, true );
        }
        return null;
    }



    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    Toast.makeText(getApplicationContext(), "date" + arg1 + "/" + (arg2+1) + "/" + arg3, Toast.LENGTH_LONG).show();
                    d = new Date(arg1-1900, arg2, arg3, 0, 0);

                    e = new EventClass("Futbo", null, null);
                    e.setDate(d);

                    if (e != null)
                    {
                        Log.i("EventBiz", "icinde");
                    }


                }
            };

    private TimePickerDialog.OnTimeSetListener myDateListener1 = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    //t1 = new Time(hourOfDay,minute,0);
                    //t2 = new Time(hourOfDay+1,minute+10,0);
                    d.setHours(hourOfDay);
                    d.setMinutes(minute);


                    Toast.makeText(getApplicationContext(), "timepicker", Toast.LENGTH_LONG).show();


                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    Toast.makeText(getApplicationContext(), "date" + arg1 + "/" + (arg2+1) + "/" + arg3, Toast.LENGTH_LONG).show();
                    endDate = new Date(arg1-1900, arg2, arg3);

                    e1 = new EventClass("Futbol", endDate, null);
                    e1.setEndingTime(endDate);

                    if (e1 != null)
                    {
                        Log.i("EventBiz", "icinde");
                    }


                }
            };

    private TimePickerDialog.OnTimeSetListener myDateListener3 = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endDate.setHours(hourOfDay);
                    endDate.setMinutes(minute);

                    String lon = getIntent().getExtras().getString("lon");
                    String lat = getIntent().getExtras().getString("lat");

                    Log.i("Lat and lon", lon + "//" + lat);

                    ParseObject object = new ParseObject("events");
                    object.put("username", ParseUser.getCurrentUser().getUsername());
                    object.put("type", e.getEventType());

                    object.put("date", e.getDate());
                    object.put("ending", e1.getEnding());

                    object.put("eventday", e.getDate().getDate());
                    object.put("eventend", e1.getDate().getDate());


                    object.put("startM", e.getDate().getMonth());
                    object.put("endM", e1.getDate().getMonth());

                    object.put("startY", e.getDate().getYear());
                    object.put("endY", e1.getDate().getYear());

                    object.put("startHour", e.getDate().getHours());
                    object.put("endHour", e1.getDate().getHours());

                    object.put("startMin", e.getDate().getMinutes());
                    object.put("endMin", e1.getDate().getMinutes());

                    object.put("long", lon);
                    object.put("lat", lat);


                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null)
                            {
                                Toast.makeText(getApplication().getBaseContext(), "Eklendi", Toast.LENGTH_LONG).show();
                            }else
                            {
                                Toast.makeText(getApplication().getBaseContext(), "eklenemedi", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };

}
