package com.example.psequizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.psequizz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswer = getIntent().getIntExtra("correct",0);
        int totalquestion = getIntent().getIntExtra("total",0);

        long point = correctAnswer*POINTS;
        binding.scoretext.setText(String.format("%d/%d",correctAnswer,totalquestion));
        binding.earncoins.setText(String.valueOf(point));

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(point));


        binding.restartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ResultActivity.this,MainActivity.class);
               startActivity(intent);
               finish();
            }
        });
    }
}