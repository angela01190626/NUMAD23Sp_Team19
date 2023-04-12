package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> {
    private List<PlanItem> planItems;

    public PlanAdapter(List<PlanItem> planItems) {
        this.planItems = planItems;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item_layout,null);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        PlanItem planItem = planItems.get(position);
        holder.bind(planItem);
    }

    @Override
    public int getItemCount() {
        return planItems.size();

    }}