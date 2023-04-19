package edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView;

public class Course {

    private String id;
    private String courseName;
    private String timeDuration;
    private String calorieCount;
    private String level;

    private int imageResource;

    public Course() {
    }

    public Course(String id, String courseName, String timeDuration, String calorieCount, String level, int imageResource) {
        this.id = id;
        this.courseName = courseName;
        this.timeDuration = timeDuration;
        this.calorieCount = calorieCount;
        this.level = level;
        this.imageResource = imageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(String calorieCount) {
        this.calorieCount = calorieCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
