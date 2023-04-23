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

public class TrainListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Course> courses = new ArrayList<>();

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("homepage_courses_ex");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = view.findViewById(R.id.courseList_view);


        // only need upload onetime
//        uploadData();
        readData();

        return view;
    }


//    private void uploadData(){
//
//        DatabaseReference runRef = database.child("Train");
//
//        // 添加运动名称的数据
//        Course course1 = new Course("001","15-minute aerobic fat-burning dumbbell workout","Mins: 15", "120-180 cal","level: K1",
//                R.drawable.ex001,"Price: $21");
//        Course course2 = new Course("002","Full Body Dumbbell Blast","Mins: 30", "250-350 cal","level: K2",
//                R.drawable.ex002, "Price: $22");
//
//        Course course3 = new Course("003","Total Body Toning","Mins: 45", "350-450 cal","level: K3",
//                R.drawable.ex003,"Price: $33");
//        Course course4 = new Course("004","Upper Body Blast","Mins: 40", "300-400 cal","level: K2",
//                R.drawable.ex004,"Price: $15");
//
//        Course course5 = new Course("005","Lower Body Burn","Mins: 40", "300-400 cal","level: K2",
//                R.drawable.ex005,"Price: $6");
//        Course course6 = new Course("006","Full Body Fusion","Mins: 50", "400-500 cal","level: K3",
//                R.drawable.ex006,"Price: $19");
//
//        Course course7 = new Course("007","Core Crusher","Mins: 30", "250-350 cal","level: K1",
//                R.drawable.ex007,"Price: $23");
//        Course course8 = new Course("008","Total Body Transformation","Mins: 40", "500-600 cal","level: K5",
//                R.drawable.ex008,"Price: $17");
//
////        Course course9 = new Course("009","Relieve physical and mental fatigue","Mins: 11", "80-100 cal","level: K1",
////                R.drawable.ex009,"Price: $8");
////        Course course10 = new Course("0010","X-shaped legs/calf external rotation","Mins: 15", "120-180 cal","level: K2",
////                R.drawable.ex010,"Price: $9");
//
//
//        runRef.push().setValue(course1);
//        runRef.push().setValue(course2);
//        runRef.push().setValue(course3);
//        runRef.push().setValue(course4);
//        runRef.push().setValue(course5);
//        runRef.push().setValue(course6);
//        runRef.push().setValue(course7);
//        runRef.push().setValue(course8);
////        runRef.push().setValue(course9);
////        runRef.push().setValue(course10);
//
//    }

    private void readData(){
        // 获取到运动名称节点的引用，例如 "run"
        DatabaseReference runRef = database;

        // 添加值事件监听器
        runRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 获取到子节点的值
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Course train = snapshot.getValue(Course.class);
                    courses.add(train);
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
