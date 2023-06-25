package com.example.psequizz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.psequizz.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());// find the layout here
        setContentView(mainBinding.getRoot());//set the layout

        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();



        mainBinding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                     transaction.replace(R.id.content,new HomeFragment());
                     transaction.commit();
                        break;
                    case 1:
                       transaction.replace(R.id.content,new LeaderboardsFragment());
                       transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content,new WalletFragment());
                        transaction.commit();
                        break;
                    case 3:
                       transaction.replace(R.id.content,new ShowProfileFragment());
                       transaction.commit();
                        break;
                }
                return false;
            }
        });



   }

    }