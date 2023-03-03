package edu.northeastern.cs5520groupproject.service;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBaseReadService {

    private DatabaseReference fireBaseCount;
    private DatabaseReference fireBaseHistory;

    public FireBaseReadService() {
        fireBaseCount = FirebaseDatabase.getInstance().getReference().child("count");
        fireBaseHistory = FirebaseDatabase.getInstance().getReference().child("history");
    }


    /**
     * @return key is the sticker id, value is the count number that it's been send
     * */
    public Map<Integer, Integer> readCountOfUser(String user) {
        Map<Integer, Integer> res = new HashMap<>();
        fireBaseCount.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<String> temp = (List<String>) snapshot.getValue();
                    for (int i = 0; i < temp.size(); i++) {
                        res.put(i, Integer.parseInt(temp.get(i)));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return res;
    }

    List<String[]> readHistoryOfUser(String user) {
        List<String[]> res = new ArrayList<>();
        fireBaseHistory.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss: snapshot.getChildren()) {
                    // array contains: date, sender, sticker id
                    String[] temp = new String[3];
                    temp[0] = ss.child("date").getValue(String.class);
                    temp[1] = ss.child("sender").getValue(String.class);
                    temp[2] = ss.child("stickerId").getValue(String.class);
                    res.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return res;
    }
}
