package com.example.psequizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.psequizz.databinding.ActivityMainBinding;
import com.example.psequizz.databinding.ActivitySignupainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupainActivity extends AppCompatActivity {

    ActivitySignupainBinding binding;
    FirebaseAuth  auth;

    FirebaseFirestore firestore;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

         progressDialog =  new ProgressDialog(this);
         progressDialog.setTitle("Creating your account....");

        binding.createnewaccoutnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String email,password,name;

              email = binding.emailbox.getText().toString();
              password = binding.passwordbox.getText().toString();
              name = binding.nameBox.getText().toString();

              final user newuser = new user(name,email,password);
              progressDialog.show();
              auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){

                           String userID = task.getResult().getUser().getUid(); //to store user id
                           firestore.collection("users")
                                           .document(userID)
                                                   .set(newuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(SignupainActivity.this,MainActivity.class));
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(SignupainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                       }
                                   });
                            /// Toast.makeText(SignupainActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                           clear();
                       }
                       else {
                           progressDialog.dismiss();
                           Toast.makeText(SignupainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                       }
                           
                           
                  }
              });
            }
        });

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupainActivity.this,loginActivity.class));
            }
        });


    }
    public  void clear()
    {
        binding.emailbox.setText(" ");
        binding.nameBox.setText(" ");
        binding.passwordbox.setText("");
    }
}