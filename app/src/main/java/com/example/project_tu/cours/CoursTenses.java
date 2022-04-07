package com.example.project_tu.cours;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tu.MainActivity;
import com.example.project_tu.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class CoursTenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cours_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursTenses.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_video);
//        getLifecycle().addObserver(youTubePlayerView);
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            String videoId = "du34F6p9rQ8";
//            @Override
//            public void onReady(YouTubePlayer youTubePlayer) {
//                youTubePlayer.cueVideo(videoId,0);
//            }
//
//        });

        TextView title= findViewById(R.id.titleCours);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        title.setTypeface(typeface);
        title.setText("Learn ALL 16 TENSES Easily in under 30 Minutes - Present, Past, Future, Conditional");
        WebView webview = (WebView) findViewById(R.id.webview);
        final ProgressDialog pd = ProgressDialog.show(CoursTenses.this, "", "\"Connexion en cours...", true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
                String webUrl = webview.getUrl();
            }

        });
        webview.loadUrl("https://www.myenglishpages.com/english/grammar-lesson-tenses.php");

    }
}
