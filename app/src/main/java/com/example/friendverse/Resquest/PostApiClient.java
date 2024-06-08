package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Service.PostCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PostApiClient {
    private static PostApiClient instance;
    private RetrievePostsRunnable retrievePostsRunnable;

    private PostApiClient() {
    }

    public static PostApiClient getInstance() {
        if (instance == null) {
            instance = new PostApiClient();
        }
        return instance;
    }

    public void getPostsByPage(int page, int pageSize, PostCallback callback) {
        if (retrievePostsRunnable != null) {
            retrievePostsRunnable = null;
        }
        retrievePostsRunnable = new RetrievePostsRunnable(page, pageSize, callback);
        final Future<?> myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrievePostsRunnable);

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrievePostsRunnable implements Runnable {
        private int page;
        private int pageSize;
        private boolean cancelRequest;
        private PostCallback callback;

        public RetrievePostsRunnable(int page, int pageSize, PostCallback callback) {
            this.page = page;
            this.pageSize = pageSize;
            this.cancelRequest = false;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                Response<Map<String, PostModel>> response = getPosts(page, pageSize).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.isSuccessful()) {
                    Map<String, PostModel> list = response.body();
                    callback.onSuccess(new ArrayList<>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    callback.onError(error);
                }

            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        }

        private Call<Map<String, PostModel>> getPosts(int page, int pageSize) {
            return Service.getInstance().friendVerseAPI.getListOfPost(page, pageSize);
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }
}

