package com.ibrahimovsh95.shamil.calendar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;

public class GeoMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btn;
    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btn = (Button) findViewById(R.id.backButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EventClassActivity.class);

                i.putExtra("lat", lat + "");
                i.putExtra("lon", lon + "");
                Log.i("Log bef", lat + "//" + lon);
                startActivity(i);


            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Marker marker = null;

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {




            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder gc = new Geocoder(GeoMap.this);
                LatLng LL = marker.getPosition();
                lat = LL.latitude;
                lon = LL.longitude;
                List<android.location.Address> list = null;
                try {
                    list =  gc.getFromLocation(lat,lon , 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address add = list.get(0);
                String s = add.getLocality();
                marker.setTitle(add.getLocality());
                marker.showInfoWindow();
                marker.setPosition(LL);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(LL));
                Log.d("System out", LL.latitude + " " + "onMarkerDragEnd..." + " " + LL.longitude + s);
            }
        });



        // Add a marker in Sydney and move the camera
        //   LatLng sydney = new LatLng(37.41, 31.84);
        mMap.addMarker(new MarkerOptions().position(new LatLng(37.41, 31.84)).title("Marker").draggable(true));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.41, 31.84)));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //    mMap.setMyLocationEnabled(true);
    }

    private void setUpMap(){
        //   mMap.addMarker(new MarkerOptions().position(new LatLng(34,35)).title("Marker"));
    }

}