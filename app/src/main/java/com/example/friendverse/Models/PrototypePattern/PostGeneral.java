package com.example.friendverse.Models.PrototypePattern;

import com.example.friendverse.Model.Comment;
import com.example.friendverse.Model.User;

import java.util.Date;
import java.util.List;

public abstract class PostGeneral {
    private String id;
    private String publisher;
    private Date publishDate;
    private int viewers;
    private List<Comment> comments;
    private int numberOfLike;


    public PostGeneral() {

    }

    public PostGeneral(PostGeneral postGeneral) {
        if (postGeneral != null) {
            this.id = postGeneral.id;
            this.publisher = postGeneral.publisher;
            this.publishDate = postGeneral.publishDate;
            this.viewers = postGeneral.viewers;
            this.comments = postGeneral.comments;
            this.numberOfLike = postGeneral.numberOfLike;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(int numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public abstract PostGeneral clone();
}
