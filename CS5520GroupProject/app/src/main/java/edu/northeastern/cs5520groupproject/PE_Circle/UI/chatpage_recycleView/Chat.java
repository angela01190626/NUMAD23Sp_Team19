package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

public class Chat {

    public String email;

    public String name;

    public String lastMessage;
    public String uid;

    private int stickerId;

    public Chat() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Chat(String email, String name, String uid, String lastMessage) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.lastMessage = lastMessage;
    }

    public Chat(String email, String name, String uid, int stickerId) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.stickerId = stickerId;
    }

    public Chat(String email, String name, int stickerId) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.stickerId = stickerId;
    }



    public int getStickerId() {
        return stickerId;
    }

    public void setStickerId(int stickerId) {
        this.stickerId = stickerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
