package edu.northeastern.cs5520groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

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

        return view ;



    }
}
