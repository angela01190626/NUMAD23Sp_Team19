package edu.northeastern.cs5520groupproject.chat;

//import static edu.northeastern.cs5520groupproject.GroupProject.SIGN_IN_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.onesignal.OneSignal;
//import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.Chat;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView.ChatAdapter;
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

    private String notification;
    private String receiverId;
    private String receiver;
    //DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        receiver = getIntent().getStringExtra("receiver");
        receiverId = getIntent().getStringExtra("receiverId");
        // check if user is login

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser()
                    .getDisplayName(), Toast.LENGTH_LONG).show();
        }
        // load chat room
        //getAllMessages();
        displayChatMessages(receiverId);

        Button button =  findViewById(R.id.fab);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("chatHistory");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                Message message = new Message(input.getText().toString(), currentUser.getDisplayName() + "-" + receiver);
                String messageId = mDatabaseRef.child("userId")
                        .child(currentUser.getUid()).push().getKey();
                mDatabaseRef.child("userId").child(currentUser.getUid())
                        .child(messageId).setValue(message);
                mDatabaseRef.child("userId").child(currentUser.getUid()).child(messageId)
                        .child("receiver").setValue(receiver);

                Message receiverMessage = new Message(input.getText().toString(), currentUser.getDisplayName() + "-" + receiver);
                String receiverMessageId = mDatabaseRef.child("userId")
                        .child(receiverId).push().getKey();
                mDatabaseRef.child("userId").child(receiverId)
                        .child(receiverMessageId).setValue(receiverMessage);
                mDatabaseRef.child("userId").child(receiverId).child(receiverMessageId)
                        .child("sender").setValue(currentUser.getDisplayName());

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
        }else{
            Toast.makeText(this, "Can not sign in. Please try again", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void displayChatMessages(String receiverId){
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
                if (model.getUser().equals(currentUser.getDisplayName() + "-" + receiver)
                        || model.getUser().equals(receiver + "-" + currentUser.getDisplayName())) {
                    messageText.setText(model.getMessageTxt());

                    messageUser.setText(model.getUser().split("-")[0]);
                    messageTime.setText(DateFormat.format("MM-dd-yyyy (HH:mm:ss)", model.getTime()));
                }

            }
        };
        listMessage.setAdapter(adapter);
    }




}