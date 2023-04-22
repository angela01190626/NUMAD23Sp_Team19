package edu.northeastern.cs5520groupproject.shopping;

public class Purchase {

    private String courseId;
    private double price;

    public Purchase() {
        // Default constructor required for calls to DataSnapshot.getValue(Purchase.class)
    }

    public Purchase(String courseId, double price) {
        this.courseId = courseId;
        this.price = price;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
