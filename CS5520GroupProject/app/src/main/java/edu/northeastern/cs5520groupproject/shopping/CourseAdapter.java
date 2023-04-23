package edu.northeastern.cs5520groupproject.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> courseList;
    private Context context;
    private final OnPurchaseButtonClickListener onPurchaseButtonClickListener;

    public CourseAdapter(Context context, List<Course> courseList, OnPurchaseButtonClickListener onPurchaseButtonClickListener) {
        this.context = context;
        this.courseList = courseList;
        this.onPurchaseButtonClickListener = onPurchaseButtonClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        final Course course = courseList.get(position);
        holder.courseDescription.setText(course.getDescription());
        holder.coachName.setText("Coach: " + course.getCoachName());
        holder.price.setText("Price: " + course.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(course.getImage())
                .placeholder(R.drawable.run)
                .into(holder.courseImage);

        holder.purchaseButton.setOnClickListener(v -> onPurchaseButtonClickListener.onPurchaseButtonClick(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    interface OnPurchaseButtonClickListener {
        void onPurchaseButtonClick(Course course);
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseDescription;
        TextView coachName;
        TextView price;
        Button purchaseButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            courseDescription = itemView.findViewById(R.id.courseDescription);
            coachName = itemView.findViewById(R.id.coachName);
            price = itemView.findViewById(R.id.price);
            purchaseButton = itemView.findViewById(R.id.purchaseButton);
        }
    }
}
