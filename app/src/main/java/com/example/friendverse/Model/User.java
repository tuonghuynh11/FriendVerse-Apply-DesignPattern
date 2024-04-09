package com.example.friendverse.Model;

import java.io.Serializable;

public class User implements Serializable {
    public static final String USERKEY = "Users";
    public static final String IDKEY = "id";
    public static final String USERNAMEKEY = "username";
    public static final String FULLNAMEKEY = "fullname";
    public static final String IMAGEKEY = "imageurl";
    public static final String BIOKEY = "bio";
    public static final String EMAILOKEY = "email";
    public static final String TOKENCALLKEY = "tokenCall";
    public static final String TOKENKEY = "token";
    public static final String ACTIVITYKEY = "activity";

    public static final String WEBSITEKEY = "website";
    public static final String PHONEKEY = "phonenumber";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private String id;
    private String username;
    private String email;
    private String fullname;
    private String imageurl;
    private String bio;

    private String website;
    private String phonenumber;

    private String tokenCall;
    private String token;


    private int activity;

    private boolean isSelected = false;

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    private boolean isBan;
    private double latitude;
    private double longitude;

    public User() {
    }


    public User(String id, String username, String fullname, String imageurl, String bio, String email, int activity, boolean ban, String tokenCall, String token, String website, String phonenumber, double latitude, double longitude) {

        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
        this.email = email;
        this.activity = activity;
        this.tokenCall = tokenCall;
        this.token = token;
        this.isBan = ban;
        this.website = website;
        this.phonenumber = phonenumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenCall() {
        return tokenCall;
    }

    public void setTokenCall(String tokenCall) {
        this.tokenCall = tokenCall;
    }

    public int getActivity() {
        return activity;
    }

    public void setAvailability(int activity) {
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

}
