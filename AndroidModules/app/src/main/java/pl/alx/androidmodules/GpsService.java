package pl.alx.androidmodules;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GpsService extends Service implements LocationListener {

    Context ctx;
    Location location;
    double latitude;
    double longitude;
    LocationManager locationManager;

    public GpsService(Context ctx) {
        this.ctx = ctx;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) ctx.getSystemService(LOCATION_SERVICE);

            boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPS) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        15000,
                        10, this
                );
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return location;
            }

            if (isNetwork) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        15000,
                        10, this
                );
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return location;
            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
