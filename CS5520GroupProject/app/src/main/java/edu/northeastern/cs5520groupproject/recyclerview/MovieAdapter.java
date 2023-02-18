package edu.northeastern.cs5520groupproject.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import edu.northeastern.cs5520groupproject.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{

    private List<MovieItem> movieList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MovieAdapter(List<MovieItem> movieList, Context context) {
        this.movieList = movieList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.moive_item_layout, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem movieItem = movieList.get(position);
        holder.movieTitle.setText(movieItem.getName());
        holder.movieDate.setText(movieItem.getReleaseDate());
        holder.moiveImage = movieItem.getImage();

        Random random = new Random();
        int ran = random.nextInt(40)-10;
        holder.moiveImage.setLayoutParams(new RelativeLayout.LayoutParams(dp2px(100),dp2px(100+ran)));

        holder.lContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You select"+position,Toast.LENGTH_SHORT).show();
            }
        });


    }


    private int dp2px(int dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
