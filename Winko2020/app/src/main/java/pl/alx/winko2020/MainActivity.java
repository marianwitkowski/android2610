package pl.alx.winko2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class MainActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnLogin;

    private static OkHttpClient myHttpClient() {
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{

                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            //Using TLS 1_2 & 1_1 for HTTP/2 Server requests
            // Note : The following is suitable for my Server. Please change accordingly
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                    .build();

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.connectionSpecs(Collections.singletonList(spec));
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //usuwamy pasek tytułowy okna
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        AndroidNetworking.initialize(getApplicationContext(), myHttpClient());
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC);

        SharedPreferences sharedPreferences = getSharedPreferences("winko2020", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("liked_wines", "{}");
        WineUtils.loadLikes(s);

        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

    }

    Thread.UncaughtExceptionHandler crashHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            Log.e("ERROR", e.getMessage() );
        }
    };

    public void startLogin(View view) {

        btnLogin.setEnabled(false);
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", etUser.getText().toString().trim());
            jo.put("password", etPass.getText().toString().trim());
            jo.put("device_token", "");
            jo.put("grant_type", "password");
            jo.put("client_id", "mobile_app");
            jo.put("platform", "android");

            AndroidNetworking.post(Consts.API_URL + "/account/login")
                    .addStringBody(jo.toString())
                    .setContentType("application/json")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            btnLogin.setEnabled(true);
                            Log.i("WINKO", response.toString());
                            String s = response.optString("access_token", "");
                            if (s.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Nie można zalogować się", Toast.LENGTH_SHORT).show();
                            } else {
                                Globals.access_token = s;
                                startActivity(new Intent(MainActivity.this, CatalogActivity.class));
                                finish();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("WINKO", anError.getErrorDetail());
                            String s = "";
                            try {
                                JSONObject jo = new JSONObject(anError.getErrorBody());
                                s = jo.getString("error");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                s = "Wystąpił problem podczas logowania";
                            }
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                            btnLogin.setEnabled(true);
                        }
                    });


        } catch (Exception exc) {
            Log.e("WINKO", exc.getMessage());
        }
    }
}