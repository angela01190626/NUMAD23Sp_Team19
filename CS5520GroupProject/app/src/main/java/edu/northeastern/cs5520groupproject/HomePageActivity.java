package edu.northeastern.cs5520groupproject;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.cs5520groupproject.friend.FriendAdapter;
import edu.northeastern.cs5520groupproject.login.User;
import edu.northeastern.cs5520groupproject.sticker.StickerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomePageActivity  extends AppCompatActivity {
    RecyclerView friendListRecyclerView;
    RecyclerView stickerListRecyclerView;
    List<User> friends = new ArrayList<>();
    List<Integer> stickers = new ArrayList<>(Arrays.asList(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4));
    public StickerAdapter stickerAdapter;
    public FriendAdapter friendAdapter;
    Button sendBtn;
    Button checkHistoryBtn;
    Button userStatBtn;
    private static String CLIENT_REGISTRATION_TOKEN;
    private static String SERVER_KEY;
    private DatabaseReference fireBase;

    private User currentUser;
    private String sender;
    private String receiver;
    private String date;
    public int message = -1;


protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    fireBase = FirebaseDatabase.getInstance().getReference().child("users");

    setContentView(R.layout.sticker_homepage_layout);

    // sticker list
    stickerListRecyclerView = findViewById(R.id.stickerList);
    stickerListRecyclerView.setHasFixedSize(true);
    stickerListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    stickerAdapter = new StickerAdapter(stickers, this);


    // friend list - firebase
    friendListRecyclerView = findViewById(R.id.friendList);
    friendListRecyclerView.setHasFixedSize(true);
    friendListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    fireBase.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            snapshot.getChildren().forEach(child -> {
                if (!child.getValue(User.class).getUserName().equals(currentUser.getUserName())) {
                    friends.add(child.getValue(User.class));
                }
            });

            friendAdapter = new FriendAdapter(friends, friendListRecyclerView.getContext());
            friendListRecyclerView.setAdapter(friendAdapter);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }

    });

}
}