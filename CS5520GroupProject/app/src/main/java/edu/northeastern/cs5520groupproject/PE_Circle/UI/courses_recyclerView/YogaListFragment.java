package edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class YogaListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Course> courses = new ArrayList<>();

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("sports");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = view.findViewById(R.id.courseList_view);


        // only need upload onetime
        //uploadData();
        readData();

        return view;
    }


    private void uploadData(){

        DatabaseReference runRef = database.child("Yoga");

        // 添加运动名称的数据
        Course course1 = new Course("001","Flexibility improvement, full-body stretching","Mins: 33", "120-180 cal","level: K3",
                R.drawable.yoga001,"Price: $20");
        Course course2 = new Course("002","Hot yoga, full-body stretching","Mins: 29", "195-293 cal","level: K2",
                R.drawable.yoga002, "Price: $12");

        Course course3 = new Course("003","Full-body stretching, practiced on bed","Mins: 25", "201-300 cal","level: K2",
                R.drawable.yoga003,"Price: $30");
        Course course4 = new Course("004","Basic strength","Mins: 25", "88-132 cal","level: K2",
                R.drawable.yoga004,"Price: $10");

        Course course5 = new Course("005","Balance training","Mins: 20", "210-301 cal","level: K2",
                R.drawable.yoga005,"Price: $5");
        Course course6 = new Course("006","Lower body flexibility","Mins: 26", "286-328 cal","level: K2",
                R.drawable.yoga006,"Price: $10");

        Course course7 = new Course("007","Beginner waist and abdomen training","Mins: 23", "150-200 cal","level: K2",
                R.drawable.yoga007,"Price: $5");
        Course course8 = new Course("008","Beginner full-body workout","Mins: 28", "144-216 cal","level: K1",
                R.drawable.yoga008,"Price: $10");

        Course course9 = new Course("009","Relieve physical and mental fatigue","Mins: 11", "80-100 cal","level: K1",
                R.drawable.yoga009,"Price: $8");
        Course course10 = new Course("0010","X-shaped legs/calf external rotation","Mins: 15", "120-180 cal","level: K2",
                R.drawable.yoga010,"Price: $9");


        runRef.push().setValue(course1);
        runRef.push().setValue(course2);
        runRef.push().setValue(course3);
        runRef.push().setValue(course4);
        runRef.push().setValue(course5);
        runRef.push().setValue(course6);
        runRef.push().setValue(course7);
        runRef.push().setValue(course8);
        runRef.push().setValue(course9);
        runRef.push().setValue(course10);

    }

    private void readData(){
        // 获取到运动名称节点的引用，例如 "run"
        DatabaseReference runRef = database.child("Yoga");

        // 添加值事件监听器
        runRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 获取到子节点的值
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Course yoga = snapshot.getValue(Course.class);
                    courses.add(yoga);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                MyAdapter adapter = new MyAdapter(courses);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理错误
            }
        });



    }
}
