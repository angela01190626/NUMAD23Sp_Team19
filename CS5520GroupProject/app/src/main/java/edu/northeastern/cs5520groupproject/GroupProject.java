package edu.northeastern.cs5520groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.ImageAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.SpaceItemDecoration;


public class GroupProject extends AppCompatActivity {

    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_project);
        bottomNavigationView  = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 根据所选菜单项ID执行相应操作
            switch (item.getItemId()) {
                case R.id.navigation_me:
                    loadFragment(new ProfileFragment());
                    return true;

                case R.id.navigation_home:

                    loadFragment(new HomeFragment());
                    return true;
//
                case R.id.navigation_post:
                    // 在这里处理发布图标点击
                    loadFragment(new PostFragment());
                    return true;
//
//                case R.id.profile:
//                    // 在这里处理个人信息图标点击
//                    loadFragment(new ProfileFragment());
//                    return true;
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


}