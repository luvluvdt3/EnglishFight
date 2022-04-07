package com.example.project_tu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.project_tu.cours.CoursClause;


public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_anim);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        ImageView photo2 = (ImageView) findViewById(R.id.photo2);
        ImageView photo1 = (ImageView) findViewById(R.id.photo1);
        final TextView titre = (TextView) findViewById(R.id.EnglishFight);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        titre.setTypeface(typeface);
        Animation down_to_up = AnimationUtils.loadAnimation(this, R.anim.down_to_up);
        Animation up_to_down = AnimationUtils.loadAnimation(this, R.anim.up_to_down);
        final Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        down_to_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                titre.startAnimation(fade_in);
                titre.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        photo2.startAnimation(down_to_up);
        photo1.startAnimation(up_to_down);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        thread.start();

    }
}
