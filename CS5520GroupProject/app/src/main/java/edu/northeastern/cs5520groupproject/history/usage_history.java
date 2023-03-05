package edu.northeastern.cs5520groupproject.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.login.User;
import edu.northeastern.cs5520groupproject.service.FireBaseReadService;

public class usage_history extends AppCompatActivity {

    TextView userName;
    private List<History> historyList = new ArrayList<>();
    private DatabaseReference fireBaseHistory;
    private User currentUser;
    RecyclerView historyRecyclerView;
    private UsageAdapter historyAdaptor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_history);
        userName = findViewById(R.id.userNamee);

        fireBaseHistory = FirebaseDatabase.getInstance().getReference().child("history");
        currentUser = new User(getIntent().getStringExtra("user"));

        // set username
        userName.setText("The sticker received history of " + currentUser.getUserName());

        initHistoryList();

        historyRecyclerView = findViewById(R.id.usageRecycleView);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdaptor = new UsageAdapter(historyList, this);
        historyRecyclerView.setAdapter(historyAdaptor);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(historyRecyclerView.getContext(), 1);
        historyRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void initHistoryList() {
        fireBaseHistory.child(currentUser.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot.getChildren().forEach(child->{
                    historyList.add(child.getValue(History.class));
                });
                Log.d("aaa",historyList.toString());
                historyAdaptor = new UsageAdapter(historyList, historyRecyclerView.getContext());
                historyRecyclerView.setAdapter(historyAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}