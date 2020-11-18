package pl.alx.winko2020;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends BaseAdapter {

    Context ctx;
    List<Wine> wineList = new ArrayList<>();

    public CatalogAdapter(Context ctx, List<Wine> wineList) {
        this.ctx = ctx;
        this.wineList = wineList;
    }

    public void addData(List<Wine> data) {
        wineList.addAll(data);
        notifyDataSetChanged();
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

    static class ViewHolder {
        private TextView tvName;
        private TextView tvCountry;
        private TextView tvColor;
        private TextView tvPrice;
        private ImageView imageView;
        private ImageView imageViewLike;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);

        ViewHolder holder;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.row_catalog, null);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvCountry = convertView.findViewById(R.id.tvCountry);
            holder.tvColor = convertView.findViewById(R.id.tvColor);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.imageViewLike = convertView.findViewById(R.id.imageViewLike);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Wine wine = wineList.get(position);

        holder.tvName.setText(wine.getName());
        holder.tvCountry.setText(wine.getCountry());
        holder.tvColor.setText(wine.getColor());
        holder.tvPrice.setText(String.format("%.2f zł", wine.getPrice()).replace(".",","));

        WineUtils.loadImage(ctx, holder.imageView, wine.getImage());

        WineUtils.setLikeState(holder.imageViewLike, wine);

        holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, "Like", Toast.LENGTH_SHORT).show();
                WineUtils.changeLike(holder.imageViewLike, wine);
                SharedPreferences sharedPreferences =  ctx.getSharedPreferences("winko2020", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("liked_wines", WineUtils.getLikesAsString());
                editor.commit();
            }
        });

        return convertView;
    }
}
