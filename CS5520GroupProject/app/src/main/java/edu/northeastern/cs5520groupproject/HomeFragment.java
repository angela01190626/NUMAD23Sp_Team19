package edu.northeastern.cs5520groupproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.ImageAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView.SpaceItemDecoration;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter = new ImageAdapter(new int[]{R.drawable.sample1, R.drawable.sample2,
            R.drawable.sample3,R.drawable.sample4,R.drawable.sample5,R.drawable.sample6,R.drawable.sample7});

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.image_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(imageAdapter);
//
        int space = getResources().getDimensionPixelSize(R.dimen.item_space); // 从资源文件中获取间距大小
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        // Inflate the layout for this fragment
        return v;
    }
}
