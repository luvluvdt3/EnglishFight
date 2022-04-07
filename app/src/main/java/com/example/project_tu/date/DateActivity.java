package com.example.project_tu.date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tu.MainActivity;
import com.example.project_tu.R;

public class DateActivity extends AppCompatActivity {
    private final String TAG = "Tu " + getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameUp, new FragmentGetValue()).commit();
        }
    }
}