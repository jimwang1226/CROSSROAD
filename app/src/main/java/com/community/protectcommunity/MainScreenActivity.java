package com.community.protectcommunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener, View.OnHoverListener{

    private Button start_btn;
    private Button more_btn;
    private MainScreenPopup popup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        //Set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Get Views and set up views
        start_btn = (Button) findViewById(R.id.start_btn);
        more_btn = (Button) findViewById(R.id.more_btn);
        start_btn.setOnClickListener(this);
        start_btn.setOnHoverListener(this);
        more_btn.setOnClickListener(this);
        more_btn.setOnHoverListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("failed");
                    }
                });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                System.out.println("click start");
                clearSharedPreference();
                goGameEnterScreen();
                break;
            case R.id.more_btn:
                System.out.println("click more");
                initPopupLayout();
                break;
            default:
                break;
        }
    }

    //clear the shared preference when click the start
    //because start means start a new game
    public void clearSharedPreference () {
        SharedPreferences sharedPref = this.getSharedPreferences("username_gender_choice", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.clear();
    }

    @Override
    public boolean onHover(View view, MotionEvent event) {
        return false;
    }

    public void initPopupLayout() {
        popup = new MainScreenPopup(MainScreenActivity.this);
        //popup.setBackground(getDrawable(R.drawable.mainscreen_popup_window_background));
        popup.showPopupWindow();
    }

    public void goGameEnterScreen(){
        Intent intent = new Intent(this, GameEnterActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
