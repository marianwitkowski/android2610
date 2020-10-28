package pl.alx.heroslist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HeroListAdapter extends ArrayAdapter<Hero> {

    List<Hero> heroList; // dane dla adaptera
    Context ctx; // kontekst activity
    int resource; // ID layoutu z wierszem listy

    public HeroListAdapter(@NonNull Context context, int resource, @NonNull List<Hero> objects) {
        super(context, resource, objects);
        ctx = context;
        this.resource = resource;
        heroList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(ctx).inflate(resource, null, false );

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvTeam = view.findViewById(R.id.tvTeam);
        Button button = view.findViewById(R.id.btnDelete);

        Hero hero = heroList.get(position);
        tvName.setText(String.format("%d. %s", position+1, hero.getName() ) );
        tvTeam.setText(hero.getTeam());
        imageView.setImageResource(hero.getImage());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HEROLIST", "usuwam pozycję nr "+position);
                removeHero(position);
            }
        });

        return view;
    }

    private void removeHero(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Czy na pewno usunąć wiersz?");
        builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // usuwamy dane z listy
                heroList.remove(position);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
