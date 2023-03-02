package edu.northeastern.cs5520groupproject;


import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import edu.northeastern.cs5520groupproject.login.User;

public class Count extends AppCompatActivity {


    private User currentUser;

    TextView userName;
    TextView count1;
    TextView count2;
    TextView count3;
    TextView count4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_count_layout);
        currentUser = (User) getIntent().getSerializableExtra("user");
        userName.setText(currentUser.getUserName());
        // set username
        userName =findViewById(R.id.userName);

        // set count for each sticker
        // 1. get
        count1 =findViewById(R.id.count1);
        count2 =findViewById(R.id.count2);
        count3 =findViewById(R.id.count3);
        count4 =findViewById(R.id.count4);
        // set?

    }
}
