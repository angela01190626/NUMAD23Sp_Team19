package edu.northeastern.cs5520groupproject.sticker;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class StickerViewHolder extends RecyclerView.ViewHolder {
    public ImageView sticker;

    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.sticker = itemView.findViewById(R.id.sticker);
    }
}
