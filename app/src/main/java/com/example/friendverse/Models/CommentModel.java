package com.example.friendverse.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CommentModel implements Parcelable {
    private String comment;
    private String commentID;
    private String publisher;

    public CommentModel() {
    }

    public CommentModel(String comment, String commentID, String publisher) {
        this.comment = comment;
        this.commentID = commentID;
        this.publisher = publisher;
    }

    protected CommentModel(Parcel in) {
        comment = in.readString();
        commentID = in.readString();
        publisher = in.readString();
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(commentID);
        dest.writeString(publisher);
    }
}
