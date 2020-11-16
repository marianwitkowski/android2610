package pl.alx.winko2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends BaseAdapter {

    Context ctx;
    List<Wine> wineList = new ArrayList<>();

    public CatalogAdapter(Context ctx, List<Wine> wineList) {
        this.ctx = ctx;
        this.wineList = wineList;
    }

    @Override
    public int getCount() {
        return wineList.size();
    }

    @Override
    public Object getItem(int position) {
        return wineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.row_catalog, null);

        Wine wine = wineList.get(position);
        TextView tv = view.findViewById(R.id.tvName);
        tv.setText(wine.getName());

        tv = view.findViewById(R.id.tvCountry);
        tv.setText(wine.getCountry());

        tv = view.findViewById(R.id.tvColor);
        tv.setText(wine.getColor());

        tv = view.findViewById(R.id.tvPrice);
        tv.setText(String.format("%.2f zł", wine.getPrice()).replace(".",","));

        return view;
    }
}