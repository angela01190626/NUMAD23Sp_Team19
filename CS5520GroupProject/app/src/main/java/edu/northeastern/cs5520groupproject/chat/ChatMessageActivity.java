package edu.northeastern.cs5520groupproject.chat;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
//import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.Chat;
import edu.northeastern.cs5520groupproject.R;

public class ChatMessageActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    //private FirebaseAuth myAuth;
    private FirebaseListAdapter<Message> adapter;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseFriend;
    private FirebaseListAdapter<Message> mAdapter;
    private List<Message> messageList= new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private String notification;
    private String receiverId;
    private String receiver;
    //DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        mDatabaseFriend = FirebaseDatabase.getInstance().getReference("FriendList");

        receiver = getIntent().getStringExtra("receiver");
        receiverId = getIntent().getStringExtra("receiverId");
        // check if user is login

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser()
                    .getDisplayName(), Toast.LENGTH_LONG).show();
        }

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("chatHistory");

        displayChatMessages(receiver);
        Button button =  findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                Message message = new Message(input.getText().toString(), currentUser.getDisplayName() + "-" + receiver);
                String messageId = mDatabaseRef.child("userId")
                        .child(currentUser.getUid() + "|" + receiverId).push().getKey();
                mDatabaseRef.child("userId").child(currentUser.getUid() + "|" + receiverId)
                        .child(messageId).setValue(message);
                mDatabaseRef.child("userId").child(currentUser.getUid() + "|" + receiverId)
                        .child(messageId).child("receiver").setValue(receiver);

                Message receiverMessage = new Message(input.getText().toString(), currentUser.getDisplayName() + "-" + receiver);
                String receiverMessageId = mDatabaseRef.child("userId")
                        .child(receiverId + "|" + currentUser.getUid()).push().getKey();
                mDatabaseRef.child("userId").child(receiverId + "|" + currentUser.getUid())
                        .child(receiverMessageId).setValue(receiverMessage);
                mDatabaseRef.child("userId").child(receiverId + "|" + currentUser.getUid())
                        .child(receiverMessageId).child("sender").setValue(currentUser.getDisplayName());

                if (!mDatabaseFriend.child(currentUser.getUid()).toString().contains(receiverId)) {
                    mDatabaseFriend.child(currentUser.getUid()).child(currentUser.getUid() + "|" + receiverId)
                            .setValue(new Chat(receiverId, receiver));
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_final").child(receiverId);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            if (dataSnapshot.child("notificationKey").exists()) {
                                notification = dataSnapshot.child("notificationKey").getValue().toString();
                            } else {
                                notification = "";
                            }
                            new NotificationGroupProject(input.getText().toString(), "New message from: "
                                    + currentUser.getDisplayName(), notification, "", "");
                            input.setText("");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE) {
            Toast.makeText(this, "Successfully signed in!", Toast.LENGTH_SHORT).show();
            displayChatMessages(receiverId);
            System.out.println(receiverId);
        }else{
            Toast.makeText(this, "Can not sign in. Please try again", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void displayChatMessages(String receiver){
        ListView listMessage = (ListView) findViewById(R.id.list_of_messages);

        String userId = currentUser.getUid();
        String name = currentUser.getDisplayName();
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("chatHistory").child("userId");

        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.pe_message_list
                ,messageRef.child(userId + "|" + receiverId)) {
            @Override
            protected void populateView(View v, Message model, int position) {

                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // set text
                if (model.getUser().equals(name + "-" + receiver)
                        || model.getUser().equals(receiver + "-" + name)) {
                    messageText.setText(model.getMessageTxt());

                    messageUser.setText(model.getUser().split("-")[0]);
                    messageTime.setText(DateFormat.format("MM-dd-yyyy (HH:mm:ss)", model.getTime()));

                }

            }
        };
        listMessage.setAdapter(adapter);
        messageRef.child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    private void showDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatMessageActivity.this);
        builder.setMessage("You are not friends with " + receiver + ". Add them as a friend to start a chat?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Add the friend to the database and display chat messages
                        String friendId = mDatabaseFriend.child(currentUser.getUid()).push().getKey();
                        mDatabaseFriend.child(currentUser.getUid()).child(friendId)
                                .setValue(new Chat(receiverId, receiver));
                        displayChatMessages(receiverId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the dialogue
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }




}