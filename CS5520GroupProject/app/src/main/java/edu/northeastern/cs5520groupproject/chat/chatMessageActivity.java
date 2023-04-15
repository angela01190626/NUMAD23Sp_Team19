package edu.northeastern.cs5520groupproject.chat;

//import static edu.northeastern.cs5520groupproject.GroupProject.SIGN_IN_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
//import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;

public class chatMessageActivity extends AppCompatActivity {


    private static final int SIGN_IN_REQUEST_CODE = 1;
    //private FirebaseAuth myAuth;
    private FirebaseListAdapter<Message> adapter;

    private DatabaseReference mDatabaseRef;
    private FirebaseListAdapter<Message> mAdapter;
    private List<Message> messageList= new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    //DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        // check if user is login

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser()
                    .getDisplayName(), Toast.LENGTH_LONG).show();
        }
        // load chat room
        //getAllMessages();
        displayChatMessages();



        Button button =  findViewById(R.id.fab);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("chatHistory");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                Message message = new Message(input.getText().toString(),
                        currentUser.getDisplayName());
                //String messageId = databaseRef.push().getKey();
                mDatabaseRef.child("userId").child(currentUser.getUid()).push().setValue(message);

                //FirebaseDatabase.getInstance().getReference().push().setValue(
                //        new Message(input.getText().toString(),
                //                FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                // );
                // clear input
                input.setText("");

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE) {
            Toast.makeText(this, "Successfully signed in!", Toast.LENGTH_SHORT).show();
            displayChatMessages();
        }else{
            Toast.makeText(this, "Can not sign in. Please try again", Toast.LENGTH_SHORT).show();
            finish();
        }
    }





    private void displayChatMessages(){
        ListView listMessage = (ListView) findViewById(R.id.list_of_messages);

        String userId = currentUser.getUid();

        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("chatHistory").child("userId");


        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.pe_message_list
                ,messageRef.child(userId)) {
            @Override
            protected void populateView(View v, Message model, int position) {

                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // set text
                messageText.setText(model.getMessageTxt());
                messageUser.setText(model.getUser());

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTime()));

            }
        };
        listMessage.setAdapter(adapter);
    }




}