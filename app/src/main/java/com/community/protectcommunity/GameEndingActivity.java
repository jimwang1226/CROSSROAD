package com.community.protectcommunity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by jingewang on 28/3/19.
 */

public class GameEndingActivity extends Activity implements View.OnClickListener{
    //Execute when the page is created
    Button backToMainScreenButton;
    View score;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backToMainScreenButton = (Button)findViewById(R.id.return_to_mainscreen_button_ending_activity);
        score = findViewById(R.id.ending_score);

        //calculate the score from shared preference
        SharedPreferences sharedPref = getSharedPreferences("username_gender_choice", Context.MODE_PRIVATE);
        String question1 = sharedPref.getString("question1", null);
        String question2 = sharedPref.getString("question2", null);
        int finalScoreInt = 0;
        //I wrote it in a reverse order, so yes will plus 50 points
        if ("YES".equals(question1)) {
            finalScoreInt += 50;
        }
        if ("NO".equals(question2)) {
            finalScoreInt += 50;
        }

        System.out.println(finalScoreInt);
        Drawable drawable;
        Resources res = getResources();
        if(finalScoreInt == 100) {
            //set the score view background to 100
            drawable = res.getDrawable(R.drawable.number_100, null);
            score.setBackground(drawable);
        }
        if(finalScoreInt == 50) {
            //set the score view background to 50
            drawable = res.getDrawable(R.drawable.number_50, null);
            score.setBackground(drawable);
        }
        if(finalScoreInt == 0) {
            //set the score view background to 0
            drawable = res.getDrawable(R.drawable.number_0, null);
            score.setBackground(drawable);
        }

        //set up and start animation
        ObjectAnimator scoreViewAnimOn = AnimUtil.getAnimatorOn(score, this);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scoreViewAnimOn);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatorSet.start();
            }
        });
        backToMainScreenButton.setOnClickListener(this);
    }


    public void getHome(){
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_to_mainscreen_button_ending_activity:
                getHome();
                break;
            default:
                break;
        }

    }
}
