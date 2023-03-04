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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import edu.northeastern.cs5520groupproject.HomePageActivity;
import edu.northeastern.cs5520groupproject.R;

public class login_activity extends AppCompatActivity {

    private Button login;
    private EditText username;
    private String input;
    private DatabaseReference fireBase;
    private static String CLIENT_REGISTRATION_TOKEN;
    private String user_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
//        input = username.getText().toString();
        fireBase = FirebaseDatabase.getInstance().getReference();

        // save token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(login_activity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (CLIENT_REGISTRATION_TOKEN == null) {
                        CLIENT_REGISTRATION_TOKEN = task.getResult();
                    }
                    Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
                    //Toast.makeText(LogIn.this, "CLIENT_REGISTRATION_TOKEN Existed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // create addListenerEvent to login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = username.getText().toString();

                fireBase.child("users").child(input).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("d", dataSnapshot.toString());
                        if(dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);

                            if(!Objects.equals(Objects.requireNonNull(user).token, CLIENT_REGISTRATION_TOKEN)) {
                                dataSnapshot.child("token").getRef().setValue(CLIENT_REGISTRATION_TOKEN);
                                user.token = CLIENT_REGISTRATION_TOKEN;
                            }

                            Intent intent = new Intent(login_activity.this, HomePageActivity.class); // update homepage的名字？
                            intent.putExtra("user", input);
                            startActivity(intent);
                            Log.e("d", dataSnapshot.toString());
                        } else {

                            User user = new User(input, CLIENT_REGISTRATION_TOKEN);

                            // add the user data into fireBase
                            fireBase.child("users").child(user.getUserName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), ("Unable to reset " + input + " !"), Toast.LENGTH_SHORT).show();
                                    } else {

                                        Intent i = new Intent(login_activity.this, HomePageActivity.class);
                                        i.putExtra("user", input);
                                        startActivity(i);
                                    }
                                }
                            });
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