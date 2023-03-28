package edu.northeastern.cs5520groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.HomePage.ui.home.HomeFragment;

public class GroupProject extends AppCompatActivity {

    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_project);

        searchView = findViewById(R.id.home_search_view);
        bottomNavigationView = findViewById(R.id.nav_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 根据所选菜单项ID执行相应操作
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // 在这里处理首页图标点击
                        loadFragment(new HomeFragment());
                        return true;

                    //case R.id.messages:
                        // 在这里处理消息图标点击
                    //    loadFragment(new MessagesFragment());
                    //    return true;

                    //case R.id.post:
                        // 在这里处理发布图标点击
                    //    loadFragment(new PostFragment());
                    //    return true;

                    //case R.id.profile:
                        // 在这里处理个人信息图标点击
                    //    loadFragment(new ProfileFragment());
                    //    return true;
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
}