package com.example.friendverse.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
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

    protected UserModel(Parcel in) {
        id = in.readString();
        username = in.readString();
        email = in.readString();
        fullname = in.readString();
        imageurl = in.readString();
        bio = in.readString();
        website = in.readString();
        phonenumber = in.readString();
        tokenCall = in.readString();
        token = in.readString();
        activity = in.readInt();
        isSelected = in.readByte() != 0;
        isBan = in.readByte() != 0;
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(fullname);
        dest.writeString(imageurl);
        dest.writeString(bio);
        dest.writeString(website);
        dest.writeString(phonenumber);
        dest.writeString(tokenCall);
        dest.writeString(token);
        dest.writeInt(activity);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isBan ? 1 : 0));
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    private boolean isBan;
    private double latitude;
    private double longitude;

    public UserModel() {
    }


    public UserModel(String id, String username, String fullname, String imageurl, String bio, String email, int activity, boolean ban, String tokenCall, String token, String website, String phonenumber, double latitude, double longitude) {

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

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", bio='" + bio + '\'' +
                ", website='" + website + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", tokenCall='" + tokenCall + '\'' +
                ", token='" + token + '\'' +
                ", activity=" + activity +
                ", isSelected=" + isSelected +
                ", isBan=" + isBan +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
