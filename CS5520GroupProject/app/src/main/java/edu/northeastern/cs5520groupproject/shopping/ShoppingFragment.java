package edu.northeastern.cs5520groupproject.shopping;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class ShoppingFragment extends Fragment implements CourseAdapter.OnPurchaseButtonClickListener {
    private RecyclerView coursesRecyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        coursesRecyclerView = view.findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getActivity(), courseList, this);
        coursesRecyclerView.setAdapter(courseAdapter);

        fetchCourses();

        return view;
    }

    @Override
    public void onPurchaseButtonClick(Course course) {
        if (currentUser == null) {
            return;
        }

        fetchUserType(currentUser, userType -> {
            double price = course.getPrice();
            String message = "The price of the course is $" + price;

            if ("vip user".equals(userType)) {
                price = Math.round(price * 0.8 * 100) / 100.0;
                message = message + "\n\nYou are a VIP user! You get a 20% discount. Final price: " + price;
            }

            message = message + "\n\nDo you want to confirm the purchase?";

            double finalPrice = price;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm Purchase")
                    .setMessage(message)
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        recordPurchase(currentUser.getUid(), course.getId(), finalPrice);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void recordPurchase(String userId, String courseId, double price) {
        DatabaseReference purchasesRef = FirebaseDatabase.getInstance().getReference("purchases").child(userId);
        String purchaseId = purchasesRef.push().getKey();

        Purchase purchase = new Purchase(courseId, price);
        purchasesRef.child(purchaseId).setValue(purchase);
    }

    private void fetchCourses() {
        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("courses");
        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    courseList.add(course);
                }
                courseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error fetching courses: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserType(FirebaseUser currentUser, OnUserTypeFetchedListener listener) {
        DatabaseReference userTypeRef = FirebaseDatabase.getInstance().getReference("user_type").child(currentUser.getUid());
        userTypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.getValue(String.class);
                listener.onUserTypeFetched(userType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error fetching user type: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface OnUserTypeFetchedListener {
        void onUserTypeFetched(String userType);
    }
}
