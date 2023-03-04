package edu.northeastern.cs5520groupproject.history;

import android.os.Parcel;

public class History {

    public String sender;

    public String date;

    public int stickerId;

    public History() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    protected History(String sender, String date, int stickerId) {
        this.sender = sender;
        this.date = date;
        this.stickerId = stickerId;
    }

}
