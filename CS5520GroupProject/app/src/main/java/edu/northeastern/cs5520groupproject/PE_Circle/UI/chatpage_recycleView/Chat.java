package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

public class Chat {

    public String friend;

    public int stickerId;

    public String lastMessage;

    public Chat() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Chat(String friend, String lastMessage, int stickerId) {
        this.friend = friend;
        this.lastMessage = lastMessage;
        this.stickerId = stickerId;
    }
}
