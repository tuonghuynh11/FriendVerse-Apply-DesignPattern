package com.example.friendverse.Model.ProxyPost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Model.Post;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Resquest.PostApiClient;
import com.example.friendverse.Service.PostCallback;

import java.util.ArrayList;
import java.util.List;

public class PostApiClientProxy {
    private static PostApiClientProxy instance;
    private PostApiClient postApiClient;
    private int _currentPage = 0;
    private static final int PAGE_SIZE = 10;
    private List<PostModel> _cachedPosts;
    private MutableLiveData<List<PostModel>> mPosts;

    private PostApiClientProxy() {
        postApiClient = PostApiClient.getInstance();
        _cachedPosts = new ArrayList<>();
        mPosts = new MutableLiveData<>();
    }

    public static PostApiClientProxy getInstance() {
        if (instance == null) {
            instance = new PostApiClientProxy();
        }
        return instance;
    }

    public LiveData<List<PostModel>> getPosts() {
        if (!_cachedPosts.isEmpty()) {
            deliverPostsFromCache();
        } else {
            fetchPosts(_currentPage + 1);
        }
        return mPosts;
    }

    private void deliverPostsFromCache() {
        List<PostModel> nextBatch = new ArrayList<>(_cachedPosts.subList(0, Math.min(PAGE_SIZE, _cachedPosts.size())));
        _cachedPosts.removeAll(nextBatch);
        mPosts.postValue(nextBatch);
        if (_cachedPosts.isEmpty()) {
            fetchPosts(_currentPage + 1);
        }
    }

    public void getNextPosts() {
        getPosts();
    }

    private void fetchPosts(int page) {
        postApiClient.getPostsByPage(page, PAGE_SIZE, new PostCallback() {
            @Override
            public void onSuccess(ArrayList<PostModel> postModels) {
                _currentPage = page;
                if (postModels.size() > PAGE_SIZE) {
                    _cachedPosts.addAll(postModels.subList(PAGE_SIZE, postModels.size()));
                    mPosts.postValue(postModels.subList(0, PAGE_SIZE));
                } else {
                    mPosts.postValue(postModels);
                }
            }

            @Override
            public void onError(String error) {
                mPosts.postValue(null);
            }
        });
    }
}

