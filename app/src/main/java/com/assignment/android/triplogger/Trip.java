package com.assignment.android.triplogger;

import java.util.Date;
import java.util.UUID;

public class Trip {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mDest;
    private String mComment;
    private String GPS;
    private int TripType;

    public Trip(){this(UUID.randomUUID());}
    public Trip(UUID id){
        mId=id;
        mDate=new Date();
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setmDest(String mDest) {
        this.mDest = mDest;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public void setTripType(int tripType) {
        TripType = tripType;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDest() {
        return mDest;
    }

    public String getComment() {
        return mComment;
    }

    public String getGPS() {
        return GPS;
    }

    public int getTripType() {
        return TripType;
    }

    public String getPhotoFilename(){
        return "IMG_"+getId().toString()+".jpg";
    }
}
