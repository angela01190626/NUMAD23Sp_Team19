package edu.northeastern.cs5520groupproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.profile.PlanAdapter;
import edu.northeastern.cs5520groupproject.PE_Circle.UI.profile.PlanItem;
import edu.northeastern.cs5520groupproject.chat.ChatMessageActivity;
import edu.northeastern.cs5520groupproject.post.Post;
import edu.northeastern.cs5520groupproject.post.PostAdapterInProfile;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_PICK = 1000;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    DatabaseReference databaseRef;
    TextView profileName;
    TextView profileEmail;
    TextView joinSince;
    List<PlanItem> planItems;
    PlanAdapter planAdapter;
    CircleImageView profileImage;
    //    Button updateImageBtn;
    DatabaseReference userRef;
    StorageReference storageReference;
    Uri filepath;
    StorageReference fileRef;
    ImageButton myPostsButton;
    ImageButton myPlansButton;
    RecyclerView recyclerViewMyPosts;
    RecyclerView recyclerViewMyPlans;
    private PostAdapterInProfile postAdapter;
    private List<Post> postList;
    private String publisherId;
    private String publisherName;
    ImageButton chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_new_in_activity);

        profileEmail = findViewById(R.id.profileEmail);
        joinSince = findViewById(R.id.joinDate);

        Bundle b = getIntent().getExtras();
        publisherId = "";
        if(b != null) publisherId = b.getString("publisherId");

        profileName = findViewById(R.id.profileName);
        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("user_final").child(publisherId).child("name");
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                if (name != null) {
                    publisherName = name;
                    profileName.setText("Name: " + name);
                } else {
                    profileEmail.setText("Unknown User");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching name: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        chatButton = findViewById(R.id.go_to_chat);
        if (Objects.equals(publisherId, currentUser.getUid())) {
            chatButton.setVisibility(View.GONE);
        }
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiver = publisherName;
                String receiverId = publisherId;
                Intent chatIntent = new Intent(view.getContext(), ChatMessageActivity.class);
                chatIntent.putExtra("receiver", receiver);
                chatIntent.putExtra("receiverId", receiverId);
                view.getContext().startActivity(chatIntent);
            }
        });

        DatabaseReference userEmailRef = FirebaseDatabase.getInstance().getReference("user_final").child(publisherId).child("email");
        userEmailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                if (email != null) {
                    profileEmail.setText("Email: " + email);
                } else {
                    profileEmail.setText("Email: Not available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching email: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        joinSince.setText("Join Since: 2023");

        profileImage = findViewById(R.id.profileImage);

        userRef= FirebaseDatabase.getInstance().getReference("userImage");

        storageReference= FirebaseStorage.getInstance().getReference("profile_images");
        fileRef = storageReference.child(publisherId + ".jpeg");

        myPostsButton = findViewById(R.id.myPosts);
        myPlansButton = findViewById(R.id.myPlans);
        recyclerViewMyPosts = findViewById(R.id.recyclerViewMyPosts);
        recyclerViewMyPlans = findViewById(R.id.recyclerViewMyPlans);
        recyclerViewMyPosts.setVisibility(View.VISIBLE);
        recyclerViewMyPlans.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);

        recyclerViewMyPlans.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapterInProfile(this , postList);
        recyclerViewMyPosts.setLayoutManager(linearLayoutManager1);
        recyclerViewMyPosts.setAdapter(postAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMyPosts.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewMyPosts.addItemDecoration(dividerItemDecoration);

        myPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMyPosts.setVisibility(View.VISIBLE);
                recyclerViewMyPlans.setVisibility(View.GONE);
            }
        });

        myPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMyPosts.setVisibility(View.GONE);
                recyclerViewMyPlans.setVisibility(View.VISIBLE);
            }
        });


        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide
                        .with(ProfileActivity.this)
                        .load(uri)
                        .placeholder(R.drawable.user_profile_default)
                        .into(profileImage);

            }
        });

        DatabaseReference userTypeRef = FirebaseDatabase.getInstance().getReference("user_type").child(publisherId);
        userTypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userType;
                if (dataSnapshot.exists()) {
                    userType = dataSnapshot.getValue(String.class);
                } else {
                    userType = "user";
                    userTypeRef.setValue(userType);
                }
                setBadge(userType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching user type: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewMyPlans.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));

        // Initialize plan items list and adapter
        planItems = new ArrayList<>();
        planAdapter = new PlanAdapter(planItems, false);
        recyclerViewMyPlans.setAdapter(planAdapter);
        databaseRef = FirebaseDatabase.getInstance().getReference("plan");

        databaseRef.child(publisherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                planItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlanItem planItem = new PlanItem(snapshot.child("item").getValue(String.class));
                    planItem.setId(snapshot.getKey());
                    planItems.add(planItem);
                }
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fetchPosts();
    }

    private void addPlanToDatabase(String title) {
        String uid = publisherId;
        String planItemId = databaseRef.push().getKey();
        PlanItem planItem = new PlanItem(planItemId,title);
        databaseRef.child(uid).child(planItemId).setValue(planItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK && intent != null) {
            try {
                InputStream inputStream = ProfileActivity.this.getContentResolver().openInputStream(intent.getData());
                File tempFile = File.createTempFile("temp_image", null, ProfileActivity.this.getCacheDir());
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();

                filepath = Uri.fromFile(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Glide.with(this).load(filepath).into(profileImage);
            if (filepath == null) {
                Toast.makeText(ProfileActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(ProfileActivity.this.getContentResolver().openInputStream(filepath));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Failed to decode image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            final StorageReference fileRef = storageReference.child(publisherId + ".jpeg");
            UploadTask uploadTask = fileRef.putBytes(imageData);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String uid=publisherId ;
                        Map<String, Object> profileImage = new HashMap<>();
                        profileImage.put("imageUrl", downloadUri.toString());

                        userRef.child(uid).setValue(profileImage);

                    }
                }
            });
        }
    }

    private void fetchPosts() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post.getPublisherUid().equals(publisherId)) {
                        postList.add(post);
                    }
                }
                // Notify your RecyclerView's adapter about the updated data
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error fetching posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBadge(String userType) {
        TextView badge = findViewById(R.id.badge);
        badge.setText(userType.toUpperCase());

        int backgroundResource;

        switch (userType) {
            case "vip user":
                backgroundResource = R.drawable.badge_background_vip;
                break;
            case "coach":
                backgroundResource = R.drawable.badge_background_coach;
                break;
            default:
                backgroundResource = R.drawable.badge_background_common;
                break;
        }

        badge.setBackgroundResource(backgroundResource);
    }
}
