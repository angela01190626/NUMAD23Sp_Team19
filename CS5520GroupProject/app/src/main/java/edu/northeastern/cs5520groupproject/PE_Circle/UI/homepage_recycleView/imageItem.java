package edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView;

public class imageItem {

    private int image;
    private String username;

    public imageItem(){ }

    public imageItem(int image, String username) {
        this.image = image;
        this.username = username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
