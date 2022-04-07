package com.example.project_tu.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

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

public class QuizActivity extends AppCompatActivity {
    TextView tv_question;
    TextView b_answer1, b_answer2, b_answer3, b_answer4;
    List<QuizItem> questionItems;
    private int currentQuestion=0;
    private int correct =0, wrong=0;
    private int maxQuizNb=5;
    LinearLayout btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tv_question = findViewById(R.id.question);
        b_answer1 = findViewById(R.id.answer1);
        b_answer2 = findViewById(R.id.answer2);
        b_answer3 = findViewById(R.id.answer3);
        b_answer4 = findViewById(R.id.answer4);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        TextView counterTxt = findViewById(R.id.counterTxt);
        counterTxt.setTypeface(typeface);
        loadAllQuestions();
        //shuffer the questions
        Collections.shuffle(questionItems);
        setQuestionScreen(currentQuestion);

        //check if the answer is correct
        b_answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(btn1);
            }
        });
        b_answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(btn2);
            }
        });
        b_answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(btn3);
            }
        });
        b_answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(btn4);
            }
        });
    }
    //questionItems.get(currentQuestion).getAnswer4().equals(questionItems.get(currentQuestion).getCorrect())
    private LinearLayout getBtnCorrect(int currentQuestion){
        if(questionItems.get(currentQuestion).getAnswer1().equals(questionItems.get(currentQuestion).getCorrect())){
            return btn1;
        }
        else if(questionItems.get(currentQuestion).getAnswer2().equals(questionItems.get(currentQuestion).getCorrect())){
            return btn2;
        }
        else if(questionItems.get(currentQuestion).getAnswer3().equals(questionItems.get(currentQuestion).getCorrect())){
            return btn3;
        }
        else{
            return btn4;
        }
    }
    private void changeView(LinearLayout btn){
        this.b_answer1.setClickable(false);
        this.b_answer2.setClickable(false);
        this.b_answer3.setClickable(false);
        this.b_answer4.setClickable(false);
        if(btn== getBtnCorrect(currentQuestion)){
                correct++;
                btn.setBackground(getDrawable(R.drawable.btngreen));
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        btn.setBackground(getDrawable(R.drawable.bgquestion));
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 1000);
            }
            else{
                wrong++;
                btn.setBackground(getDrawable(R.drawable.btnred));
                getBtnCorrect(currentQuestion).setBackground(getDrawable(R.drawable.btngreen));
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        btn.setBackground(getDrawable(R.drawable.bgquestion));
                        getBtnCorrect(currentQuestion).setBackground(getDrawable(R.drawable.bgquestion));
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 1000);
            }
            //load next question if any
            //if(currentQuestion<questionItems.size()-1){
            Handler h2 =new Handler() ;
            h2.postDelayed(new Runnable() {
                public void run() {
                    if(currentQuestion<maxQuizNb-1){
                        currentQuestion++;
                        setQuestionScreen(currentQuestion);
                        TextView counterTxt=findViewById(R.id.counterTxt);//gotta search for it again, since the screen resets
                        counterTxt.setText(String.valueOf((currentQuestion+1)+"/"+maxQuizNb));
                        b_answer1.setClickable(true);
                        b_answer2.setClickable(true);
                        b_answer3.setClickable(true);
                        b_answer4.setClickable(true);
                    }
                    else{ //if no more question
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        Result res=new Result(correct,wrong);
                        intent.putExtra("res", (Parcelable) res);
                        intent.putExtra("activity", "quiz");
                        startActivity(intent);
                        finish();
                    }
                }

            }, 1000);
    }
    private void setQuestionScreen(int number){
        tv_question.setText(questionItems.get(number).getQuestion());
        b_answer1.setText(questionItems.get(number).getAnswer1());
        b_answer2.setText(questionItems.get(number).getAnswer2());
        b_answer3.setText(questionItems.get(number).getAnswer3());
        b_answer4.setText(questionItems.get(number).getAnswer4());
    }

    private void loadAllQuestions(){
        questionItems=new ArrayList<>();
        String jsonStr=loadJSONFromAsset("questions.json");
        try{
            JSONObject jsonObj= new JSONObject(jsonStr);
            JSONArray questions= jsonObj.getJSONArray("quiz");
            for(int i=0; i<questions.length();i++){
                JSONObject question=questions.getJSONObject(i);
                String questionString = question.getString("question");
                //shuffle answers
                ArrayList<String> answers = new ArrayList<String>();
                answers.add(question.getString("answer1"));
                answers.add(question.getString("answer2"));
                answers.add(question.getString("answer3"));
                answers.add(question.getString("answer4"));
                Collections.shuffle(answers);
                String answer1String=answers.get(0);
                String answer2String=answers.get(1);
                String answer3String=answers.get(2);
                String answer4String=answers.get(3);
                String correctString=question.getString("correct");

                questionItems.add(new QuizItem(questionString,answer1String,answer2String,answer3String,answer4String,correctString));
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

