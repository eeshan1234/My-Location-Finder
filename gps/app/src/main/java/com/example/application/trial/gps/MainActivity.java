package com.example.application.trial.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=findViewById(R.id.btn);
        textView=(TextView)findViewById(R.id.idtxt);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

                }

                Location location=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                onLocationChanged(location);
            }
        });

        //loc_func(location);
    }

    public void onLocationChanged(Location location)
    {
        longitude=location.getLongitude();
        latitude=location.getLatitude();
        textView.setText("Longitude:"+longitude+"\n"+"Latitude:"+latitude);
        loc_func(location);
    }


    public void onStatusChanged(String s,int i,Bundle bundle){


    }

    public void onProviderEnabled(String s){


    }

    public void onProviderDisabled(String s){

    }


    private void loc_func(Location location){


        try {
   //Toast.makeText(MainActivity.this,"HERE",Toast.LENGTH_SHORT).show();
            Geocoder geocoder=new Geocoder(this);
            List<Address> addresses=null;
            addresses=geocoder.getFromLocation(latitude,longitude,1);
            String country=addresses.get(0).getCountryName();
            String city=addresses.get(0).getLocality();
            String state=addresses.get(0).getAdminArea();
            textView.setText("Country: "+country+"\n"+"State: "+state+"\n"+"City: "+city);
            //Toast.makeText(this, ""+location.get, Toast.LENGTH_SHORT).show();


        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error:" + e,Toast.LENGTH_SHORT).show();
        }
    }}