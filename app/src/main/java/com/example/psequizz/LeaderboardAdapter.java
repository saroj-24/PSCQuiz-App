package com.example.psequizz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.psequizz.databinding.RowLeaderboardsBinding;

import java.util.ArrayList;

public class LeaderboardAdapter  extends  RecyclerView.Adapter<LeaderboardAdapter.leaderboardviewholder> {

     Context context;
    ArrayList<user> arrayListuser;
    public LeaderboardAdapter(Context context, ArrayList<user> arrayListuser) {
        this.context = context;
        this.arrayListuser = arrayListuser;
    }


    @NonNull
    @Override
    public leaderboardviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboards,parent,false);
        return new leaderboardviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull leaderboardviewholder holder, int position) {
           user newuser = arrayListuser.get(position);

           holder.binding.name.setText(newuser.getName());
           holder.binding.coins.setText(String.valueOf(newuser.getCoins()));
           holder.binding.index.setText(String.format("#%d",position+1));
           Glide.with(context)
                .load(newuser.getImage())
                .into(holder.binding.imageView7);
    }

    @Override
    public int getItemCount() {
        return arrayListuser.size();
    }

    public class leaderboardviewholder extends RecyclerView.ViewHolder{

        RowLeaderboardsBinding  binding;
        public leaderboardviewholder(@NonNull View itemView) {
            super(itemView);

            binding = RowLeaderboardsBinding.bind(itemView);
        }
    }
}
