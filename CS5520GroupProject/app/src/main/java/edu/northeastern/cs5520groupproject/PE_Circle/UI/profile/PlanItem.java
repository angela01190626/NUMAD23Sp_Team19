package edu.northeastern.cs5520groupproject.PE_Circle.UI.profile;

public class PlanItem {
    private String planItem;


    public PlanItem() {
        // Required empty public constructor for Firebase Realtime Database
    }

    public PlanItem(String planItem) {
        this.planItem = planItem;
    }

    public String getItem() {
        return planItem;
    }



}