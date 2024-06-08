package com.example.friendverse.Models.PrototypePattern;

public class VideoArticle extends Post{
    private String videoLink;
    public VideoArticle(){

    }
    public VideoArticle(VideoArticle videoArticle){
        super(videoArticle);
        if(videoArticle!=null){
            this.videoLink= videoArticle.videoLink;
        }
    }
    @Override
    public VideoArticle clone() {
        return new VideoArticle(this);
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
