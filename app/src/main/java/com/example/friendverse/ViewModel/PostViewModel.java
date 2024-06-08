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


    public LiveData<PostModel> createNewPost(PostModel postModel){
        return postRepository.createNewPost(postModel);
    }
    public LiveData<PostModel> updatePost(String idPost,PostModel postModel){
       return postRepository.updatePost(idPost,postModel);
    }

    public void deletePost(String idPost){
        postRepository.deletePost(idPost);
    }
}
