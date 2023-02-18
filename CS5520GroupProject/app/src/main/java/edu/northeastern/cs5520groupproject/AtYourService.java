package edu.northeastern.cs5520groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.northeastern.cs5520groupproject.recyclerview.MovieAdapter;
import edu.northeastern.cs5520groupproject.recyclerview.MovieItem;
import edu.northeastern.cs5520groupproject.util.MovieRequest;


public class AtYourService extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MovieItem> movieList;
    private MovieAdapter movieAdapter;

    TextView loading;
    String result;

    List<Map<String, String>> response;
    ExecutorService executor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executor = Executors.newFixedThreadPool(10);
        setContentView(R.layout.activity_at_your_service);
        Bundle bundle = getIntent().getExtras();
        result = bundle.getString("KEY");
        Callable<List<Map<String, String>>> callable = new MovieRequest(result);
        Future<List<Map<String, String>>> future = executor.submit(callable);
        try {
            response = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d("ccc", response.toString());
        initView();
        initMovieData(response);
        //initData();
        initEvent();
        // loading = findViewById(R.id.loading);
        //callServiceThread callServiceThread = new callServiceThread();
        //new Thread(callServiceThread).start();
    }

//    class callServiceThread extends Thread {
//        @Override
//        public void run() {
//            try {
//                // Log.d("aaaa", result);
//                response = MovieRequest.searchMovieTitle(result);
//                Log.d("ccc", response.toString());
//                initView();
//                //initMovieData(response);
//                initData();
//                initEvent();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//    }

    private void initView() {
        recyclerView = findViewById(R.id.rlv);
    }

    private void initEvent() {
        // create movie Adapter and set adapter
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView = findViewById(R.id.rlv);
        recyclerView.setHasFixedSize(true);

        // set linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);

    }


//    private void initData(){
//        movieList = new ArrayList<>();
//        MovieItem movie1 = new MovieItem(image);
//        movie1.setName("Star War");
//        movie1.setName("Jan 01,2020");
//        //movie1.setImage(R.drawable.test1);
//
//
//        MovieItem movie2 = new MovieItem(image);
//        movie2.setName("Star WarII ");
//        movie2.setName("Feb 01,2022");
//        //movie2.setImage(R.drawable.test2);
//        movieList.add(movie1);
//        movieList.add(movie2);
//    }


    private void initMovieData(List<Map<String, String>> response) {
        movieList = new ArrayList<>();

        for (Map<String, String> i : response) {
            MovieItem n = new MovieItem();
            n.setName(i.get("Title"));
            n.setImage(i.get("Poster"));
            n.setReleaseDate(i.get("Year"));
            movieList.add(n);
        }
    }

        //        MovieItem movie1 = new MovieItem();
        //        movie1.setName("Star War");
        //        movie1.setName("Jan 01,2020");
        //        movie1.setImage(R.drawable.test1);
        //
        //
        //        MovieItem movie2 = new MovieItem();
        //        movie2.setName("Star WarII ");
        //        movie2.setName("Feb 01,2022");
        //        movie2.setImage(R.drawable.test2);
        //
        //
        //        MovieItem movie3 = new MovieItem();
        //        movie3.setName("阿凡达");
        //        movie3.setName("Jan 01,2023");
        //        movie3.setImage(R.drawable.test3);
        //
        //        MovieItem movie4 = new MovieItem();
        //        movie4.setName("阿凡达");
        //        movie4.setName("Jan 01,2023");
        //        movie4.setImage(R.drawable.test3);
        //
        //
        //        MovieItem movie5 = new MovieItem();
        //        movie5.setName("蝙蝠侠");
        //        movie5.setName("Jan 01,2023");
        //        movie5.setImage(R.drawable.test10);
        //
        //        MovieItem movie6 = new MovieItem();
        //        movie6.setName("蜘蛛侠");
        //        movie6.setName("Jan 01,2023");
        //        movie6.setImage(R.drawable.test6);
        //
        //        MovieItem movie7 = new MovieItem();
        //        movie7.setName("流浪地球");
        //        movie7.setName("Jan 01,2023");
        //        movie7.setImage(R.drawable.test7);
        //
        //        MovieItem movie8 = new MovieItem();
        //        movie8.setName("钢铁侠");
        //        movie8.setName("Jan 01,2023");
        //        movie8.setImage(R.drawable.test8);
        //
        //        movieList.add(movie1);
        //        movieList.add(movie2);
        //        movieList.add(movie3);
        //        movieList.add(movie4);
        //        movieList.add(movie6);
        //        movieList.add(movie5);
        //        movieList.add(movie7);
        //        movieList.add(movie8);

        //    public void onMovieSearchClick(View view) {
        //        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        //        loading.setText("Loading...");
        //        loading.setVisibility(View.INVISIBLE);
        //
        //        if (enterName.getText().toString().isEmpty()) {
        //            Snackbar.make(view, "Please enter a movie name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //            return;
        //        }
        //        loading.setVisibility(View.VISIBLE);
        //        callService();
        //    }

    //}

}