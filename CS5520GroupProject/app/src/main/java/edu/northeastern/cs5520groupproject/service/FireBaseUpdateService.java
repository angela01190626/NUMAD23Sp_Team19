package edu.northeastern.cs5520groupproject.service;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class FireBaseUpdateService {

    private DatabaseReference fireBaseCount;
    private DatabaseReference fireBaseHistory;

    public FireBaseUpdateService() {
        fireBaseCount = FirebaseDatabase.getInstance().getReference().child("count");
        fireBaseHistory = FirebaseDatabase.getInstance().getReference().child("history");
    }

    public void update(String fromUser, String toUser, int sticker) {
        // update count, the naive way
        fireBaseCount.child(fromUser).child(String.valueOf(sticker)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                Integer prevCount = s == null ? 0 : Integer.parseInt(s);
                fireBaseCount.child(fromUser)
                        .child(String.valueOf(sticker))
                        .setValue(String.valueOf(prevCount + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // update history
        fireBaseHistory.child(toUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                        .format(new java.util.Date());
                String key = toUser + "|" + timeStamp;
                fireBaseHistory.child(toUser).child(key).child("date").setValue(timeStamp);
                fireBaseHistory.child(toUser).child(key).child("sender").setValue(fromUser);
                fireBaseHistory.child(toUser).child(key).child("stickerId").setValue(sticker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
