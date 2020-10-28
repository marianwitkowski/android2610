package pl.alx.heroslist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicjalizacja listy
        heroList = new ArrayList<>();
        // dodanie danych do listy

        for (int i=0;i<10;i++) {
            heroList.add(new Hero(R.drawable.spiderman, "Spiderman", "Avengers"));
            heroList.add(new Hero(R.drawable.joker, "Joker", "Injustice League"));
            heroList.add(new Hero(R.drawable.ironman, "Ironman", "Avengers"));
            heroList.add(new Hero(R.drawable.doctorstrange, "Dr Strange", "Avengers"));
            heroList.add(new Hero(R.drawable.captainamerica, "Capitan America", "Avengers"));
            heroList.add(new Hero(R.drawable.batman, "Batman", "Justice League"));
        }

        HeroListAdapter adapter = new HeroListAdapter(this,
                R.layout.my_custom_list, heroList);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}