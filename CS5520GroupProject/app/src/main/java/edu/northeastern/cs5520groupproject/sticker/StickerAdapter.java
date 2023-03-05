package edu.northeastern.cs5520groupproject.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520groupproject.ClickListener;
import edu.northeastern.cs5520groupproject.R;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder>  {
    private final Context context;
    public List<Integer> stickers;
    public int row_index;

    private ClickListener listener;

    public StickerAdapter(List<Integer> stickers, Context context, ClickListener listener) {
        this.context = context;
        this.stickers=stickers;
        this.row_index=-1;
        this.listener = listener;
    }


    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerViewHolder(LayoutInflater.from(context).inflate(R.layout.sticker_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.sticker.setImageResource(stickers.get(position));
        holder.bindThisData(stickers.get(position));
        holder.itemView.setOnClickListener(view -> {
            row_index=position;
            notifyDataSetChanged();
        });

        if(row_index==position){
            holder.sticker.setBackgroundColor(Color.parseColor("lightgrey"));
        }
        else
        {
            holder.sticker.setBackgroundColor(Color.parseColor("white"));
        }
        holder.itemView.setOnClickListener(view -> listener.onStickerClicked(position));
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}
