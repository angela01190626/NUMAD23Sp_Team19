package edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.Course;
import edu.northeastern.cs5520groupproject.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private int[] imageIds;
    private String[] usernameList; // new

    private List<imageItem> coachs = new ArrayList<>();


   /* public ImageAdapter(int[] imageIds, String[] usernameList) {
        this.imageIds = imageIds;
        this.usernameList = usernameList; // new

    }*/


    public ImageAdapter(List<imageItem> coachs) {
        this.coachs = coachs;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        imageItem coach = coachs.get(position);
        int targetWidth = holder.imageView.getWidth();
        int targetHeight = (int) (targetWidth * 1.5);
        Glide.with(holder.imageView.getContext())
                .load(coach.getImage())
                .override(targetWidth, targetHeight)
                .fitCenter()
                .into(holder.imageView);
        holder.usernameView.setText(coach.getUsername());

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeIcon.setImageResource(R.drawable.ic_liked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coachs.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView usernameView;

        ImageView likeIcon;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            usernameView = itemView.findViewById(R.id.userName_home);
            likeIcon = itemView.findViewById(R.id.like_icon);

        }
    }
}

