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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HomePageCourseMocker {
    private Context context;
    private StorageReference storageRef;

    public HomePageCourseMocker(Context context) {
        this.context = context;
        storageRef = FirebaseStorage.getInstance().getReference("homepage_courses");
    }

    public void createRunMockCourses() {
        // Define your course data
        List<String> courseNames = new ArrayList<>();
        List<String> durations = new ArrayList<>();
        List<String> calories = new ArrayList<>();
        List<String> levels = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        // Fill in your mock course data
        courseNames.add("15-minute aerobic fat-burning run");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K1");
        prices.add("Price: $10");

        courseNames.add("Music fat-burning run");
        durations.add("Mins: 25");
        calories.add("195-293 cal");
        levels.add("Level: K2");
        prices.add("Price: $10");

        courseNames.add("Boys' 1000m special training");
        durations.add("Mins: 36");
        calories.add("301-451 cal");
        levels.add("Level: K2");
        prices.add("Price: $10");

        courseNames.add("Music stress-relieving run");
        durations.add("Mins: 25");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $10");

        courseNames.add("Speed engine");
        durations.add("Mins: 30");
        calories.add("88-132 cal");
        levels.add("Level: K2");
        prices.add("Price: $10");

        courseNames.add("Advanced fat-burning HIIT run");
        durations.add("Mins: 29");
        calories.add("286-428 cal");
        levels.add("Level: K3");
        prices.add("Price: $10");

        courseNames.add("Intensive fat-burning HIIT run");
        durations.add("Mins: 35");
        calories.add("407-611 cal");
        levels.add("Level: K3");
        prices.add("Price: $10");

        courseNames.add("Easy run for beginners");
        durations.add("Mins: 20");
        calories.add("144-216 cal");
        levels.add("Level: K3");
        prices.add("Price: $10");

        courseNames.add("35-minute aerobic fat-burning run 01");
        durations.add("Mins: 35");
        calories.add("297-445 cal");
        levels.add("Level: K1");
        prices.add("Price: $10");

        courseNames.add("Free run");
        durations.add("Mins: 30");
        calories.add("250-320 cal");
        levels.add("Level: K2");
        prices.add("Price: $10");

        // Create courses and upload them to Firebase
        for (int i = 0; i < courseNames.size(); i++) {
            uploadCourseImage(i + 1, "run", courseNames.get(i),
                    durations.get(i),calories.get(i), levels.get(i), prices.get(i));
        }
    }

    public void createTrainMockCourses() {
        // Define your course data
        List<String> courseNames = new ArrayList<>();
        List<String> durations = new ArrayList<>();
        List<String> calories = new ArrayList<>();
        List<String> levels = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        // Fill in your mock course data
        courseNames.add("15-minute aerobic fat-burning dumbbell workout");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K1");
        prices.add("Price: $21");

        // 2
        courseNames.add("Full Body Dumbbell Blast");
        durations.add("Mins: 30");
        calories.add("250-350 cal");
        levels.add("Level: K2");
        prices.add("Price: $22");

        // 3
        courseNames.add("Total Body Toning");
        durations.add("Mins: 45");
        calories.add("301-451 cal");
        levels.add("Level: K3");
        prices.add("Price: $33");

        // 4
        courseNames.add("Upper Body Blast");
        durations.add("Mins: 40");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $15");

        // 5
        courseNames.add("Lower Body Burn");
        durations.add("Mins: 40");
        calories.add("88-132 cal");
        levels.add("Level: K2");
        prices.add("Price: $6");

        // 6
        courseNames.add("Full Body Fusion");
        durations.add("Mins: 50");
        calories.add("286-428 cal");
        levels.add("Level: K3");
        prices.add("Price: $19");

        // 7
        courseNames.add("Core Crusher");
        durations.add("Mins: 30");
        calories.add("407-611 cal");
        levels.add("Level: K1");
        prices.add("Price: $23");

        // 8
        courseNames.add("Total Body Transformation");
        durations.add("Mins: 40");
        calories.add("144-216 cal");
        levels.add("Level: K5");
        prices.add("Price: $17");

        // 9
        courseNames.add("Relieve physical and mental fatigue");
        durations.add("Mins: 11");
        calories.add("297-445 cal");
        levels.add("Level: K1");
        prices.add("Price: $8");

        // 10
        courseNames.add("X-shaped legs/calf external rotation");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $9");

        // Create courses and upload them to Firebase
        for (int i = 0; i < courseNames.size(); i++) {
            uploadCourseImage(i + 1, "ex", courseNames.get(i),
                    durations.get(i),calories.get(i), levels.get(i), prices.get(i));
        }
    }

    public void createCycleMockCourses() {
        // Define your course data
        List<String> courseNames = new ArrayList<>();
        List<String> durations = new ArrayList<>();
        List<String> calories = new ArrayList<>();
        List<String> levels = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        // Fill in your mock course data
        courseNames.add("Free run");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K1");
        prices.add("Price: $21");

        // 2
        courseNames.add("15-minute aerobic fat-burning run");
        durations.add("Mins: 30");
        calories.add("250-350 cal");
        levels.add("Level: K2");
        prices.add("Price: $22");

        // 3
        courseNames.add("Music fat-burning run");
        durations.add("Mins: 45");
        calories.add("301-451 cal");
        levels.add("Level: K3");
        prices.add("Price: $33");

        // 4
        courseNames.add("Boys' 1000m special training");
        durations.add("Mins: 40");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $15");

        // 5
        courseNames.add("Music stress-relieving run");
        durations.add("Mins: 40");
        calories.add("88-132 cal");
        levels.add("Level: K2");
        prices.add("Price: $6");

        // 6
        courseNames.add("Speed engine");
        durations.add("Mins: 50");
        calories.add("286-428 cal");
        levels.add("Level: K3");
        prices.add("Price: $19");

        // 7
        courseNames.add("Advanced fat-burning HIIT run");
        durations.add("Mins: 30");
        calories.add("407-611 cal");
        levels.add("Level: K1");
        prices.add("Price: $23");

        // 8
        courseNames.add("Intensive fat-burning HIIT run");
        durations.add("Mins: 40");
        calories.add("144-216 cal");
        levels.add("Level: K5");
        prices.add("Price: $17");

        // 9
        courseNames.add("Easy run for beginners");
        durations.add("Mins: 11");
        calories.add("297-445 cal");
        levels.add("Level: K1");
        prices.add("Price: $8");

        // 10
        courseNames.add("35-minute aerobic fat-burning run 01");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $9");

        // Create courses and upload them to Firebase
        for (int i = 0; i < courseNames.size(); i++) {
            uploadCourseImage(i + 1, "bic", courseNames.get(i),
                    durations.get(i),calories.get(i), levels.get(i), prices.get(i));
        }
    }

    public void createYogaMockCourses() {
        // Define your course data
        List<String> courseNames = new ArrayList<>();
        List<String> durations = new ArrayList<>();
        List<String> calories = new ArrayList<>();
        List<String> levels = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        // Fill in your mock course data
        courseNames.add("Flexibility improvement, full-body stretching");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K1");
        prices.add("Price: $998");

        // 2
        courseNames.add("Hot yoga, full-body stretching");
        durations.add("Mins: 30");
        calories.add("250-350 cal");
        levels.add("Level: K2");
        prices.add("Price: $698");

        // 3
        courseNames.add("Full-body stretching, practiced on bed");
        durations.add("Mins: 45");
        calories.add("301-451 cal");
        levels.add("Level: K3");
        prices.add("Price: $798");

        // 4
        courseNames.add("Basic strength");
        durations.add("Mins: 40");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $98");

        // 5
        courseNames.add("Balance training");
        durations.add("Mins: 40");
        calories.add("88-132 cal");
        levels.add("Level: K2");
        prices.add("Price: $598");

        // 6
        courseNames.add("Lower body flexibility");
        durations.add("Mins: 50");
        calories.add("286-428 cal");
        levels.add("Level: K3");
        prices.add("Price: $666");

        // 7
        courseNames.add("Beginner waist and abdomen training");
        durations.add("Mins: 30");
        calories.add("407-611 cal");
        levels.add("Level: K1");
        prices.add("Price: $233");

        // 8
        courseNames.add("Beginner full-body workout");
        durations.add("Mins: 40");
        calories.add("144-216 cal");
        levels.add("Level: K5");
        prices.add("Price: $198");

        // 9
        courseNames.add("Relieve physical and mental fatigue");
        durations.add("Mins: 11");
        calories.add("297-445 cal");
        levels.add("Level: K1");
        prices.add("Price: $898");

        // 10
        courseNames.add("X-shaped legs/calf external rotation");
        durations.add("Mins: 15");
        calories.add("120-180 cal");
        levels.add("Level: K2");
        prices.add("Price: $298");

        // Create courses and upload them to Firebase
        for (int i = 0; i < courseNames.size(); i++) {
            uploadCourseImage(i + 1, "yoga", courseNames.get(i),
                    durations.get(i),calories.get(i), levels.get(i), prices.get(i));
        }
    }

    private void uploadCourseImage(int courseNumber, String courseType, String courseName,
                                   String duration, String calorie, String level, String price) {
        String imageName = courseType + "00" + courseNumber + ".jpeg";
        try {
            InputStream inputStream = context.getAssets().open("mocks/homepage_courses/" + imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            String courseId = UUID.randomUUID().toString();
            StorageReference courseImageRef = storageRef.child(courseId).child("image.jpeg");
            UploadTask uploadTask = courseImageRef.putBytes(imageData);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                courseImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    uploadCourseData(courseId, uri, courseName, duration, calorie, level, price);
                });
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadCourseData(String courseId, Uri imageUri, String courseName,
                                  String duration, String calorie, String level, String price) {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("id", courseId);
        courseData.put("imageUrl", imageUri.toString());
        courseData.put("courseName", courseName);
        courseData.put("duration", duration);
        courseData.put("calorie", calorie);
        courseData.put("level", level);
        courseData.put("price", price);

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("homepage_courses_yoga");
        coursesRef.child(courseId).setValue(courseData);
    }
}
