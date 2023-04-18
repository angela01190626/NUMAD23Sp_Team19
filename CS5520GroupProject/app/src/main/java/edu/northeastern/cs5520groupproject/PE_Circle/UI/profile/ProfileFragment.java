package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class ProfileFragment extends Fragment {
    private static final int REQUEST_CODE_IMAGE_PICK = 1000;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    DatabaseReference databaseRef;
    TextView profileEmail;
    TextView joinSince;
     RecyclerView recyclerView;
     List<PlanItem> planItems;
    PlanAdapter planAdapter;
    EditText etPlan;
    CircleImageView profileImage;
//    Button updateImageBtn;
    DatabaseReference userRef;
    StorageReference storageReference;
    Uri filepath;
    StorageReference fileRef;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String email = currentUser.getEmail();
        Date creationDate = new Date(currentUser.getMetadata().getCreationTimestamp());
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String strDate = dateFormat.format(creationDate);

        profileImage=view.findViewById(R.id.profileImage);

        userRef= FirebaseDatabase.getInstance().getReference("userImage");

        storageReference= FirebaseStorage.getInstance().getReference("profile_images");
        fileRef= storageReference.child(currentUser.getUid() + ".jpeg");
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

        profileEmail= view.findViewById(R.id.profileEmail);
        profileEmail.setText("Email: "+email);
        joinSince= view.findViewById(R.id.joinDate);
        joinSince.setText("Join Since: "+strDate);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.planList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize plan items list and adapter
        planItems = new ArrayList<>();
        planAdapter = new PlanAdapter(planItems);
        recyclerView.setAdapter(planAdapter);
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
        etPlan = view.findViewById(R.id.et_plan);

        Button btnAddPlan = view.findViewById(R.id.btn_add_plan);
        btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plan = etPlan.getText().toString();
                addPlanToDatabase(plan);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK);
            }
        });

        return view ;

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

}




