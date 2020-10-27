package pl.alx.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        TextView t = findViewById(R.id.txtScroll);
        StringBuilder sb = new StringBuilder(1024);
        for (int i=1;i<=100;i++) {
            sb.append("Linia nr "+i+"\n");
        }
        t.setText(sb.toString());
    }
}