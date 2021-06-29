package com.example.myandroidlocationtest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv_show;
    Button btn_getdata1, btn_getdata2, btn_getdata3 ;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = findViewById(R.id.show);
        btn_getdata1 = findViewById(R.id.getdata1);
        btn_getdata2 = findViewById(R.id.getdata2);
        btn_getdata3 = findViewById(R.id.getdata3);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        List<String> s = locationManager.getAllProviders();
        for (int i=0; i<s.size(); i++){
            if (i<s.size()-1){
                tv_show.setText(tv_show.getText() + s.get(i).toString() +",");
            }else
                tv_show.setText(tv_show.getText() + s.get(i).toString());
        }

        btn_getdata1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListener = new MylocationListener1();
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            }
        });

        btn_getdata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListener = new MylocationListener2();
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
            }
        });

        btn_getdata3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListener = new MylocationListener3();
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 10, locationListener);
            }
        });
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private class MylocationListener1 implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            tv_show.setText("");
            String longitude = "Longitude: " + location.getLongitude();
            Log.v("TAG1", longitude);
            String latitude = "Latitude: " + location.getLatitude();
            Log.v("TAG1", latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = "GPS Provider\nVị trí: \n" + longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            tv_show.setText(s);
        }
    }

    private class MylocationListener2 implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            tv_show.setText("");
            String longitude = "Longitude: " + location.getLongitude();
            Log.v("TAG1", longitude);
            String latitude = "Latitude: " + location.getLatitude();
            Log.v("TAG1", latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = "Network Provider\nVị trí: \n" + longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            tv_show.setText(s);
        }
    }

    private class MylocationListener3 implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            tv_show.setText("");
            String longitude = "Longitude: " + location.getLongitude();
            Log.v("TAG1", longitude);
            String latitude = "Latitude: " + location.getLatitude();
            Log.v("TAG1", latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = "Passive Provider\nVị trí: \n" + longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            tv_show.setText(s);
        }
    }
}