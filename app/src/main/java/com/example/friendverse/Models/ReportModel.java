package com.example.friendverse.Models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ReportModel implements Parcelable {
    private String postType;
    private String postid;
    private String postimage;
    private String publisher;
    private int repostCount;
    private Boolean shared;
    private String username;

    public ReportModel(String postType, String postid, String postimage, String publisher, int repostCount, boolean shared, String username) {
        this.postType = postType;
        this.postid = postid;
        this.postimage = postimage;
        this.publisher = publisher;
        this.repostCount = repostCount;
        this.shared = shared;
        this.username = username;
    }
    public ReportModel(){

    }

    protected ReportModel(Parcel in) {
        postType = in.readString();
        postid = in.readString();
        postimage = in.readString();
        publisher = in.readString();
        repostCount = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            shared = in.readBoolean();
        }
        username = in.readString();
    }

    public static final Creator<ReportModel> CREATOR = new Creator<ReportModel>() {
        @Override
        public ReportModel createFromParcel(Parcel in) {
            return new ReportModel(in);
        }

        @Override
        public ReportModel[] newArray(int size) {
            return new ReportModel[size];
        }
    };

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(postType);
        dest.writeString(postid);
        dest.writeString(postimage);
        dest.writeString(publisher);
        dest.writeInt(repostCount);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(shared);
        }
        dest.writeString(username);
    }
}
