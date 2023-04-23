package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.chat.ChatMessageActivity;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<Chat> chatList;
    // private final Context context;
    private List<Chat> filteredChatList;
    public Context context;

    public int row_index;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
        this.filteredChatList = new ArrayList<>();
        row_index = -1;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Chat chat = chatList.get(position);
        holder.friendName.setText(chat.getName());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        StorageReference fileRef = storageReference.child(chat.getUid() + ".jpeg");
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide
                        .with(context)
                        .load(uri)
                        .placeholder(R.drawable.user_profile_default)
                        .into(holder.sticker);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.sticker.setImageResource(R.drawable.user_profile_default);
            }
        });


        holder.itemView.setOnClickListener(view -> {
            row_index = position;
            // 调用chatMessage活动
            Chat selectedChat = chatList.get(position);
            String receiver = selectedChat.getName();
            String receiverId = selectedChat.getUid();
            Intent chatIntent = new Intent(view.getContext(), ChatMessageActivity.class);
            System.out.println("receiver" + receiver);
            chatIntent.putExtra("receiver", receiver);
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

    @SuppressLint("NotifyDataSetChanged")
    public List<Chat> filter(String searchText) {
        filteredChatList.clear();

        if (searchText.length() == 0) {
            filteredChatList.addAll(chatList);
        } else {
            searchText = searchText.toLowerCase();
            for (Chat chat : chatList) {
                if (chat.getName() != null && chat.getName().toLowerCase(Locale.getDefault()).contains(searchText)) {
                    filteredChatList.add(chat);
                }
            }
        }

        notifyDataSetChanged();
        return filteredChatList;
    }


}
