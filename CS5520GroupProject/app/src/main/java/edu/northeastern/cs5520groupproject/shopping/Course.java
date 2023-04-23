package edu.northeastern.cs5520groupproject.shopping;

public class Course {
    private String id;
    private String image;
    private String description;
    private String coachName;
    private double price;

    public Course() {
        // Required empty constructor for Firebase
    }

    public Course(String id, String imageUrl, String description, String coachName, double price) {
        this.id = id;
        this.image = imageUrl;
        this.description = description;
        this.coachName = coachName;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
