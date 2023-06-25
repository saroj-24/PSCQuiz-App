package com.example.psequizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class splashscreen extends AppCompatActivity {

    TextView textView;
    LottieAnimationView lottie;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        textView  =findViewById(R.id.boostext);
        lottie = findViewById(R.id.lottieAnimationView);

        textView.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottie.animate().setDuration(4000).setStartDelay(4000);

        handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashscreen.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}