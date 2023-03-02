package edu.northeastern.cs5520groupproject.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import edu.northeastern.cs5520groupproject.R;

public class login_activity extends AppCompatActivity {

    private Button login;
    private EditText username;
    private String input;
    private DatabaseReference fireBase;

    private String user_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
//        input = username.getText().toString();
        fireBase = FirebaseDatabase.getInstance().getReference();


        // create addListenerEvent to login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = username.getText().toString();

                // ？确定具体的database的名字 和他的child是否有用户输入的名字 - input是用户输入的名字，作为关键词去查找是否在数据库中
                fireBase.child("users").child(input).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("d", dataSnapshot.toString());
                        if(dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            if(!Objects.equals(user.getToken(), user_token)) {
                                // set token？
                            }

                            // Intent intent = new Intent(login_activity.this, HomePageActivity.this); // update homepage的名字？
                            // startActivity(intent);
                            Log.e("d", dataSnapshot.toString());
                        } else {

                          User user = new User(input, user_token);

                          // how to add the user data into fireBase
                          fireBase.child("users").child(user.getUserName()).setValue(user);

                          // Intent intent = new Intent(login_activity.this, HomePageActivity.this); // update homepage的名字？
                          // startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }
}