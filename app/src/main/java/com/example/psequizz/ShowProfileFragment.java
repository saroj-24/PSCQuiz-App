package com.example.psequizz;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.psequizz.databinding.FragmentLeaderboardsBinding;
import com.example.psequizz.databinding.FragmentShowProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class ShowProfileFragment extends Fragment
{


    FragmentShowProfileBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String currentUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowProfileBinding.inflate(inflater,container,false);
        auth = FirebaseAuth.getInstance();
        ArrayList<user> arrayList = new ArrayList<>();
        fetchUserData();


        binding.signoutprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });

        return binding.getRoot();
    }

    private void fetchUserData() {
        DocumentReference userRef = db.collection("users").document(currentUserId);

        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        user currentUser = documentSnapshot.toObject(user.class);
                        if (currentUser != null) {
                            String name = currentUser.getName();
                            String email = currentUser.getEmail();
                            long coins = currentUser.getCoins();
                            String image = currentUser.getImage();

                            // Display user data in the UI
                            binding.profileName.setText(name);
                            binding.profileemail.setText(email);
                            binding.profilecoin.setText(String.valueOf(coins));
                            Glide.with(getContext())
                                    .load(image)
                                    .into(binding.profileimage);

                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ShowProfileFragment", "Error fetching user data: ", e);
                });
    }

}