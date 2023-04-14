package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> {
    private List<PlanItem> planItems;
    public  DatabaseReference databaseRef;

    public PlanAdapter(List<PlanItem> planItems) {
        this.planItems = planItems;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_layout,null);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PlanItem planItem = planItems.get(position);
        holder.bind(planItem);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseRef = FirebaseDatabase.getInstance().getReference("plan");
                databaseRef.child(uid).child(planItem.getId()).removeValue();
                planItems.remove(position) ;
                notifyDataSetChanged();

                //

                Log.d(TAG,"deleted");
            }
        });
    }

    @Override
    public int getItemCount() {
        return planItems.size();

    }}