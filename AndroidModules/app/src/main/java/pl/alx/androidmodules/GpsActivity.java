package pl.alx.androidmodules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class GpsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        if (ContextCompat.
                checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101 );
        }
    }

    public void getLocation(View view) {
        GpsService gpsService = new GpsService(this);
        Location location = gpsService.getLocation();
        if (location!=null) {
            String s =  String.valueOf(location.getLongitude()) + ", " +  String.valueOf(location.getLatitude());
            Log.i("alx", s);
            Toast.makeText(this, s , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Brak lokalizacji", Toast.LENGTH_SHORT).show();
        }
    }
}