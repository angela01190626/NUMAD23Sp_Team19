package edu.northeastern.cs5520groupproject.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

import edu.northeastern.cs5520groupproject.R;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private Context context;
    private List<Purchase> purchaseList;
    private DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("courses");

    public PurchaseAdapter(Context context, List<Purchase> purchaseList) {
        this.context = context;
        this.purchaseList = purchaseList;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase purchase = purchaseList.get(position);
        coursesRef.child(purchase.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);

                if (course != null) {
                    Glide.with(context)
                            .load(course.getImage())
                            .placeholder(R.drawable.run)
                            .into(holder.courseImage);

                    holder.courseDescription.setText(course.getDescription());
                    holder.coachName.setText("Coach" + course.getCoachName());
                    holder.price.setText("original price: " + String.format(Locale.getDefault(), "%.2f", course.getPrice()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error here
            }
        });
        holder.finalPrice.setText(String.format(Locale.getDefault(), "%.2f", purchase.getPrice()));
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseDescription;
        TextView coachName;
        TextView price;
        TextView finalPrice;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            courseDescription = itemView.findViewById(R.id.courseDescription);
            coachName = itemView.findViewById(R.id.coachName);
            price = itemView.findViewById(R.id.price);
            finalPrice = itemView.findViewById(R.id.finalPrice);
        }
    }
}
