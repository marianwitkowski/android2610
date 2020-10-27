package pl.alx.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView t = findViewById(R.id.textView);

        // odczyt danych z intenta
        t.setText( getIntent().getStringExtra("text") );
    }

    public void returnPhone(View view) {
        EditText et = findViewById(R.id.editTextPhone);
        String phone = et.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        setResult(RESULT_OK, intent);

        finish();
    }
}