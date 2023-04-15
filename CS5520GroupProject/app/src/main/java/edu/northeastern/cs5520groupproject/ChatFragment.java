package edu.northeastern.cs5520groupproject;

import androidx.annotation.NonNull;
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
import edu.northeastern.cs5520groupproject.chat.chatMessageActivity;

public class ChatFragment extends Fragment {

    private List<Chat> chatList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    // add authorization to get user - Fan
    private FirebaseAuth myAuth;
    private Button chat;

    // find all user for friend
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_chat_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.chat_recycle_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 检查目前注册的用户 或者说 我自己的好朋友

        // databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("user_final");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = userSnapshot.getValue(Chat.class);
                    //String email = chat.getEmail();
                    //String name = chat.getName();
                    //String uid = chat.getUid();
                    //Chat newChat = new Chat(email,name,uid);
                    chatList.add(chat);
                }

                // 在这里处理和显示用户列表
                chatAdapter = new ChatAdapter(chatList);
                recyclerView.setAdapter(chatAdapter);
                // Inflate the layout for this fragment

                //                chat = (Button) v.findViewById(R.id.chat_btn);
                //
                //                chat.setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View view) {
                //                        Intent intent = new Intent(getActivity(), chatMessageActivity.class);
                //                        startActivity(intent);
                //                    }
                //                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 错误处理
            }
        });




      /*  //chatList.add(new Chat("13913001866@163.com", "Tom", R.drawable.cat1));
        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(chatAdapter);
        // Inflate the layout for this fragment

        chat = (Button) v.findViewById(R.id.chat_btn);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), chatMessageActivity.class);
                startActivity(intent);
            }
        });
*/

        return v;
    }



}