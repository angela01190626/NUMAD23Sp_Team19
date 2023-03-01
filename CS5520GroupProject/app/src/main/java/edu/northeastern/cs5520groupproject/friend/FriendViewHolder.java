package edu.northeastern.cs5520groupproject.friend;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class FriendViewHolder  extends RecyclerView.ViewHolder   {

    public TextView username;
    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);
        this.username=itemView.findViewById(R.id.username);
    }
}