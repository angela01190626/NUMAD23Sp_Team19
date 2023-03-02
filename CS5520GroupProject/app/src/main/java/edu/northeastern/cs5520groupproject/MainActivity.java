package edu.northeastern.cs5520groupproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.cs5520groupproject.login.login_activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.atyourservice);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MovieEnter.class);
                startActivity(intent1);
            }
        });

        Button btn2 = (Button) findViewById(R.id.firebase);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, login_activity.class);
                startActivity(intent1);
            }
        });

        Button btn3 = (Button) findViewById(R.id.groupproject);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, GroupProject.class);
                startActivity(intent1);
            }
        });


    }
}