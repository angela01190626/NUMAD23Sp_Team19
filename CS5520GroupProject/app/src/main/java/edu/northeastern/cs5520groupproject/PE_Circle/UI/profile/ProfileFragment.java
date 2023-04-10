package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.service.FireBaseReadService;

public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FireBaseReadService fireBaseReadService;
    DatabaseReference fireBaseCount;
    TextView profileEmail;
    TextView joinSince;

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

//        fireBaseReadService = new FireBaseReadService();
//        fireBaseCount = FirebaseDatabase.getInstance().getReference().child("plan");

        return view ;

    }
}
