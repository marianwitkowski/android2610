package com.example.listviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;

    private String[] users = {
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
            "Jan Kowalski", "Adam Nowak", "Jan Wróbel", "Kazimierz Nowacki",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvUsers);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, users[position], Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "LONG CLICK : " + users[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}