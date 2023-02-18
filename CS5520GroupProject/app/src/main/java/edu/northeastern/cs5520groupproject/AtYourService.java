package edu.northeastern.cs5520groupproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.recyclerview.MovieAdapter;
import edu.northeastern.cs5520groupproject.recyclerview.MovieItem;


public class AtYourService extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MovieItem> movieList;
    private MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        initView();
        initMovieData();
        initEvent();
    }


    private void initView() {
        recyclerView = findViewById(R.id.rlv);
    }


    private void initEvent() {
        // create moiveAdapter and set adapter
        movieAdapter = new MovieAdapter( movieList,this);
        recyclerView.setAdapter(movieAdapter);
        // set linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void initMovieData() {
        movieList = new ArrayList<>();

        MovieItem movie1 = new MovieItem();
        movie1.setName("Star War");
        movie1.setName("Jan 01,2020");
        movie1.setImage(R.drawable.test1);


        MovieItem movie2 = new MovieItem();
        movie2.setName("Star WarII ");
        movie2.setName("Feb 01,2022");
        movie2.setImage(R.drawable.test2);


        MovieItem movie3 = new MovieItem();
        movie3.setName("阿凡达");
        movie3.setName("Jan 01,2023");
        movie3.setImage(R.drawable.test3);

        MovieItem movie4 = new MovieItem();
        movie4.setName("阿凡达");
        movie4.setName("Jan 01,2023");
        movie4.setImage(R.drawable.test3);


        MovieItem movie5 = new MovieItem();
        movie5.setName("蝙蝠侠");
        movie5.setName("Jan 01,2023");
        movie5.setImage(R.drawable.test10);

        MovieItem movie6 = new MovieItem();
        movie6.setName("蜘蛛侠");
        movie6.setName("Jan 01,2023");
        movie6.setImage(R.drawable.test6);

        MovieItem movie7 = new MovieItem();
        movie7.setName("流浪地球");
        movie7.setName("Jan 01,2023");
        movie7.setImage(R.drawable.test7);

        MovieItem movie8 = new MovieItem();
        movie8.setName("钢铁侠");
        movie8.setName("Jan 01,2023");
        movie8.setImage(R.drawable.test8);

        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);
        movieList.add(movie4);
        movieList.add(movie6);
        movieList.add(movie5);
        movieList.add(movie7);
        movieList.add(movie8);



    }



}