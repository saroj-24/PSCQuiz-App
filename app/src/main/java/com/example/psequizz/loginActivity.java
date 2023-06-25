package com.example.psequizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.psequizz.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityLoginBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       auth = FirebaseAuth.getInstance();
       progress =  new ProgressDialog(this);
       progress.setTitle("Sing in  please wait....");

       if(auth.getCurrentUser() != null)
       {
           startActivity(new Intent(loginActivity.this,MainActivity.class));
           finish();
       }


       binding.loginbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String email;
               String password;

               email=  binding.emailBoxlogin.getText().toString();
               password =  binding.passwordBoxlogin.getText().toString();
               progress.show();
               auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       progress.dismiss();
                       startActivity(new Intent(loginActivity.this,MainActivity.class));
                       finish();

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       progress.dismiss();
                       Toast.makeText(loginActivity.this, "Sorry Check your email or password", Toast.LENGTH_SHORT).show();

                   }
               });

           }
       });

       binding.createNewaccountBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(loginActivity.this, SignupainActivity.class));
           }
       });

    }
}