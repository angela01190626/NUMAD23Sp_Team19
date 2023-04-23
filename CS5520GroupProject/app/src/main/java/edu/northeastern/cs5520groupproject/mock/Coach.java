package edu.northeastern.cs5520groupproject.mock;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Coach {
    private String id;
    private String imageUrl;
    private String name;

    // Required default constructor for Firebase
    public Coach() {}

    public Coach(String id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
