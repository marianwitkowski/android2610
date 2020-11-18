package pl.alx.androidmodules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ImageMoveActivity extends AppCompatActivity {

    ViewGroup mainLayout;
    ImageView image;

    int xDelta, yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_move);

        mainLayout = findViewById(R.id.layout);
        image = findViewById(R.id.image);

        image.setOnTouchListener(onTouchListener());

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int)event.getRawX();
                int y = (int)event.getRawY();
                RelativeLayout.LayoutParams lParams;

                switch (event.getAction() & MotionEvent.ACTION_MASK ) {

                    case MotionEvent.ACTION_DOWN:
                        lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        Log.i("alx", String.format("Action down: %d - %d ", xDelta, yDelta));
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(ImageMoveActivity.this, "Palec w górę", Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        lParams.leftMargin = x - xDelta;
                        lParams.topMargin = y - yDelta;
                        lParams.bottomMargin = 0;
                        lParams.rightMargin = 0;
                        v.setLayoutParams(lParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }

}