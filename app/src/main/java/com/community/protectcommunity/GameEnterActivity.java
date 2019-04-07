package com.community.protectcommunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class GameEnterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userGameName;
    Animation mShowAnimation;
    RelativeLayout chatBox;
    Button maleButton;
    Button femaleButton;
    Button returnButton;
    Button nextButton;
    RelativeLayout boyGirlChangeArea;
    SharedPreferences sharedPref;
    TextView chatboxContent;
    String gender = "MALE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OH_Whale.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_game_enter);
        //Set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Get Views and set up views
        chatBox = (RelativeLayout)findViewById(R.id.game_enter_chat_box);
        chatBox.setRotation(350.0f);
        maleButton = (Button)findViewById(R.id.male_button);
        femaleButton = (Button)findViewById(R.id.female_button);
        returnButton = (Button)findViewById(R.id.return_to_mainscreen_button);
        nextButton = (Button)findViewById(R.id.next_to_start_game_button);
        boyGirlChangeArea = (RelativeLayout)findViewById(R.id.boy_girl_change_area);
        userGameName = (EditText)findViewById(R.id.user_game_name);
        chatboxContent = (TextView)findViewById(R.id.game_enter_chat_box_content);

        userGameName.requestFocus();

        maleButton.setOnClickListener(this);
        femaleButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        AnimUtil.setShowAnimation(chatBox, 8000, mShowAnimation);
        //returnButton.setVisibility(View.INVISIBLE);
        //nextButton.setVisibility(View.INVISIBLE);

        sharedPref = this.getSharedPreferences("username_gender_choice", Context.MODE_PRIVATE);

    }



    @Override
    public void onClick(View view) {
        String username = null;
        switch (view.getId()) {
            case R.id.male_button:
                System.out.println("click male");
                Resources res = getResources(); //resource handle
                //change the picture of the character
                Drawable drawable = res.getDrawable(R.drawable.mainscreen_boy);
                boyGirlChangeArea.setBackground(drawable);
                gender = "MALE";
                //change the line in the chat box
                username = userGameName.getText().toString().trim();
                if (username.equals("")) {
                    chatboxContent.setText(R.string.game_enter_boy_line);
                    chatboxContent.setPadding(0,100,0,0);
                    AnimUtil.setShowAnimation(chatBox, 3000, mShowAnimation);
                }
                else {
                    chatboxContent.setText("Hi! " + username);
                    chatboxContent.setPadding(0,100,0,0);
                    AnimUtil.setShowAnimation(chatBox, 3000, mShowAnimation);
                }
                break;
            case R.id.female_button:
                res = getResources(); //resource handle
                //change the picture of the character
                drawable = res.getDrawable(R.drawable.game_enter_girl, null);
                boyGirlChangeArea.setBackground(drawable);
                gender = "FEMALE";
                //change the line in the chat box
                username = userGameName.getText().toString().trim();
                if (username.equals("")) {
                    chatboxContent.setText(R.string.game_enter_girl_line);
                    chatboxContent.setPadding(0,100,0,0);
                    AnimUtil.setShowAnimation(chatBox, 3000, mShowAnimation);
                }
                else {
                    chatboxContent.setText("Hi! " + username);
                    chatboxContent.setPadding(0,100,0,0);
                    AnimUtil.setShowAnimation(chatBox, 3000, mShowAnimation);
                }
                System.out.println("click female");
                break;
            case R.id.return_to_mainscreen_button:
                System.out.println("click return");
                getHome();
                break;
            case R.id.next_to_start_game_button:
                System.out.println("click next");
                //check validation, save username and gender
                saveUsernameGender();
                //go to game page
                getGame();
                break;
            default:
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    //save user's game name and gender to shared preference
    public void saveUsernameGender () {
        String username = userGameName.getText().toString().trim();
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.putString("username", username);
        spEditor.putString("gender", gender);
        spEditor.apply();
    }


    //go to game page
    public void getGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finish();
    }

    //go to main screen page
    public void getHome(){
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userGameName = null;
        mShowAnimation = null;
        chatBox = null;
        maleButton = null;
        femaleButton = null;
        returnButton = null;
        nextButton = null;
        boyGirlChangeArea = null;
        sharedPref = null;
        chatboxContent = null;
        gender = null;
        System.gc();
    }

}
