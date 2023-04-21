package edu.northeastern.cs5520groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.Chat;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.ChatAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.ChatHistory;
import edu.northeastern.cs5520groupproject.chat.chatMessageActivity;

public class ChatFragment extends Fragment {

    private List<Chat> chatList = new ArrayList<>();
    private List<Chat> chatListFiltered = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    // add authorization to get user - Fan
    private FirebaseAuth myAuth;
    private Button chat;
    private SearchView searchView;

    // find all user for friend
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("user_final");
        // query database for chatList
        queryDatabase();
    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_chat_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.chat_recycle_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 检查目前注册的用户 或者说 我自己的好朋友
        searchView = v.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0) {
                    chatListFiltered = chatAdapter.filter(newText);
                    chatAdapter = new ChatAdapter(chatListFiltered);
                    recyclerView.setAdapter(chatAdapter);
                }
                else {
                    queryDatabase();
                }
                return true;
            }
        });

        return v;
    }

    private void queryDatabase() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("FriendList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(currentUserId)) {
                        for (DataSnapshot user : userSnapshot.getChildren()) {
                            Chat chat = user.getValue(Chat.class);
                            chatList.add(chat);
                        }
                    }
                    else if (userSnapshot.getValue().toString().contains(currentUserId)) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_final")
                                .child(userSnapshot.getKey());
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Chat chat = dataSnapshot.getValue(Chat.class);
                                chatList.add(chat);
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle errors here
                            }
                        });
                    }
                }
                chatAdapter = new ChatAdapter(chatList);
                recyclerView.setAdapter(chatAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 错误处理
            }
        });
    }

}