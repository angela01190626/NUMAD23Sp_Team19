package edu.northeastern.cs5520groupproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.profile.ProfileFragment;
import edu.northeastern.cs5520groupproject.chat.chatMessageActivity;


public class GroupProject extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;
    private ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_project);
        signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user_final");
                        Map<String, Object> user = new HashMap<>();
                        user.put("uid", uid);
                        user.put("email", email);
                        user.put("name", name);
                        usersRef.child(uid).setValue(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this,
                                                    "Successfully signed in. Welcome!",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                    Load();
                                })
                                .addOnFailureListener(e -> {
                                    // Handle the error...
                                });
                    } else {
                        Toast.makeText(this,
                                        "We couldn't sign you in. Please try again later.",
                                        Toast.LENGTH_LONG)
                                .show();
                        // Close the app
                        finish();
                    }
                });

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
//            signInLauncher.launch(
//                    AuthUI.getInstance()
//                            .createSignInIntentBuilder()
//                            .build());
            Load();
        } else {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user_final");
            Map<String, Object> user = new HashMap<>();
            user.put("uid", uid);
            user.put("email", email);
            user.put("name", name);
            usersRef.child(uid).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this,
                                        "Welcome " + FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getDisplayName(),
                                        Toast.LENGTH_LONG)
                                .show();
                        Load();
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error...
                    });
        }
    }

    private void Load(){
        bottomNavigationView  = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // 根据所选菜单项ID执行相应操作
                switch (item.getItemId()) {
                    case R.id.navigation_me:
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            loadFragment(new ProfileFragment());
                        } else {
                            signInLauncher.launch(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .build());
                        }
                        return true;
                    case R.id.navigation_home:
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_message:
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            loadFragment(new ChatFragment());
                        } else {
                            signInLauncher.launch(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .build());
                        }
                        return true;
                    case R.id.navigation_post:
                        // 在这里处理发布图标点击
                        loadFragment(new PostFragment());
                        return true;
                }
                return false;
            }
        });
        // 默认加载首页Fragment
        loadFragment(new HomeFragment());
    }


    // 用于加载并显示传入的Fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

        //searchView = findViewById(R.id.home_search_view);
        //bottomNavigationView = findViewById(R.id.nav_view);
//        RecyclerView recyclerView = findViewById(R.id.image_recycler_view);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(new ImageAdapter(new int[]{R.drawable.sample1, R.drawable.sample2,
//                R.drawable.sample3,R.drawable.sample4,R.drawable.sample5,R.drawable.sample6,R.drawable.sample7}));
//
//        int space = getResources().getDimensionPixelSize(R.dimen.item_space); // 从资源文件中获取间距大小
//        recyclerView.addItemDecoration(new SpaceItemDecoration(space));



//    }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(GroupProject.this,
                                            "You have been signed out.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            // Close activity
                            finish();
                        }
                    });
        }
        return true;
    }
}