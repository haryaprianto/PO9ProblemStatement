package com.example.a16022596.po9problemstatement;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    Button btnStop;
    Button btnStart;
    Button btnCheck;
    TextView tvResult;

    FusedLocationProviderClient client;
    LocationCallback mLocationCallBack;
    LocationRequest mLocationRequest = LocationRequest.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStop = (Button)findViewById(R.id.buttonStop);
        btnStart = (Button)findViewById(R.id.buttonStart);
        btnCheck = (Button)findViewById(R.id.buttonCheck);
        tvResult = (TextView)findViewById(R.id.tvResult);

        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);



        if (checkPermission() == true) {
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        String msg = "Last seen location when this activity started:" + "\n" +"Latitude : " + location.getLatitude() + "\n" + "Longtitude : " + location.getLongitude();
                        tvResult.setText(msg);
                    } else {
                        String msg = "No last Known Location found";
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Permission Not granted", Toast.LENGTH_SHORT);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                startService(i);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                stopService(i);
            }
        });









    }

    private boolean checkPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
