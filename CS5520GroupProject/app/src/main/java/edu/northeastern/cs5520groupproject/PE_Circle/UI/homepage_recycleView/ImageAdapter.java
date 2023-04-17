package edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private int[] imageIds;
    private String[] usernameList; // new


    public ImageAdapter(int[] imageIds, String[] usernameList) {
        this.imageIds = imageIds;
        this.usernameList = usernameList; // new

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageIds[position]);
        holder.usernameView.setText(usernameList[position]);

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeIcon.setImageResource(R.drawable.ic_liked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageIds.length;
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

