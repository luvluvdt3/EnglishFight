package com.example.project_tu;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tu.cours.CoursClause;
import com.example.project_tu.sections.Section;
import com.example.project_tu.sections.SectionAdapter;
import com.example.project_tu.sections.SectionList;

public class CourActivity extends AppCompatActivity implements SectionAdapter.SectionAdapterListener {
    ImageView purfection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final TextView titre = (TextView) findViewById(R.id.EnglishFight);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        titre.setTypeface(typeface);
        Animation smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        purfection = (ImageView) findViewById(R.id.purfection);
        purfection.startAnimation(smalltobig);
        SectionList listS= new SectionList();
        listS.construireListeCours();
        SectionAdapter adapter = new SectionAdapter(this,listS);
        ListView list =(ListView)findViewById(R.id.coursListView);
        list.setAdapter(adapter);
        adapter.addListener( this);
    }
    public void onClickNom(Section item, int position) {
        Intent intent = new Intent(CourActivity.this,item.getLinkToPage());
        startActivity(intent);
    }
}

