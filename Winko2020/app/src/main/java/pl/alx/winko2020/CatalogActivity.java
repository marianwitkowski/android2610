package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    List<Wine> allWineList = new ArrayList<Wine>();
    int partId = 0;
    ListView listView;
    CatalogAdapter adapter;

    final int PART_SIZE = 50;

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

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
                allWineList.add(wine);
                if (i>=125) break;
            } catch (Exception exc) {
                Log.e("WINKO", exc.getMessage());
            }
        }

        // jesteśmy za pętlą
        partId = 0;
        wineList = loadPartialData();
        adapter = new CatalogAdapter(this, wineList);
        listView.setAdapter(adapter);

    }

    public List<Wine> loadPartialData() {
        int indexStart = partId*PART_SIZE;
        int indexStop = partId*PART_SIZE + PART_SIZE;
        if (indexStart>=allWineList.size()) {
            return new ArrayList<>();
        }
        if (indexStop>allWineList.size()) {
            indexStop = allWineList.size();
        }
        List<Wine> data = new ArrayList<>(allWineList.subList(indexStart, indexStop));
        return data;
    }


    public void loadMore(View view) {
        partId++;
        adapter.addData(loadPartialData());

        if ((partId+1)*PART_SIZE>=allWineList.size()-1) {
            //zablokuj przycisk
            ((Button)view).setEnabled(false);
        }
    }
}