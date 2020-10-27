package pl.alx.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ThreadActivity extends AppCompatActivity {

    Button btnStart;
    ProgressBar progressBar;

    private class ProgresInc extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnStart.setEnabled(false);
            Log.i("HELLO-ALX", "Rozpoczynam wątek" );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnStart.setEnabled(true);
            Log.i("HELLO-ALX", "Kończę wątek" );
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=1;i<=10;i++) {
                progressBar.setProgress(i*10);
                Log.d("HELLO-ALX", "Jestem w pętli na i="+i);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("HELLO-ALX", e.getMessage(), e);
                }
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        btnStart = findViewById(R.id.btnStart);
        /*btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1;i<=10;i++) {
                    progressBar.setProgress(i*10);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        Button b = findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThreadActivity.this, "Wciśnięto przycisk", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //obsługa przycisku start
    private boolean isBusy = false;
    public void startProgress(View view) {
        if (isBusy) return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                isBusy = true;
                //btnStart.setEnabled(false);
                for(int i=1;i<=10;i++) {
                    progressBar.setProgress(i*10);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isBusy = false;
                //btnStart.setEnabled(true);
            }
        }).start();
    }

    public void startAsyncTask(View view) {
        new ProgresInc().execute();
    }
}