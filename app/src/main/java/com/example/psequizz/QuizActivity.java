package com.example.psequizz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psequizz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.R.id.*;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Question> arrayList;

    int index = 0;
    Question questions;

    CountDownTimer timer;

    FirebaseFirestore firestoreDB;
    int correctAnswers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayList = new ArrayList<>();
        firestoreDB = FirebaseFirestore.getInstance();

        final String catId = getIntent().getStringExtra("catId");

        Random random = new Random();
        final int rand = random.nextInt(12);

        firestoreDB.collection("Category")
                .document(catId)
                .collection("question")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        firestoreDB.collection("Category")
                                .document(catId)
                                .collection("question")
                                .whereLessThanOrEqualTo("index", rand)
                                .orderBy("index", Query.Direction.DESCENDING)
                                .limit(10)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots1 -> {
                                    for (DocumentSnapshot snapshot : queryDocumentSnapshots1) {
                                        Question question = snapshot.toObject(Question.class);
                                        arrayList.add(question);
                                    }
                                    nextQuestion();
                                });
                    } else {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            Question question = snapshot.toObject(Question.class);
                            arrayList.add(question);
                        }
                        nextQuestion();
                    }
                });



        countTimer();
        finishapp();

    }

    public void showAnswer()
    {
        if(questions.getAnswer().equals(binding.option1.getText().toString()))
        {
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(questions.getAnswer().equals(binding.option2.getText().toString()))
        {
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(questions.getAnswer().equals(binding.option3.getText().toString()))
        {
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(questions.getAnswer().equals(binding.option4.getText().toString()))
        {
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
    }

    public   void finishapp()
    {
        binding.quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void countTimer()
    {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timertext.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                  nextQuestion();
            }
        }.start();
    }
    void nextQuestion()
    {
         if(timer !=null)
          timer.cancel();
          timer.start();
        if(index <  arrayList.size())
        {
            binding.questioncounter.setText(String.format("%d/%d",(index+1),arrayList.size()));
            questions = arrayList.get(index);
            binding.question.setText(questions.getQuestion());
            binding.option1.setText(questions.getOption1());
            binding.option2.setText(questions.getOption2());
            binding.option3.setText(questions.getOption3());
            binding.option4.setText(questions.getOption4());
        }
    }


    public void resetOption()
    {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

   public void onClick(View view)
   {
       int viewId = view.getId();

       if (viewId == R.id.option1 || viewId == R.id.option2 || viewId == R.id.option3 || viewId == R.id.option4) {
           if (timer != null) {
               timer.cancel();
           }
           TextView selected = (TextView) view;
           checkAnswer(selected);
       } else if (viewId == R.id.nextbutton) {
           resetOption();
           if (index < arrayList.size()) {
               index++;
               nextQuestion();
           } else {
               Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
               intent.putExtra("correct",correctAnswers);
               intent.putExtra("total",arrayList.size());
               startActivity(intent);

           }
       }

    }


    public  void checkAnswer(TextView textView)
    {
        String selectedAnswer = textView.getText().toString();
        if (questions != null && selectedAnswer.equals(questions.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }


}