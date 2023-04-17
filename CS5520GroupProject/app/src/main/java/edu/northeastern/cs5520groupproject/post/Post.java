package edu.northeastern.cs5520groupproject.post;

public class Post {
    private String postid;
    private String imageUrl; // Changed from postImage
    private String description;
    private String publisher;

    public Post() {
    }

    public Post(String postid, String imageUrl, String description, String publisher) {
        this.postid = postid;
        this.imageUrl = imageUrl; // Changed from postImage
        this.description = description;
        this.publisher = publisher;
    }

    public String getPostId() {
        return postid;
    }

    public void setPostId(String postid) {
        this.postid = postid;
    }

    public String getImageUrl() { // Changed from getPostImage()
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { // Changed from setPostImage()
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
