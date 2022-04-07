package com.example.project_tu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.project_tu.cours.CoursClause;
import com.example.project_tu.sections.Section;
import com.example.project_tu.sections.SectionAdapter;
import com.example.project_tu.sections.SectionList;

public class MainActivity extends AppCompatActivity implements SectionAdapter.SectionAdapterListener {
    ImageView purfection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        Animation smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        purfection = (ImageView) findViewById(R.id.purfection);
        purfection.startAnimation(smalltobig);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        TextView titre=findViewById(R.id.EnglishFight);
        titre.setTypeface(typeface);
        SectionList listS= new SectionList();
        listS.construireListeMenu();

        SectionAdapter adapter = new SectionAdapter(this,listS);
        ListView list =(ListView)findViewById(R.id.subjectListView);
        list.setAdapter(adapter);
        adapter.addListener( this);
    }
    public void onClickNom(Section item, int position) {
        Intent intent = new Intent(MainActivity.this,item.getLinkToPage());
        startActivity(intent);
    }


}