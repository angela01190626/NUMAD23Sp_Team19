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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.Course;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.CourseListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.CycleListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.MyAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.TrainListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView.YogaListFragment;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.ImageAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.SpaceItemDecoration;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.imageItem;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageButton runButton;
    private ImageButton yogaButton;
    private ImageButton cycleButton;
    private ImageButton trainButton;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("coach");

    private List<imageItem> coachList = new ArrayList<>();
    /*private ImageAdapter imageAdapter = new ImageAdapter(new int[]{R.drawable.sample1, R.drawable.sample2,
            R.drawable.sample3,R.drawable.sample4,R.drawable.sample5,R.drawable.sample6,R.drawable.sample7}
            ,new String[]{"Jacky","Alice","Joy","Nancy","Alex","Jojo","Fan"});*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.image_recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setAdapter(imageAdapter);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//

        // Inflate the layout for this fragment

        runButton =  v.findViewById(R.id.running_button);
        yogaButton = v.findViewById(R.id.yoga_button);
        cycleButton = v.findViewById(R.id.cycling_button);
        trainButton = v.findViewById(R.id.strength_training_button);

        //uploadCoach(); only need upload one time
        readCoach();



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

        cycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Fragment 实例
                CycleListFragment cycleListFragment = new CycleListFragment();

                // 将该 Fragment 添加到 Activity 中
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, cycleListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        trainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Fragment 实例
                TrainListFragment trainListFragment = new TrainListFragment();

                // 将该 Fragment 添加到 Activity 中
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, trainListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return v;
    }


    public void uploadCoach(){


        imageItem coach1 = new imageItem(R.drawable.sample1,"Jacky");
        imageItem coach2 = new imageItem(R.drawable.sample2,"Jessica");
        imageItem coach3 = new imageItem(R.drawable.sample3,"David");
        imageItem coach4 = new imageItem(R.drawable.sample4,"Nancy");
        imageItem coach5 = new imageItem(R.drawable.sample5,"Jose");
        imageItem coach6 = new imageItem(R.drawable.sample6,"May");
        imageItem coach7 = new imageItem(R.drawable.sample7,"Ronaldo");
        imageItem coach8 = new imageItem(R.drawable.sample8,"Jordan");
        imageItem coach9 = new imageItem(R.drawable.sample9,"Ray");
        imageItem coach10 = new imageItem(R.drawable.sample10,"Zachary");

        database.push().setValue(coach1);
        database.push().setValue(coach2);
        database.push().setValue(coach3);
        database.push().setValue(coach4);
        database.push().setValue(coach5);
        database.push().setValue(coach6);
        database.push().setValue(coach7);
        database.push().setValue(coach8);
        database.push().setValue(coach9);
        database.push().setValue(coach10);

    }

    public void readCoach(){
        // 获取到运动名称节点的引用，例如 "run"
        DatabaseReference coachRef = database;

        // 添加值事件监听器
        coachRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 获取到子节点的值
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageItem coachOne = snapshot.getValue(imageItem.class);
                    coachList.add(coachOne);
                }

                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                ImageAdapter adapter = new ImageAdapter(coachList);
                recyclerView.setAdapter(adapter);
                int space = getResources().getDimensionPixelSize(R.dimen.item_space); // 从资源文件中获取间距大小
                recyclerView.addItemDecoration(new SpaceItemDecoration(space));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理错误
            }
        });


    }
}
