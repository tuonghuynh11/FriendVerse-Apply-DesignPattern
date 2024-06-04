package com.example.friendverse.Models.PrototypePattern;

public class Post extends PostGeneral {
    private String hashtag;
    private String description;

    public Post() {
    }

    public Post(Post post) {
        super(post);
        if (post != null) {
            this.hashtag = post.hashtag;
            this.description = post.description;
        }

    }

    @Override
    public Post clone() {
        return new Post(this);
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
