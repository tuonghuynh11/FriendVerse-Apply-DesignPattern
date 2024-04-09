package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PostApiClient {
    //LiveData
    private MutableLiveData<List<PostModel>> mPosts;

    private static PostApiClient instance;


    //making Global Runnable
    private RetrievePostsRunnable retrievePostsRunnable;

    private PostApiClient() {
        mPosts = new MutableLiveData<>();
    }

    public static PostApiClient getInstance() {
        if (instance == null) {
            instance = new PostApiClient();
        }
        return instance;
    }

    public LiveData<List<PostModel>> getPosts() {
        return mPosts;
    }

    public void getAllPosts() {
        if (retrievePostsRunnable != null) {
            retrievePostsRunnable = null;
        }
        retrievePostsRunnable = new RetrievePostsRunnable();
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrievePostsRunnable);

        // Call set timeout for api session call (set timeout cho phiên gọi api
        // nếu quá lâu không phản hồi)

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }
    //Retrieving Data from RestAPI bu runnable class

    private class RetrievePostsRunnable implements Runnable {
        boolean cancelRequest;

        public RetrievePostsRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getPosts().execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response " + response.body());

                if (response.isSuccessful()) {
                    Map<String, PostModel> list = (Map<String, PostModel>) response.body();
                    mPosts.postValue(new ArrayList<PostModel>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err " + e);

                e.printStackTrace();
                mPosts.postValue(null);
            }

        }

        private Call<Map<String, PostModel>> getPosts() {
            return Service.getInstance().friendVerseAPI.getListOfPost();
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Posts Request");
            cancelRequest = true;
        }
    }
}
