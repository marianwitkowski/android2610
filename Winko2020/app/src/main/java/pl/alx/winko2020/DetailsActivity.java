package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    Wine wine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String s = getIntent().getStringExtra("data");
        wine = new Gson().fromJson(s, Wine.class);
        Log.i("WINKO", wine.getPimId());
    }
}