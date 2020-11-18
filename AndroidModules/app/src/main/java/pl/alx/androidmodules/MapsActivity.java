package pl.alx.androidmodules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    JSONArray shops;
    GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        readData();

        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    public void readData() {
        try {
            InputStream inputStream = getAssets().open("places.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String s = new String(buffer);
            shops = new JSONArray(s);
        } catch (Exception exc) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        for (int i=0;i<shops.length();i++) {
            try {
                JSONObject item = shops.getJSONObject(i);
                LatLng latLng = new LatLng( item.optDouble("lat"), item.optDouble("lon") );
                map.addMarker( new MarkerOptions().position(latLng) ).setTag(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.2351, 21.01), 6));
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                JSONObject data = (JSONObject) marker.getTag();
                Toast.makeText(MapsActivity.this, data.optString("nazwa"), Toast.LENGTH_SHORT).show();
                Log.i("alx", data.toString());
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }
}