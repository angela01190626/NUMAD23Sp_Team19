package edu.northeastern.cs5520groupproject.legacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import edu.northeastern.cs5520groupproject.AtYourService;
import edu.northeastern.cs5520groupproject.R;

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