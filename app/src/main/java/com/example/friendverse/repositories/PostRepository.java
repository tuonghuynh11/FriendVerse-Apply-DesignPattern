package com.example.friendverse.repositories;

import androidx.lifecycle.LiveData;

import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Resquest.PostApiClient;
import com.example.friendverse.Resquest.UserApiClient;

import java.util.List;

public class PostRepository {
    private static PostRepository instance;

    //LiveData
    private PostApiClient postApiClient;

    //Constructor
    private PostRepository(){
        postApiClient= PostApiClient.getInstance();
    }
    public static PostRepository getInstance(){
        if(instance==null){
            instance= new PostRepository();
        }
        return instance;
    }
    public LiveData<List<PostModel>> getPosts(){return postApiClient.getPosts();}
    public void getAllPosts() {
        postApiClient.getAllPosts();
    }
}
