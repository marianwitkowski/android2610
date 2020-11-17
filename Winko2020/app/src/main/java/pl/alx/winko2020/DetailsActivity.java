package pl.alx.winko2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.seismic.ShakeDetector;

import java.util.Timer;
import java.util.TimerTask;

public class DetailsActivity extends AppCompatActivity {

    Wine wine;
    private SensorManager sensorManager;
    private ShakeDetector sd;
    ImageView ivLike;
    Timer timer;

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                Log.i("winko2020", String.format("x=%f y=%f z=%f", x, y, z) );
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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

        ivLike = findViewById(R.id.imageViewLike);
        WineUtils.setLikeState(ivLike, wine);

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLike();
            }
        });

        sd = new ShakeDetector(new ShakeDetector.Listener() {
            @Override
            public void hearShake() {
                Log.i("winko2020", "shake detected");
                sd.stop();
                changeLike();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.i("winko2020", "odpalam shake detector na nowo");
                        sd.start(sensorManager);
                    }
                }, 5000);
            }
        });
        sd.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
    }

    private void changeLike() {
        WineUtils.changeLike(ivLike, wine);
        SharedPreferences sharedPreferences =  getSharedPreferences("winko2020", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("liked_wines", WineUtils.getLikesAsString());
        editor.commit();

        WineUtils.playBeep(this);
    }

    @Override
    protected void onResume() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sd.start(sensorManager);

        /*sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL );*/
        super.onResume();
    }

    @Override
    protected void onPause() {
        sd.stop();

        //sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }
}