package com.example.friendverse.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ConversationsModel implements Parcelable {
    private Date dateTime;
    private String lastMessage;
    private String receiverId;
    private String receiverImage;
    private String receiverName;
    private String senderId;
    private String senderName;

    public ConversationsModel() {
    }

    public ConversationsModel(Date dateTime, String lastMessage, String receiverId, String receiverImage, String receiverName, String senderId, String senderName) {
        this.dateTime = dateTime;
        this.lastMessage = lastMessage;
        this.receiverId = receiverId;
        this.receiverImage = receiverImage;
        this.receiverName = receiverName;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    protected ConversationsModel(Parcel in) {
        dateTime = (Date) in.readValue(getClass().getClassLoader());
        lastMessage = in.readString();
        receiverId = in.readString();
        receiverImage = in.readString();
        receiverName = in.readString();
        senderId = in.readString();
        senderName = in.readString();
    }

    public static final Creator<ConversationsModel> CREATOR = new Creator<ConversationsModel>() {
        @Override
        public ConversationsModel createFromParcel(Parcel in) {
            return new ConversationsModel(in);
        }

        @Override
        public ConversationsModel[] newArray(int size) {
            return new ConversationsModel[size];
        }
    };

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(dateTime);
        dest.writeString(lastMessage);
        dest.writeString(receiverId);
        dest.writeString(receiverImage);
        dest.writeString(receiverName);
        dest.writeString(senderId);
        dest.writeString(senderName);
    }
}
