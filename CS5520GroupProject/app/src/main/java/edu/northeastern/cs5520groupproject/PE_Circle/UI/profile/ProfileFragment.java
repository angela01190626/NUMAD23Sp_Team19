package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import de.hdodenhof.circleimageview.CircleImageView;

import edu.northeastern.cs5520groupproject.AddPostActivity;
import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.post.Post;
import edu.northeastern.cs5520groupproject.post.PostAdapter;
import edu.northeastern.cs5520groupproject.post.PostAdapterInProfile;


public class ProfileFragment extends Fragment {
    private static final int REQUEST_CODE_IMAGE_PICK = 1000;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    DatabaseReference databaseRef;
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
    Button addPlanButton;
    private PostAdapterInProfile postAdapter;
    private List<Post> postList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_new, container, false);
        String email = currentUser.getEmail();
        Date creationDate = new Date(currentUser.getMetadata().getCreationTimestamp());
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String strDate = dateFormat.format(creationDate);

        profileImage = view.findViewById(R.id.profileImage);

        userRef= FirebaseDatabase.getInstance().getReference("userImage");

        storageReference= FirebaseStorage.getInstance().getReference("profile_images");
        fileRef= storageReference.child(currentUser.getUid() + ".jpeg");

        myPostsButton = view.findViewById(R.id.myPosts);
        myPlansButton = view.findViewById(R.id.myPlans);
        recyclerViewMyPosts = view.findViewById(R.id.recyclerViewMyPosts);
        recyclerViewMyPlans = view.findViewById(R.id.recyclerViewMyPlans);
        addPlanButton = view.findViewById(R.id.addPlanButton);
        recyclerViewMyPosts.setVisibility(View.VISIBLE);
        recyclerViewMyPlans.setVisibility(View.GONE);
        addPlanButton.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);

        recyclerViewMyPlans.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapterInProfile(getContext() , postList);
        recyclerViewMyPosts.setLayoutManager(linearLayoutManager1);
        recyclerViewMyPosts.setAdapter(postAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMyPosts.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewMyPosts.addItemDecoration(dividerItemDecoration);

        profileEmail= view.findViewById(R.id.profileEmail);
        profileEmail.setText("Email: "+email);
        joinSince= view.findViewById(R.id.joinDate);
        joinSince.setText("Join Since: "+strDate);

        myPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMyPosts.setVisibility(View.VISIBLE);
                recyclerViewMyPlans.setVisibility(View.GONE);
                addPlanButton.setVisibility(View.GONE);
            }
        });

        myPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMyPosts.setVisibility(View.GONE);
                recyclerViewMyPlans.setVisibility(View.VISIBLE);
                addPlanButton.setVisibility(View.VISIBLE);
            }
        });

        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPlanDialog(getContext());
            }
        });


        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide
                        .with(getActivity())
                        .load(uri)
                        .placeholder(R.drawable.user_profile_default)
                        .into(profileImage);

            }
        });

        recyclerViewMyPlans.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize plan items list and adapter
        planItems = new ArrayList<>();
        planAdapter = new PlanAdapter(planItems);
        recyclerViewMyPlans.setAdapter(planAdapter);
        databaseRef = FirebaseDatabase.getInstance().getReference("plan");

        databaseRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK);
            }
        });

        fetchPosts();

        return view ;

    }

    private void showAddPlanDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_plan, null);
        builder.setView(dialogView);

        final EditText editTextPlanTitle = dialogView.findViewById(R.id.editTextPlanTitle);
        Button confirmAddPlanButton = dialogView.findViewById(R.id.confirmAddPlanButton);

        final AlertDialog dialog = builder.create();
        dialog.show();

        confirmAddPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextPlanTitle.getText().toString().trim();
                if (!title.isEmpty()) {
                    addPlanToDatabase(title);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter a plan title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addPlanToDatabase(String title) {
        String uid = currentUser.getUid();
        String planItemId = databaseRef.push().getKey();
        PlanItem planItem = new PlanItem(planItemId,title);
        databaseRef.child(uid).child(planItemId).setValue(planItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == RESULT_OK && intent != null) {
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(intent.getData());
                File tempFile = File.createTempFile("temp_image", null, getActivity().getCacheDir());
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
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(filepath));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to decode image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            final StorageReference fileRef = storageReference.child(currentUser.getUid() + ".jpeg");
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
                        String uid=currentUser.getUid() ;
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
                    if (post.getPublisherUid().equals(currentUser.getUid())) {
                        postList.add(post);
                    }
                }
                // Notify your RecyclerView's adapter about the updated data
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error fetching posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}




