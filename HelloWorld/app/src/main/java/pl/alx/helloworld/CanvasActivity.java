package pl.alx.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

public class CanvasActivity extends AppCompatActivity {

    class CanvasView extends View {

        Random rnd = new Random();
        int width;
        int height;
        public CanvasView(Context context) {
            super(context);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();
/*            paint.setStrokeWidth(10);
            paint.setARGB(0xFF, 0xFF, 0x00, 0x00 );
            canvas.drawLine( 10, 10, 50,100, paint);

            paint.setARGB(0xFF, 0xFF, 0xFF, 0x00 );
            canvas.drawLine( 50, 100, 450,400, paint);

            canvas.drawRect(300, 200, 900, 500, paint);

            paint.setARGB(0xFF, 0x0, 0xFF, 0x7F );
            canvas.drawCircle(500, 500, 90, paint);*/

            for(int i=0;i<1000;i++) {
                int x = rnd.nextInt(width);
                int y = rnd.nextInt(height);
                paint.setARGB(0xFF, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                canvas.drawCircle(x, y, 40, paint);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_canvas);
        setContentView(new CanvasView(this));
    }
}