package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

import com.google.firebase.database.Exclude;

public class PlanItem {
    private String planItem;

//    @Exclude
    private String Id;

    public PlanItem() {
        // Required empty public constructor for Firebase Realtime Database
    }
    public PlanItem(String planItem) {
        this.planItem = planItem;
    }

    public PlanItem(String Id, String planItem) {
        this.Id=Id;
        this.planItem = planItem;
    }

    public String getItem() {
        return planItem;
    }


    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id=id;
    }
}