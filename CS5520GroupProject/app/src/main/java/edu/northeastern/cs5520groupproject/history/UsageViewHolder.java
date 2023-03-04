package edu.northeastern.cs5520groupproject.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class UsageViewHolder extends RecyclerView.ViewHolder {
    TextView sender;

    TextView sendTime;

    ImageView sticker;


    public UsageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.sender = itemView.findViewById(R.id.senderName);
        this.sendTime = itemView.findViewById(R.id.sendTime);
        this.sticker = itemView.findViewById(R.id.imageSticker);
    }

    public void bindThisData(History his) {
        int[] stickerList = new int[]{R.drawable.cat1, R.drawable.cat2,  R.drawable.cat3,  R.drawable.cat4};
        sender.setText(his.sender);
        sendTime.setText(his.date.substring(5, 16));
        sticker.setImageResource(stickerList[his.stickerId]);
    }
}
