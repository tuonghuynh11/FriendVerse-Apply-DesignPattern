package com.example.friendverse.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostService implements IPostService{
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");

    @Override
    public void getPosts(PostCallback callBack) {

    }
}
