package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.chat.chatMessageActivity;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<Chat> chatList;
    // private final Context context;

    public int row_index;

    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
        row_index = -1;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(chatList.get(position));
        holder.itemView.setOnClickListener(view -> {
            row_index = position;
            // 调用chatMessage活动
            Chat selectedChat = chatList.get(position);
            String receiverId = selectedChat.getUid();
            Intent chatIntent = new Intent(view.getContext(), chatMessageActivity.class);
            chatIntent.putExtra("receiverId", receiverId);
            view.getContext().startActivity(chatIntent);
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

}
