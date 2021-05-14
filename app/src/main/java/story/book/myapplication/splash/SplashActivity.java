package story.book.myapplication.splash;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import story.book.myapplication.MainActivity;
import story.book.myapplication.R;


public class SplashActivity extends AppCompatActivity {

    ImageView anim_cat_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        anim_cat_image_view = findViewById(R.id.animationlist_loading_cat);
        anim_cat_image_view.setBackgroundResource(R.drawable.animationlist_loading_cat);
        AnimationDrawable animationDrawable = (AnimationDrawable) anim_cat_image_view.getBackground();
        animationDrawable.start();
        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}