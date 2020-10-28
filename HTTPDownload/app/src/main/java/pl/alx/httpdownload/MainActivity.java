package pl.alx.httpdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tvText);
        iv = findViewById(R.id.ivImg);
    }

    public void downloadTxt(View view) {
        String url = "http://51.91.120.89/TABLICE/";
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DOWNLOADER", error.getMessage(), error );
                    }
                }

        );
        rq.add(request);
    }

    public void downloadImage(View view) {
        String url = "https://i.imgur.com/7spzG.png";
        RequestQueue rq = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(
                url,

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                        iv.setVisibility(View.VISIBLE);
                    }
                },
                0,
                0,
                ImageView.ScaleType.FIT_XY,
                null,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DOWNLOADER", error.getMessage(), error );
                        iv.setVisibility(View.INVISIBLE);
                    }
                }


        );
        rq.add(request);

    }
}