package pl.alx.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    Context ctx;

    public final int REQUEST_SEC_ACT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        //pokazanie logo w title bar aplikacji
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        //pobieranie wskaźnika do widgeta w layout
        TextView t = findViewById(R.id.txtLabel);
        t.setText("Witaj na kursie");

        CheckBox cb = findViewById(R.id.checkBox);
        cb.setChecked(true);

        RadioButton rb = findViewById(R.id.radioButton1);
        rb.setChecked(true);

        editName = findViewById(R.id.editName);

        //obsługa zdarzenia klinknięcia przycisku
//        Button b = findViewById(R.id.btnOK);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // obsługa kliknięcia
//                String s = editName.getText().toString(); //to co user wpisał
//                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void clickButton(View view) {
        String s = editName.getText().toString(); //to co user wpisał
        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();

        //otwieranie kolejnego activity
        Intent intent = new Intent(ctx, SecondActivity.class);
        intent.putExtra("text", editName.getText().toString());

        //startActivity(intent);
        // uruchamianie activity na okoliczność informacji zwrotnej
        startActivityForResult(intent, REQUEST_SEC_ACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_SEC_ACT) {
            if (resultCode==RESULT_OK) {
                String s = data.getStringExtra("phone");
                editName.setText( s );
            }
        }
    }

    //podpinanie menu do activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //obsługa wyboru pozycji z menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.mnu1) {
            Toast.makeText(ctx, "menu 1", Toast.LENGTH_SHORT).show();
            //uruchomienie programu odpowiedzialnego za przeglądanie WWW
            Uri uri = Uri.parse("http://alx.pl");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (id==R.id.mnu2) {
            Toast.makeText(ctx, "menu 2", Toast.LENGTH_SHORT).show();
            shareIt();
        }
        if (id==R.id.mnu4) {
            Intent intent = new Intent(ctx, ScrollActivity.class);
            startActivity(intent);
        }
        if (id==R.id.mnu5) {
            Intent intent = new Intent(ctx, CanvasActivity.class);
            startActivity(intent);
        }
        if (id==R.id.mnu6) {
            Intent intent = new Intent(ctx, ThreadActivity.class);
            startActivity(intent);
        }
        return true;
    }

    /***
     * Metoda typu "Udostępnij" = "Share"
     */
    private void shareIt() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String subject = "Wiadomość";
        String body = "A to jest wiadomość z aplikacji Android";
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(i, "Udostępnij"));
    }
}