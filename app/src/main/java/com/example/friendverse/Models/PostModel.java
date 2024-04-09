package com.example.friendverse.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class PostModel implements Parcelable {
    private String postid;
    private boolean isShared;
    private String postimage;
    private String postvid;
    private String sharer;
    private String description;
    private String publisher;
    private String repostText;
    private String postType;
    private String Imagine;
    private String username;
    private int repostCount;
    public PostModel(String postid,String Imagine,String username, String postType, String sharer,boolean isShared, String postimage, String postvid, String description, String publisher) {
        this.postid = postid;
        this.sharer = sharer;
        this.postType = postType;
        this.postimage = postimage;
        this.postvid = postvid;
        this.isShared = true;
        this.description = description;
        this.Imagine =Imagine;
        this.publisher = publisher;
        this.username = username;
    }

    public PostModel() {
    }

    protected PostModel(Parcel in) {
        postid = in.readString();
        isShared = in.readByte() != 0;
        postimage = in.readString();
        postvid = in.readString();
        sharer = in.readString();
        description = in.readString();
        publisher = in.readString();
        repostText = in.readString();
        postType = in.readString();
        Imagine = in.readString();
        username = in.readString();
        repostCount = in.readInt();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    public String getRepostText() {
        return repostText;
    }

    public void setRepostText(String repostText) {
        this.repostText = repostText;
    }
    public String getSharer() {
        return sharer;
    }

    public void setSharer(String sharer) {
        this.sharer = sharer;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }
    public int getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
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

    public String getPostvid() {
        return postvid;
    }

    public void setPostvid(String postvid) {
        this.postvid = postvid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostType() {
        return  postType;}

    public void setPostType( String postType){
        this.postType = postType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImage_profile(String toString) {

    }

    public String getImagine() {
        return Imagine;
    }

    public void setImagine(String imagine) {
        Imagine = imagine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postid);
        dest.writeByte((byte) (isShared ? 1 : 0));
        dest.writeString(postimage);
        dest.writeString(postvid);
        dest.writeString(sharer);
        dest.writeString(description);
        dest.writeString(publisher);
        dest.writeString(repostText);
        dest.writeString(postType);
        dest.writeString(Imagine);
        dest.writeString(username);
        dest.writeInt(repostCount);
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "postid='" + postid + '\'' +
                ", isShared=" + isShared +
                ", postimage='" + postimage + '\'' +
                ", postvid='" + postvid + '\'' +
                ", sharer='" + sharer + '\'' +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", repostText='" + repostText + '\'' +
                ", postType='" + postType + '\'' +
                ", Imagine='" + Imagine + '\'' +
                ", username='" + username + '\'' +
                ", repostCount=" + repostCount +
                '}';
    }
}


