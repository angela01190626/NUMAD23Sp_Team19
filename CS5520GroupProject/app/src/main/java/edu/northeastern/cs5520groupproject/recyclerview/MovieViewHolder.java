package edu.northeastern.cs5520groupproject.recyclerview;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    TextView movieTitle;
    TextView movieDate;
    ImageView moiveImage;
    RelativeLayout lContainer;

    String icon;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        this.movieTitle = itemView.findViewById(R.id.movie_title);
        this.movieDate = itemView.findViewById(R.id.movie_release_date);
        this.moiveImage = itemView.findViewById(R.id.movie_img);
        this.lContainer = itemView.findViewById(R.id.rl_item_container);
        // this.icon = itemView.findViewById(R.id.movie_icon);
    }
}
