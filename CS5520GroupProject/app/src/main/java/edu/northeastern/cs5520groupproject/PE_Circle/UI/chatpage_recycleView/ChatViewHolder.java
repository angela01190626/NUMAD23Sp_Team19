package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    TextView friendName;
    TextView lastMessage;
    ImageView sticker;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        this.friendName = itemView.findViewById(R.id.chat_personName);
        this.lastMessage = itemView.findViewById(R.id.chat_lastMessage);
        this.sticker = itemView.findViewById(R.id.chat_avatar);
    }

    public void bindThisData(Chat chat) {
        friendName.setText(chat.friend);
        lastMessage.setText(chat.lastMessage);
        sticker.setImageResource(R.drawable.cat1);
    }
}
