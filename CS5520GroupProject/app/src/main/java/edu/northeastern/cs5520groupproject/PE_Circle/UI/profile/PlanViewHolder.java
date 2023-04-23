package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520groupproject.R;

public class PlanViewHolder extends RecyclerView.ViewHolder {
    public TextView planItemTV;
    public Button deleteButton;
    public View spacerView;

    public PlanViewHolder(View itemView) {
        super(itemView);
        planItemTV = itemView.findViewById(R.id.planItem);
        deleteButton = itemView.findViewById(R.id.delete_plan_item_button);
        spacerView = itemView.findViewById(R.id.spacerView);
    }

    public void bind(PlanItem planItem, boolean showDeleteButton) {
        planItemTV.setText(planItem.getItem());

        if (showDeleteButton) {
            spacerView.setVisibility(View.VISIBLE);
        } else {
            spacerView.setVisibility(View.GONE);
        }
    }
}
