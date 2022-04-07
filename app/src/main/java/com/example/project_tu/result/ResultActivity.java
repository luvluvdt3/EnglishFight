package com.example.project_tu.result;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project_tu.MainActivity;
import com.example.project_tu.R;
import com.example.project_tu.cours.CoursClause;
import com.example.project_tu.quiz.QuizActivity;
import com.example.project_tu.word.WordActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    TextView resultTxt;
    ImageView stars;
    Button continueBtn, envoieSMS;
    TextView revenir;
    ImageView logo;
    String currentActivity;
    ToggleButton btnSMS;
    LinearLayout layoutSMS;
    EditText numSMS, messageSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        Toolbar toolbar=findViewById(R.id.toolBar);
        toolbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        currentActivity= getIntent().getStringExtra("activity");
        resultTxt= findViewById(R.id.resultTxt);
        stars=findViewById(R.id.stars);
        continueBtn=findViewById(R.id.btnContinue);
        revenir=findViewById(R.id.home);
        logo=findViewById(R.id.logo);
        btnSMS=findViewById(R.id.btnSMS);
        layoutSMS=findViewById(R.id.layoutSMS);
        numSMS=findViewById(R.id.numSMS);
        messageSMS=findViewById(R.id.messageSMS);
        envoieSMS=findViewById(R.id.envoieSMS);
        Animation smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        logo.startAnimation(smalltobig);
        stars.startAnimation(smalltobig);
        Result res = getIntent().getExtras().getParcelable("res");
        resultTxt.setText(res.getText());
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        resultTxt.setTypeface(typeface);
        stars.setImageResource(res.getImage());
        revenir.setTypeface(typeface);
        revenir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currentActivity.equals("word")){
                    Intent intent = new Intent(ResultActivity.this, WordActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(currentActivity.equals("quiz")){
                    Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btnSMS.setOnClickListener(v -> {
            if(btnSMS.isChecked()){
                layoutSMS.setVisibility(View.VISIBLE);
            }
            else{
                layoutSMS.setVisibility(View.GONE);
            }
        });

        envoieSMS.setOnClickListener(v -> {
            //hide keyboard
            View view = this.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            if(messageSMS.getText().toString().length()<1){
                Toast.makeText(ResultActivity.this,"Veuillez entrez votre nom",Toast.LENGTH_SHORT).show();
            }
            else if(!isNumTelephone(numSMS.getText().toString())){
                Toast.makeText(ResultActivity.this,"Pas de bon format de numéro téléphone",Toast.LENGTH_SHORT).show();
            }
            else{
                askPermissionAndSendSMS();
            }

        });
    }
    private boolean isNumTelephone(String num){
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(num);
        if (matcher.matches()) {
            return true;
        }
        else{
            return false;
        }
    }

    private void askPermissionAndSendSMS() {
        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager();
    }

    private void sendSMS_by_smsManager()  {
        String phoneNumber = this.numSMS.getText().toString();
        Result res = getIntent().getExtras().getParcelable("res");
        String message = this.messageSMS.getText().toString()+" a gagné "+res.getScore()+ "% dans English Fight" ;

        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    message,
                    null,
                    null);
            Toast.makeText(getApplicationContext(),"Message envoyé",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"ERROR: Message non envoyé" + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission donnée!", Toast.LENGTH_SHORT).show();

                    this.sendSMS_by_smsManager();
                }
                // Cancelled or denied.
                else {
                    Toast.makeText(this, "Permission refusé!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
