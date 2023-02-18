package edu.northeastern.cs5520groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import edu.northeastern.cs5520groupproject.recyclerview.MovieAdapter;
import edu.northeastern.cs5520groupproject.recyclerview.MovieItem;

public class MovieEnter extends AppCompatActivity {

    EditText enterName;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_enter);

        enterName = findViewById(R.id.movie_input);
        btn = findViewById(R.id.get_movie);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MovieEnter.this, AtYourService.class);
                // intent1.putExtra("MY_KEY", enterName.getText());
                Bundle b = new Bundle();
                b.putString("KEY", String.valueOf(enterName.getText()));
                intent1.putExtras(b);

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
                // progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent1);
            }
        });
    }

}