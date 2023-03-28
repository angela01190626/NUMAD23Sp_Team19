package edu.northeastern.cs5520groupproject.PE_Circle.UI.HomePage;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import edu.northeastern.cs5520groupproject.PE_Circle.UI.HomePage.databinding.HomepageProjectBinding;
import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.databinding.HomepageProjectBinding;

public class PE_Homepage_activity extends AppCompatActivity {

    private HomepageProjectBinding binding;
    private Toolbar toolbar;
    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_project);

        /**
        binding = HomepageProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_homepage_project);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

         */







    }

}