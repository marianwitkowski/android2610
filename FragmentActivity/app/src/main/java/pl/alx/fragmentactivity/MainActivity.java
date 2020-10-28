package pl.alx.fragmentactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import pl.alx.fragmentactivity.fragments.CarDetailFragment;
import pl.alx.fragmentactivity.fragments.CarListFragment;

public class MainActivity extends AppCompatActivity implements CarListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarListFragment fragmentA = new CarListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState==null) {
            ft.add(R.id.flContainer, fragmentA);
        } else {
            ft.replace(R.id.flContainer, fragmentA);
        }
        ft.commit();

        //detekacja orientacji ekranu
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CarDetailFragment fragmentB = new CarDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            fragmentB.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.flContainer2, fragmentB)
                    .commit();
        }

    }

    @Override
    public void onCarItemSelected(int position) {
        Log.i("CARS", "wciśnięto indeks="+position);

        CarDetailFragment fragmentB = new CarDetailFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentB.setArguments(args);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, fragmentB)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, fragmentB)
                    .addToBackStack(null)
                    .commit();
        }
    }
}