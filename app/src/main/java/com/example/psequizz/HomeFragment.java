package com.example.psequizz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.psequizz.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {



    public HomeFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentHomeBinding binding;
    FirebaseFirestore firestoredb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        firestoredb = FirebaseFirestore.getInstance();
        ArrayList<CategoryModel> category  =  new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(getContext(),category);

        firestoredb.collection("Category")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        category.clear();
                        for(DocumentSnapshot snapshot : value.getDocuments())
                        {
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            model.setCategoryId(snapshot.getId());
                            category.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        binding.catergorylist.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.catergorylist.setAdapter(adapter);
        return binding.getRoot();
    }
}