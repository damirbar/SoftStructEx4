package com.example.damir.firstapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class GetLocation extends FragmentActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMyLocationButtonClickListener {



    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;


//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Button mBackBtn;
    private Button mJerusalemButton;
    private Button mTLVButton;
    private Button mPetahTikwaButton;
//    private SupportMapFragment mapFragment;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mJerusalemButton  = (Button) findViewById(R.id.jrslm_btn);
        mTLVButton        = (Button) findViewById(R.id.tlv_btn);
        mPetahTikwaButton = (Button) findViewById(R.id.pth_tikwa_btn);
        mBackBtn          = (Button) findViewById(R.id.back_button);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mJerusalemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LatLng Jerusalem = new LatLng(31.768319, 35.213710);
                mMap.addMarker(new MarkerOptions().position(Jerusalem).title("Jerusalem"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Jerusalem));

            }
        });

        mTLVButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LatLng TLV = new LatLng(32.085300, 34.781768);
                mMap.addMarker(new MarkerOptions().position(TLV).title("Tel-Aviv"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(TLV));
            }
        });

        mPetahTikwaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LatLng PetahTikwa = new LatLng(32.084041, 34.887762);
                mMap.addMarker(new MarkerOptions().position(PetahTikwa).title("Petah Tikwa"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(PetahTikwa));
            }
        });

        onMyLocationButtonClick();
        enableMyLocation();

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
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "Get current location button clicked", Toast.LENGTH_SHORT).show();

        String message = String.format(GetLocation.this.getResources()
                .getString(R.string.get_curr_loc_btn));

        Toast.makeText(GetLocation.this, message,
                Toast.LENGTH_LONG).show();

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(),
                "Location permission missing!");
    }

}