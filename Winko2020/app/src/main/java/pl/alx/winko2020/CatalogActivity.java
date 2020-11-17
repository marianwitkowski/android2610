package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    List<Wine> wineList = new ArrayList<Wine>();
    ListView listView;
    CatalogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        listView = findViewById(R.id.listCatalog);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //tworzenie nowego activity ze szczegółami
                Intent intent = new Intent(CatalogActivity.this, DetailsActivity.class );
                Wine wine = wineList.get(position);
                String wineStr = new Gson().toJson(wine);
                intent.putExtra("data", wineStr);
                startActivity(intent);
            }
        });
        loadData();
        
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("winko2020"));

        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void onEvent(Wine wine) {
        Log.i("winko2020", "odebrałem:" + wine.getPimId());
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("winko2020", "broadcastReceiver");
            Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            //listView.invalidateViews();
            adapter.notifyDataSetChanged();
        }
    }; 
    
    @Override
    protected void onStop() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
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
        adapter = new CatalogAdapter(this, wineList);
        listView.setAdapter(adapter);

    }

}