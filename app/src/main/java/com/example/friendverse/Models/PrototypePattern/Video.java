package com.example.friendverse.Models.PrototypePattern;

public class Video extends Post{
    private String videoLink;
    public Video(){

    }
    public Video(Video video){
        super(video);
        if(video !=null){
            this.videoLink= video.videoLink;
        }
    }
    @Override
    public Video clone() {
        return new Video(this);
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
