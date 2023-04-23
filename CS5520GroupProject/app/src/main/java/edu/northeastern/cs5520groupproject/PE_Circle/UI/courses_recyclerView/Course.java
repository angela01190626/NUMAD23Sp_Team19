package edu.northeastern.cs5520groupproject.PE_Circle.UI.courses_recyclerView;

public class Course {

    private String id;
    private String courseName;
    private String duration;
    private String calorie;
    private String level;

    private String imageUrl;

    private String price;

    public Course() {
    }

    public Course(String id, String courseName, String timeDuration, String calorieCount, String level, String imageResource, String price) {
        this.id = id;
        this.courseName = courseName;
        this.duration = timeDuration;
        this.calorie = calorieCount;
        this.level = level;
        this.imageUrl = imageResource;
        this.price = price;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
