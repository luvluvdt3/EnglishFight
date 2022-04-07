package com.example.project_tu.word;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tu.MainActivity;
import com.example.project_tu.R;
import com.example.project_tu.cours.CoursClause;
import com.example.project_tu.result.Result;
import com.example.project_tu.result.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//todo: customized toast
public class WordActivity extends AppCompatActivity {
    private int presCounter = 0;
    private int maxPresCounter ;
    private float correct =0, wrong=0;
    private int currentWord=0;
    private int maxPlay=5;
    List<WordItem> wordItems;
    TextView counterTxt, textQuestion;
    Animation smallbigforth;
    EditText editText;
    private LinearLayout.LayoutParams linearLayoutParams=new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.WRAP_CONTENT,
    LinearLayout.LayoutParams.WRAP_CONTENT
        );;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        loadAllWords();
        //shuffer the questions
        Collections.shuffle(wordItems);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        counterTxt=findViewById(R.id.counterTxt);
        counterTxt.setTypeface(typeface);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        setWordScreen(currentWord);
        LinearLayout viewParent = findViewById(R.id.layoutWords);
        editText=(EditText) findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewParent.removeAllViews();
                editText.setText("");
                presCounter=0;
                setWordScreen(currentWord);
            }
        });
    }

    private void setWordScreen(int number){
        counterTxt=findViewById(R.id.counterTxt);//gotta search for it again, since the screen resets
        counterTxt.setText(String.valueOf((number+1)+"/"+maxPlay));
        LinearLayout viewParent = findViewById(R.id.layoutWords);
        TextView txtQ=findViewById(R.id.textQuestion);
        WordItem w = wordItems.get(number);
        maxPresCounter=w.getSize()-1;
        txtQ.setText(w.getTextQuestion());
        viewParent.removeAllViews();

        for (int i=0; i< w.getSize();i++) {
            addView((LinearLayout)viewParent, w.getKey(i), ((EditText) findViewById(R.id.editText)));
        }
    }
    private void resetParam(){
        if(maxPresCounter-presCounter==5){
            this.linearLayoutParams.rightMargin = 2;
            this.linearLayoutParams.leftMargin = 2;
        }
        else if(maxPresCounter-presCounter==6){
            this.linearLayoutParams.rightMargin = -8;
            this.linearLayoutParams.leftMargin = -12;
        }
        else{ //=4 or less
            this.linearLayoutParams.leftMargin = 35;
        }
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        resetParam();
        final TextView textView = new TextView(this);
        textView.setLayoutParams(this.linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        textQuestion.setTypeface(typeface);

        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0) {
                        editText.setText("");
                    }
                    editText.setText(editText.getText().toString() + text);
                    //textView.startAnimation(smallbigforth);
                    //textView.animate().alpha(0).setDuration(300);
                    presCounter++;
                    if (presCounter == maxPresCounter)
                        doValidate();
                }
                textView.setVisibility(View.GONE);
                resetParam();
            }
        });
        viewParent.addView(textView);
    }
    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutWords);

        if(editText.getText().toString().equals(wordItems.get(currentWord).getTextAnswer())) {
            Toast toast= Toast.makeText(WordActivity.this, "Correct", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0); //pour Android 11+, gravity ne sera pas appliquée
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();

            correct++;
            if(currentWord<maxPlay-1){
                currentWord++;
                setWordScreen(currentWord);
                editText.setText("");
            }
            else{
                //Result=
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                Result res=new Result(correct,wrong);
                intent.putExtra("res", (Parcelable) res);
                intent.putExtra("activity", "word");
                startActivity(intent);
                finish();
            }

        }
        else {
            Toast toast= Toast.makeText(WordActivity.this, "Wrong! Correct answer: "+wordItems.get(currentWord).getTextAnswer(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();

            wrong++;
            if(currentWord<maxPlay-1){
                currentWord++;
                setWordScreen(currentWord);
                editText.setText("");
            }
            else{
                //Result=
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                Result res=new Result(correct,wrong);
                final Parcel parcelData  = Parcel.obtain();
                res.writeToParcel(parcelData, 0);
                parcelData.setDataPosition(0);
                intent.putExtra("res", (Parcelable) res);
                intent.putExtra("activity", "word");
                startActivity(intent);
                finish();
            }
        }

    }
    private void loadAllWords(){
        wordItems=new ArrayList<>();
        String jsonStr=loadJSONFromAsset("questions.json");

        try{
            JSONObject jsonObj= new JSONObject(jsonStr);
            JSONArray wordsJS= jsonObj.getJSONArray("words");
            for(int i=0; i<wordsJS.length();i++){
                JSONObject wJS=wordsJS.getJSONObject(i);
                //shuffle answers
                ArrayList<String> keys = new ArrayList<String>();

                keys.add(wJS.getString("key1"));
                keys.add(wJS.getString("key2"));
                keys.add(wJS.getString("key3"));
                keys.add(wJS.getString("key4"));
                keys.add(wJS.getString("key5"));

                if(wJS.has("key6")){
                    keys.add(wJS.getString("key6"));
                }
                if(wJS.has("key7")){
                    keys.add(wJS.getString("key7"));
                }
                Collections.shuffle(keys);
                String key1String=keys.get(0);
                String key2String=keys.get(1);
                String key3String=keys.get(2);
                String key4String=keys.get(3);
                String key5String=keys.get(4);
                String textAnswer = wJS.getString("textAnswer");
                String textQuestion = wJS.getString("textQuestion");
                if(keys.size()==5){
                    wordItems.add(new WordItem(key1String,key2String,key3String,key4String,key5String,textAnswer,textQuestion));
                }
                if(keys.size()==6){
                    String key6String=keys.get(5);
                    wordItems.add(new WordItem(key1String,key2String,key3String,key4String,key5String,key6String,textAnswer,textQuestion));
                }
                if(keys.size()==7){
                    String key6String=keys.get(5);
                    String key7String=keys.get(6);
                    wordItems.add(new WordItem(key1String,key2String,key3String,key4String,key5String,key6String,key7String,textAnswer,textQuestion));
                }

            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    private String loadJSONFromAsset(String file){
        String json="";
        try{
            InputStream is=getAssets().open(file);
            int size= is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return json;
    }
}
