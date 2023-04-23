package edu.northeastern.cs5520groupproject.mock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CourseMocker {
    private Context context;
    private StorageReference storageRef;

    public CourseMocker(Context context) {
        this.context = context;
        storageRef = FirebaseStorage.getInstance().getReference("courses");
    }

    public void createMockCourses() {
        // Define your course data
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<String> coachNames = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();

        // Fill in your mock course data
        descriptions.add("Boxing");
        coachNames.add("Jacky");
        prices.add(59.99);

        descriptions.add("Meditation");
        coachNames.add("Jessica");
        prices.add(49.99);

        descriptions.add("Tennis");
        coachNames.add("David");
        prices.add(55.99);

        descriptions.add("Core Strength");
        coachNames.add("Nancy");
        prices.add(57.99);

        descriptions.add("Yoga");
        coachNames.add("Jose");
        prices.add(49.99);

        descriptions.add("Aerobics");
        coachNames.add("May");
        prices.add(55.99);

        descriptions.add("Football");
        coachNames.add("Ronaldo");
        prices.add(60.99);

        descriptions.add("Basketball");
        coachNames.add("Jordan");
        prices.add(60.99);

        descriptions.add("Muscle Building");
        coachNames.add("Ray");
        prices.add(60.99);

        descriptions.add("Weightlifting");
        coachNames.add("Zachary");
        prices.add(57.99);

        // Create courses and upload them to Firebase
        for (int i = 0; i < descriptions.size(); i++) {
            uploadCourseImage(i + 1, descriptions.get(i), coachNames.get(i), prices.get(i));
        }
    }

    private void uploadCourseImage(int courseNumber, String description, String coachName, double price) {
        String imageName = courseNumber + ".jpg";
        try {
            InputStream inputStream = context.getAssets().open("mocks/courses/" + imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            String courseId = UUID.randomUUID().toString();
            StorageReference courseImageRef = storageRef.child(courseId).child("image.jpeg");
            UploadTask uploadTask = courseImageRef.putBytes(imageData);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                courseImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    uploadCourseData(courseId, uri, description, coachName, price);
                });
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadCourseData(String courseId, Uri imageUri, String description, String coachName, double price) {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("id", courseId);
        courseData.put("image", imageUri.toString());
        courseData.put("description", description);
        courseData.put("coachName", coachName);
        courseData.put("price", price);

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("courses");
        coursesRef.child(courseId).setValue(courseData);
    }
}