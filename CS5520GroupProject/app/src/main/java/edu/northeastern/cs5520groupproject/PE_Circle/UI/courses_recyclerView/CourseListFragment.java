package edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;


public class CourseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Course> courses = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = view.findViewById(R.id.courseList_view);

        // 在这里创建课程列表
        createCourseList();

        // 设置 RecyclerView 的布局管理器和适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter adapter = new MyAdapter(courses);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void createCourseList() {
        // 创建课程列表
        // ...
        Course course1 = new Course("001","15-minute aerobic fat-burning run","Mins: 15", "120-180 cal","level: K1",
                R.drawable.run001);
        Course course2 = new Course("002","Music fat-burning run","Mins: 25", "195-293 cal","level: K2",
                R.drawable.run002);

        Course course3 = new Course("003","Boys' 1000m special training","Mins: 36", "301-451 cal","level: K2",
                R.drawable.run003);
        Course course4 = new Course("004","Music stress-relieving run","Mins: 25", "88-132 cal","level: K2",
                R.drawable.run004);

        Course course5 = new Course("005","Speed engine","Mins: 30", "310-464 cal","level: K3",
                R.drawable.run005);
        Course course6 = new Course("006","Advanced fat-burning HIIT run","Mins: 29", "286-428 cal","level: K3",
                R.drawable.run006);

        Course course7 = new Course("007","Intensive fat-burning HIIT run","Mins: 35", "407-611 cal","level: K3",
                R.drawable.run007);
        Course course8 = new Course("008","Easy run for beginners","Mins: 20", "144-216 cal","level: K1",
                R.drawable.run008);

        Course course9 = new Course("009","35-minute aerobic fat-burning run 01","Mins: 35", "297-445 cal","level: K2",
                R.drawable.run009);
        Course course10 = new Course("0010","Free run","Mins: 30", "250-320 cal","level: K2",
                R.drawable.run010);




        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        courses.add(course9);
        courses.add(course10);



    }
}