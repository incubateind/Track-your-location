package com.example.mapsincubate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{


     GoogleMap mMap;
    FusedLocationProviderClient mFusedLocation;

    int signal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
       signal= getIntent().getIntExtra("s",0);




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

       // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



//        // Add a marker in Jaipur and move the camera
//        LatLng jaipur = new LatLng(26.8749,75.7684 );

        if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){
            return;

        }
        mMap.setMyLocationEnabled(true);
        mFusedLocation= LocationServices.getFusedLocationProviderClient(this);
        LocationRequest request = LocationRequest.create();
        request.setInterval(1000);
        request.setFastestInterval(500);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(request);
        SettingsClient client=LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    Location current= location;
                    LatLng loc= new LatLng(current.getLatitude(),current.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
                    if(signal==1){
                        Intent i2 = new Intent(MapsActivity.this,MainActivity.class);
                        i2.putExtra("loc",current);
                        startActivity(i2);
                    }
                }
            }
        });


    }


}
