package edu.northeastern.cs5520groupproject;


import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import edu.northeastern.cs5520groupproject.login.User;
import edu.northeastern.cs5520groupproject.service.FireBaseReadService;

public class Count extends AppCompatActivity {

    private User currentUser;
    private FireBaseReadService fireBaseReadService;
    private DatabaseReference fireBaseCount;
    TextView userName;
    TextView count1;
    TextView count2;
    TextView count3;
    TextView count4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBaseReadService = new FireBaseReadService();
        fireBaseCount = FirebaseDatabase.getInstance().getReference().child("count");
        setContentView(R.layout.sticker_count_layout);
        userName =findViewById(R.id.userName);
        currentUser = new User(getIntent().getStringExtra("user"));
        // set username
        userName.setText(currentUser.getUserName());

        // set count for each sticker
        // 1. get
        count1 =findViewById(R.id.count1);
        count2 =findViewById(R.id.count2);
        count3 =findViewById(R.id.count3);
        count4 =findViewById(R.id.count4);
        // read from

        fireBaseCount.child(currentUser.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView[] arr = new TextView[]{count1, count2, count3, count4};
                for (int i = 0; i < 4; i++) {
                    if (snapshot.child(String.valueOf(i)).exists()) {
                        arr[i].setText(snapshot.child(String.valueOf(i)).getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Map<Integer, Integer> result = fireBaseReadService.readCountOfUser(currentUser.getUserName());
//        count1.setText(String.valueOf(result.get(0)));
//        count2.setText(String.valueOf(result.get(1)));
//        count3.setText(String.valueOf(result.get(2)));
//        count4.setText(String.valueOf(result.get(3)));
    }
}
