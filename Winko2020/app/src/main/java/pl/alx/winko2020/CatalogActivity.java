package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    List<Wine> wineList = new ArrayList<Wine>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        loadData();
    }

    public void loadData() {
        AndroidNetworking.get(Consts.API_URL + "/user/products")
                .addHeaders("Authorization", "Bearer "+Globals.access_token)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("WINKO", "pobrano listę");
                        parseData(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(CatalogActivity.this,
                                "Nie można pobrać listy produktów", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    public void parseData(JSONArray array) {
        for(int i=0;i<array.length();i++) {
            try {
                JSONObject jo = array.getJSONObject(i);
                Wine wine = new Wine(
                        jo.getString("pim_id"),
                        jo.getString("nazwa"),
                        jo.getString("kraj"),
                        jo.getString("kolor"),
                        jo.getString("obrazek"),
                        jo.getString("opis"),
                        jo.getDouble("cena"),
                        jo.getInt("liczba_glosow"),
                        jo.getInt("suma_ocen")
                );
                wineList.add(wine);
                if (i>=50) break;
            } catch (Exception exc) {
                Log.e("WINKO", exc.getMessage());
            }
        }
        // jesteśmy za pętlą
    }

}