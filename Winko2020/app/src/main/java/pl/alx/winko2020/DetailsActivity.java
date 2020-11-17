package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView tv = findViewById(R.id.tvName);
        tv.setText(wine.getName());

        tv = findViewById(R.id.tvCountry);
        tv.setText(wine.getCountry());

        tv = findViewById(R.id.tvColor);
        tv.setText(wine.getColor());

        tv = findViewById(R.id.tvPrice);
        tv.setText(String.format("%.2f zł", wine.getPrice()).replace(".",","));

        ImageView iv = findViewById(R.id.imageView);
        WineUtils.loadImage(this, iv, wine.getImage());

        WebView webView = findViewById(R.id.webDescr);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //włącza obsługę JavaScript

        String html = wine.getDescr().replace("#FFF", "#000");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}