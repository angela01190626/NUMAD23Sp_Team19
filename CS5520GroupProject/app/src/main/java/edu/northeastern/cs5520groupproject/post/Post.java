package edu.northeastern.cs5520groupproject.post;

public class Post {
    private String postId;
    private String imageUrl;
    private String description;
    private String publisherUid;
    private int likesCount;

    public Post() {

    }

    public Post(String postId, String imageUrl, String description, String publisherUid) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publisherUid = publisherUid;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postid) {
        this.postId = postid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisherUid() {
        return publisherUid;
    }

    public void setPublisherUid(String publisherUid) {
        this.publisherUid = publisherUid;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}