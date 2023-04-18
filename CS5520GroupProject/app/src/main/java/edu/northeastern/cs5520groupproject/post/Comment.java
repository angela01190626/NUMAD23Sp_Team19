package edu.northeastern.cs5520groupproject.post;

public class Comment {
    private String commentId;
    private String commenterId;
    private String text;

    private String commenterName;

    public Comment() {
    }

    public Comment(String commentId, String commenterId, String text, String commenterName) {
        this.commentId = commentId;
        this.commenterId = commenterId;
        this.text = text;
        this.commenterName = commenterName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }
}
