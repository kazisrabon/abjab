package com.example.bs_36.cwc1.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bs_36.cwc1.DashboardActivity;
import com.example.bs_36.cwc1.ListCatagoryActivity;
import com.example.bs_36.cwc1.R;

import java.io.IOException;
import java.util.ArrayList;

public class LocationTracking extends ActionBarActivity  implements LocationListener{
    private Criteria criteria;
    private ProgressDialog progDailog;
    private String provider;
    private static final int TIME_INTERVAL = 1000 * 60 * 2;
    private static final int DISTANCE_INTERVAL = 1;
    private Location currentLocation;
    private DashboardActivity dashBoardActivity;
    private TextView latitudeText;

    private TextView longitudeText;
    public static String locationText;
    private LocationManager locationManager;
    private String address;
    public static EditText locationEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationEdit=(EditText)findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        ;
        criteria = new Criteria();
        if (isGpsEnabled()) {
            provider = locationManager.GPS_PROVIDER;
        } else {
            provider = locationManager.getBestProvider(criteria, false);
        }
               Location location = getLocation();
        if (location != null) {
            onLocationChanged(location);
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        } else {
            popUpLocationNotification();
        }


    }
    private String getLocationAddress(Double latitude,Double longitude) throws NumberFormatException,
            IOException {
        Geocoder gCoder = new Geocoder(getBaseContext());
        ArrayList<Address> addresses = (ArrayList<Address>) gCoder
                .getFromLocation(
                        latitude,
                        longitude, 5);
        if (addresses != null && addresses.size() > 0) {
            return addresses.get(addresses.size() - 1).getFeatureName() + ","
                    + addresses.get(addresses.size() - 1).getLocality();
        }
        //Toast.makeText(getApplicationContext(),addresses.size(),Toast.LENGTH_SHORT).show();
        return "";
    }
    private Location getLocation() {
        if (locationManager.getLastKnownLocation(provider) != null)
            return locationManager.getLastKnownLocation(provider);
        else
            return null;
    }

    private Boolean isGpsEnabled() {
        return getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_LOCATION_GPS);
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (isBetterLocation(location, currentLocation)) {
            double latitude = (double) location.getLatitude();
            double longitude = (double) location.getLongitude();
                /*latitudeText.setText(Float.toString(latitude));
                longitudeText.setText(Float.toString(longitude));*/

            try {
                address=getLocationAddress(latitude,longitude);
                locationEdit.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(address.equals("")){
                Toast.makeText(getApplicationContext(),"Could not get your location,set it manually",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    /**
     * Determines whether one Location reading is better than the current
     * Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new
     *                            one
     */
    protected boolean isBetterLocation(Location location,
                                       Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TIME_INTERVAL;
        boolean isSignificantlyOlder = timeDelta < -TIME_INTERVAL;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use
        // the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be
            // worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate
                && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void popUpLocationNotification() {
                       new AlertDialog.Builder(LocationTracking.this)
                        .setMessage(
                                "Could not resolve location")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // Perform Your Task Here--When Yes Is
                                        // Pressed.
                                        dialog.cancel();
                                    }
                                }).show();

    }
    //set location
    public void set(View view){

        Intent intent=new Intent(this, ListCatagoryActivity.class);
        startActivity(intent);
        LocationTracking.this.finish();
    }
}
