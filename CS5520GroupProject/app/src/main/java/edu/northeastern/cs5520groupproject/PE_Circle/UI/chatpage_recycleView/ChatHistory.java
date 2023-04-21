package edu.northeastern.cs5520groupproject.PE_Circle.UI.chatpage_recycleView;

public class ChatHistory {

    private String message;

    private String sender;

    private int time;

    private String receiverSender;


    public ChatHistory() {
    }

    public ChatHistory(String message, String sender, int time, String receiverSender) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.receiverSender = receiverSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getReceiverSender() {
        return receiverSender;
    }

    public void setReceiverSender(String receiverSender) {
        this.receiverSender = receiverSender;
    }
}
