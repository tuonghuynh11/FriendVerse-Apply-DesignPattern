package com.example.friendverse.Models.PrototypePattern;

public class Article extends Post{
    private String image;
    public Article(){

    }
    public Article(Article article){
        super(article);
        if(article!=null){
            this.image= article.image;
        }
    }
    @Override
    public Article clone() {
        return new Article(this);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
