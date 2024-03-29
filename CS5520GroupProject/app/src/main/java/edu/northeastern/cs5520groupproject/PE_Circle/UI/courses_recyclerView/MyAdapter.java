package edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.profile.PlanItem;
import edu.northeastern.cs5520groupproject.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Course> courses; // 这里假设您已经定义了一个 Course 类，用于存储课程信息

    public MyAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_course_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = courses.get(position);

        Glide.with(holder.imageView.getContext())
                .load(course.getImageUrl())
                .into(holder.imageView);

        holder.courseName.setText(course.getCourseName());
        holder.courseDuration.setText(course.getDuration());
        holder.calorieCount.setText(course.getCalorie());
        holder.level.setText(course.getLevel());
        holder.price.setText(course.getPrice());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("plan");
                String courseName=course.getCourseName();
                String planItemId = databaseRef.push().getKey();
                PlanItem planItem = new PlanItem(planItemId,courseName);

                databaseRef.child(uid).child(planItemId).setValue(planItem);
                Toast.makeText(v.getContext(), "Successfully added to your plan",
                                Toast.LENGTH_LONG)
                        .show();

                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView courseName;
        public TextView courseDuration;
        public TextView calorieCount;
        public TextView level;

        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            courseName = itemView.findViewById(R.id.course_name);
            courseDuration = itemView.findViewById(R.id.course_duration);
            calorieCount = itemView.findViewById(R.id.calorie_count);
            level = itemView.findViewById(R.id.level);
            price = itemView.findViewById(R.id.price);
        }
    }
}
