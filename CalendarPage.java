package com.ibrahimovsh95.shamil.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

public class CalendarPage extends AppCompatActivity {
    CalendarView cl;
    // TextView editText;
    Button btn;
    ListView ls;

    int dayofmontss;

    ArrayList<String> dates;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        dates = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dates);


        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EventClassActivity.class);
                startActivity(i);
            }
        });

        cl = (CalendarView) findViewById(R.id.calendarView);
        ls = (ListView) findViewById(R.id.listView);

        if(ParseUser.getCurrentUser()!= null)
        {
            Log.i("LogIn", "here");
        }
        else
        {
            Log.i("LogIn", "not here");
        }


        cl.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "" + year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
                dayofmontss = dayOfMonth;

                ParseQuery query = new ParseQuery("events");
                query.whereEqualTo("eventday", dayofmontss);

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null)
                        {
                            arrayAdapter.clear();
                            String myObject = list.toString();
                            for(ParseObject obje : list)
                            {
                                dates.add(obje.getString("type"));
                                Log.i("Ne veryim abime", "" + obje.getString("type"));
                            }

                            ls.setAdapter(arrayAdapter);

                            arrayAdapter.notifyDataSetChanged();


                            Log.i("Query", "Objeler" + myObject);
                        }
                        else
                            Log.i("Query", "olmadi");
                    }
                });

            }
        });
    }
}
