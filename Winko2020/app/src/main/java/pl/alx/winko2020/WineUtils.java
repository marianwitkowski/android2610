package pl.alx.winko2020;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class WineUtils {

    private static JSONObject likedWines = new JSONObject(); //przechowuje informacje o polubionych winach

    public static void loadImage(Context ctx, ImageView imageView, String filename) {
        String url = Consts.API_IMAGE_BASE_URL + filename;

        GlideUrl urlBuilder = new GlideUrl(url,
                new LazyHeaders.Builder().addHeader("Authorization", "Basic c2VydmljZTphbGFtYWtvdGE=").build()
                );

        //url = "https://winezja.pl/zdjecia/drouhin-meursault_p_d.webp";
        Glide.with(ctx)
                .load(urlBuilder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.butelka_klubowe_zycie)
                .error(R.drawable.butelka_zaslepka)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void changeLike(ImageView imageView, Wine wine) {
        try {
            if (!likedWines.optBoolean(wine.getPimId(), false)) {
                likedWines.put(wine.getPimId(), true);
                imageView.setImageResource(R.drawable.like_active);
            } else {
                likedWines.put(wine.getPimId(), false);
                imageView.setImageResource(R.drawable.like_inactive);
            }
        } catch (Exception exc) {

        }
    }

    public static void setLikeState(ImageView imageView, Wine wine) {
        try {
            if (!likedWines.optBoolean(wine.getPimId(), false)) {
                imageView.setImageResource(R.drawable.like_inactive);
            } else {
                imageView.setImageResource(R.drawable.like_active);
            }
        } catch (Exception exc) {

        }
    }

    public static void loadLikes(String s)  {
        try {
            likedWines = new JSONObject(s);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static String getLikesAsString() {
        return likedWines.toString();
    }
}
