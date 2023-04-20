package edu.northeastern.cs5520groupproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.CourseListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.YogaListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.ImageAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.SpaceItemDecoration;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageButton runButton;
    private ImageButton yogaButton;
    private ImageAdapter imageAdapter = new ImageAdapter(new int[]{R.drawable.sample1, R.drawable.sample2,
            R.drawable.sample3,R.drawable.sample4,R.drawable.sample5,R.drawable.sample6,R.drawable.sample7}
            ,new String[]{"Jacky","Alice","Joy","Nancy","Alex","Jojo","Fan"});

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.image_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(imageAdapter);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//
        int space = getResources().getDimensionPixelSize(R.dimen.item_space); // 从资源文件中获取间距大小
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        // Inflate the layout for this fragment

        runButton =  v.findViewById(R.id.running_button);
        yogaButton = v.findViewById(R.id.yoga_button);


        // add 点击事件
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Fragment 实例
                CourseListFragment courseListFragment = new CourseListFragment();

                // 将该 Fragment 添加到 Activity 中
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, courseListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        // add click event for yoga button
        yogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的 Fragment 实例
                YogaListFragment yogaCourseListFragment = new YogaListFragment();

                // 将该 Fragment 添加到 Activity 中
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, yogaCourseListFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });



        return v;
    }
}
