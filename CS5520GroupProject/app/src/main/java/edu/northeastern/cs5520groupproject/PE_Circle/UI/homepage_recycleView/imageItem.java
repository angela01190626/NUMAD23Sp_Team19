package edu.northeastern.cs5520groupproject.PE_Circle.UI.homepage_recycleView;

public class imageItem {

    private String imageUrl;
    private String name;

    public imageItem(){ }

    public imageItem(String imageUrl, String username) {
        this.imageUrl = imageUrl;
        this.name = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
