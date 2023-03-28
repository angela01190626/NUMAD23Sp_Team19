package edu.northeastern.cs5520groupproject.PE_Circle.UI.HomePage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import edu.northeastern.cs5520groupproject.PE_Circle.UI.HomePage.databinding.FragmentHomeBinding;
import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private TextView welcomeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeText = view.findViewById(R.id.text_home);

        return view;
    }
}