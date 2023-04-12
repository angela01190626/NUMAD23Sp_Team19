package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class PlanViewHolder extends RecyclerView.ViewHolder {
    public TextView planItemTV;


    public PlanViewHolder(View itemView) {
        super(itemView);
        planItemTV = itemView.findViewById(R.id.planItem);
    }

    public void bind(PlanItem planItem) {
        planItemTV.setText(planItem.getItem());

    }
}

