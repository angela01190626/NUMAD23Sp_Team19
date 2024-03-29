package edu.northeastern.cs5520groupproject.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.friend.FriendViewHolder;
import edu.northeastern.cs5520groupproject.login.User;
import edu.northeastern.cs5520groupproject.recyclerview.MovieItem;

public class UsageAdapter extends RecyclerView.Adapter<UsageViewHolder>{

    private List<History> historyList;
    private final Context context;

    public int row_index;

    public UsageAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
        row_index = -1;
    }

    @NonNull
    @Override
    public UsageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsageViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UsageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindThisData(historyList.get(position));
        holder.itemView.setOnClickListener(view -> {
            row_index=position;
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

}
