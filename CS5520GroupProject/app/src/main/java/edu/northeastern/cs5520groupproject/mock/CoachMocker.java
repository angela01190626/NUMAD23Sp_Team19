package edu.northeastern.cs5520groupproject.mock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class CoachMocker {
    private Context context;
    private StorageReference storageRef;
    private DatabaseReference coachRef;

    public CoachMocker(Context context) {
        this.context = context;
        storageRef = FirebaseStorage.getInstance().getReference("coach");
        coachRef = FirebaseDatabase.getInstance().getReference("coach");
    }

    public void createCoaches() {
        String[] coachNames = {"jacky", "jessica", "jose", "jordan", "may", "nancy", "ray", "ronaldo", "david", "zachary"};

        for (String coachName : coachNames) {
            String imageName = coachName + ".jpg";
            try {
                InputStream inputStream = context.getAssets().open("mocks/coaches/" + imageName);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                String coachId = UUID.randomUUID().toString();
                StorageReference coachImageRef = storageRef.child(coachId).child("image.jpeg");
                UploadTask uploadTask = coachImageRef.putBytes(imageData);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    coachImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        uploadCoachData(coachId, uri.toString(), coachName);
                    });
                }).addOnFailureListener(e -> {
                    // Handle the error here
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadCoachData(String coachId, String imageUrl, String coachName) {
        Coach coach = new Coach(coachId, imageUrl, coachName);
        coachRef.child(coachId).setValue(coach);
    }
}
