package edu.northeastern.cs5520groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.northeastern.cs5520groupproject.friend.FriendAdapter;
import edu.northeastern.cs5520groupproject.history.usage_history;
import edu.northeastern.cs5520groupproject.login.User;
import edu.northeastern.cs5520groupproject.service.FireBaseReadService;
import edu.northeastern.cs5520groupproject.service.FireBaseUpdateService;
import edu.northeastern.cs5520groupproject.service.SendMessageService;
import edu.northeastern.cs5520groupproject.sticker.StickerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.messaging.FirebaseMessaging;


public class HomePageActivity  extends AppCompatActivity {
    RecyclerView friendListRecyclerView;
    RecyclerView stickerListRecyclerView;
    List<User> friends = new ArrayList<>();
    List<Integer> stickers = new ArrayList<>(Arrays.asList(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4));
    public StickerAdapter stickerAdapter;
    public FriendAdapter friendAdapter;
    Button sendBtn;
    Button historyBtn;
    Button countBtn;
    private static String CLIENT_REGISTRATION_TOKEN;
    private static String SERVER_KEY;
    private DatabaseReference fireBase;
    private FireBaseUpdateService fireBaseUpdateService;
    private FireBaseReadService fireBaseReadService;
    private SendMessageService sendMessageService;

    private User currentUser;
    private int stickerToSend;
    private String receiver = "555";
    private String date;
    public int message = -1;


protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    setContentView(R.layout.sticker_homepage_layout);
    // buttons
    countBtn = findViewById(R.id.count);
    sendBtn = findViewById(R.id.send);
    historyBtn = findViewById(R.id.history);

    // services
    fireBaseUpdateService = new FireBaseUpdateService();
    fireBaseReadService = new FireBaseReadService();
    sendMessageService = new SendMessageService();

    fireBase = FirebaseDatabase.getInstance().getReference().child("users");
    Intent intent = getIntent();
    String username = intent.getStringExtra("user");
    currentUser = new User(username);

            // need debug for recycle view
    // sticker list
    stickerListRecyclerView = findViewById(R.id.stickerList);
    stickerListRecyclerView.setHasFixedSize(true);
    stickerListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    stickerAdapter = new StickerAdapter(stickers, this);
    stickerListRecyclerView.setAdapter(stickerAdapter);

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

    countBtn.setOnClickListener(view -> {
        Intent i = new Intent(HomePageActivity.this, Count.class);
        i.putExtra("user", currentUser.getUserName());
//        Map<Integer, Integer> result = fireBaseReadService.readCountOfUser(currentUser.getUserName());
//        i.putExtra("count1", String.valueOf(result.getOrDefault(0, 0)));
//        i.putExtra("count2", String.valueOf(result.getOrDefault(1, 0)));
//        i.putExtra("count3", String.valueOf(result.getOrDefault(2, 0)));
//        i.putExtra("count4", String.valueOf(result.getOrDefault(3, 0)));
        startActivity(i);
    });

    sendBtn.setOnClickListener(view -> {
        fireBaseUpdateService.update(currentUser.getUserName(), receiver, stickerToSend);
        //sendMessageService.sendMessageToDevice("fill target token here", currentUser.getUserName(), receiver, stickerToSend);
    });

    historyBtn.setOnClickListener(view -> {
        Intent i = new Intent(HomePageActivity.this, usage_history.class);
        i.putExtra("user", currentUser.getUserName());
//
        startActivity(i);
    });
}
}