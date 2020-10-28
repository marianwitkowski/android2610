package pl.alx.httpdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tvText);
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
}