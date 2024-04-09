package com.example.friendverse.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Resquest.PostApiClient;
import com.example.friendverse.repositories.PostRepository;
import com.example.friendverse.repositories.UserRepository;

import java.util.List;

public class PostViewModel extends ViewModel {
    private PostRepository postRepository;
    //Constructor
    public PostViewModel() {
        postRepository = PostRepository.getInstance();
    }

    public LiveData<List<PostModel>> getPosts() {
        return this.postRepository.getPosts();
    }
    public void getAllPosts() {
        postRepository.getAllPosts();
    }
}
