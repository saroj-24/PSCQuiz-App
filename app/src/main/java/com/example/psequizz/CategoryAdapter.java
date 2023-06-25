package com.example.psequizz;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter  extends  RecyclerView.Adapter<CategoryAdapter.myviewholder>{

   Context context;
   ArrayList<CategoryModel> categoryModels;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_caterogy,null);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
         CategoryModel model = categoryModels.get(position);

         holder.category.setText(model.getCategoryName());
         Glide.with(context).load(model.getCategoryImage())
                .placeholder(R.drawable.book) // Placeholder image to show while loading
                .error(R.drawable.ic_launcher_foreground) // Error image to show if loading fails
                .centerCrop()
                .into(holder.img);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent  = new Intent(context, QuizActivity.class);
                 intent.putExtra("catId",model.getCategoryId());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        private TextView category;
        private ImageView img;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.categorytext);
            img = itemView.findViewById(R.id.categoryimage);

        }
    }
}
