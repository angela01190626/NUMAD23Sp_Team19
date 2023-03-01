package edu.northeastern.cs5520groupproject.friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.login.User;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    private final List<User> friends;
    private final Context context;
    private int row_index ;

    public FriendAdapter(List<User> friends,Context context) {
        this.friends = friends;
        this.context=context;
        this.row_index=-1;
    }


    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_item_layout, null));    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.username.setText( friends.get(position).getUserName());
        holder.itemView.setOnClickListener(view -> {
            row_index=position;
            notifyDataSetChanged();
        });

        if(row_index==position){
            holder.username.setBackgroundColor(Color.parseColor("lightgrey"));
        }
        else
        {
            holder.username.setBackgroundColor(Color.parseColor("white"));
        }

    }



    @Override
    public int getItemCount() {
        return friends.size();
    }
}
