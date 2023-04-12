package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.service.FireBaseReadService;

public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    DatabaseReference databaseRef;
    TextView profileEmail;
    TextView joinSince;
     RecyclerView recyclerView;
     List<PlanItem> planItems;
    PlanAdapter planAdapter;
    EditText etPlan;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String email = currentUser.getEmail();
        Date creationDate = new Date(currentUser.getMetadata().getCreationTimestamp());
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String strDate = dateFormat.format(creationDate);
        profileEmail= view.findViewById(R.id.profileEmail);
        profileEmail.setText("Email: "+email);
        joinSince= view.findViewById(R.id.joinDate);
        joinSince.setText("Join Since: "+strDate);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.planList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize plan items list and adapter
        planItems = new ArrayList<>();
        planAdapter = new PlanAdapter(planItems);
        recyclerView.setAdapter(planAdapter);
        databaseRef = FirebaseDatabase.getInstance().getReference("plan");
        databaseRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                planItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlanItem planItem = new PlanItem(snapshot.child("item").getValue(String.class));
                    planItems.add(planItem);
                }
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
        etPlan = view.findViewById(R.id.et_plan);

        Button btnAddPlan = view.findViewById(R.id.btn_add_plan);
        btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plan = etPlan.getText().toString();
                addPlanToDatabase(plan);
            }
        });


        return view ;

    }
    private void addPlanToDatabase(String title) {
        String uid = currentUser.getUid();
        String planItemId = databaseRef.push().getKey();
        PlanItem planItem = new PlanItem(title);
        databaseRef.child(uid).child(planItemId).setValue(planItem);
    }
}
