package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.PostModel;
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

    //LiveData
    private MutableLiveData<List<PostModel>> mPosts;
    private MutableLiveData<PostModel> newPost;


    private static PostApiClient instance;
    private RetrievePostsRunnable retrievePostsRunnable;
    private RetrievePostsByPageRunnable retrievePostsByPageRunnable;
    private CreateNewPostsRunnable createNewPostsRunnable;
    private UpdatePostInformationRunnable updatePostInformationRunnable;
    private DeletePostRunnable deletePostRunnable;

    private PostApiClient() {

        mPosts = new MutableLiveData<>();
        newPost = new MutableLiveData<>();

    }

    public static PostApiClient getInstance() {
        if (instance == null) {
            instance = new PostApiClient();
        }
        return instance;
    }


    public void getPostsByPage(int page, int pageSize, PostCallback callback) {
        if (retrievePostsByPageRunnable != null) {
            retrievePostsByPageRunnable = null;
        }
        retrievePostsByPageRunnable = new RetrievePostsByPageRunnable(page, pageSize, callback);
        final Future<?> myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrievePostsByPageRunnable);

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public LiveData<List<PostModel>> getPosts() {
        return mPosts;
    }
    public LiveData<PostModel> getNewPost() {
        return newPost;
    }
    public void getAllPosts() {

        if (retrievePostsRunnable != null) {
            retrievePostsRunnable = null;
        }
        retrievePostsRunnable = new RetrievePostsRunnable();
        final Future<?> myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrievePostsRunnable);

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void createNowPost(PostModel postModel) {
        if (createNewPostsRunnable != null) {
            createNewPostsRunnable = null;
        }
        createNewPostsRunnable = new CreateNewPostsRunnable(postModel);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(createNewPostsRunnable);

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

    public void updatePostInformation(String idPost,PostModel postModel) {
        if (updatePostInformationRunnable != null) {
            updatePostInformationRunnable = null;
        }
        updatePostInformationRunnable = new UpdatePostInformationRunnable(idPost,postModel);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(updatePostInformationRunnable);

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
    public void deletePost(String idPost) {
        if (deletePostRunnable != null) {
            deletePostRunnable = null;
        }
        deletePostRunnable = new DeletePostRunnable(idPost);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(deletePostRunnable);

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
        private boolean cancelRequest;

        public RetrievePostsRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<Map<String, PostModel>> response = getPosts().execute();

                if (cancelRequest) {
                    return;
                }

                if (response.isSuccessful()) {
                    Map<String, PostModel> list = response.body();
                    mPosts.postValue(new ArrayList<>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mPosts.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mPosts.postValue(null);
            }
        }

        private Call<Map<String, PostModel>> getPosts() {
            return DataProvider.getInstance().friendVerseAPI.getListOfPost();
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }

    private class RetrievePostsByPageRunnable implements Runnable {
        private int page;
        private int pageSize;
        private boolean cancelRequest;
        private PostCallback callback;

        public RetrievePostsByPageRunnable(int page, int pageSize, PostCallback callback) {
            this.page = page;
            this.pageSize = pageSize;
            this.cancelRequest = false;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                Response<Map<String, PostModel>> response = getPostsByPage(page, pageSize).execute();

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

        private Call<Map<String, PostModel>> getPostsByPage(int page, int pageSize) {
            return DataProvider.getInstance().friendVerseAPI.getListOfPostByPage(page, pageSize);
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }
    private class CreateNewPostsRunnable implements Runnable {
        boolean cancelRequest;
        private PostModel newPosts;
        public CreateNewPostsRunnable(PostModel post)
        {
            newPosts = post;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = createNewPost(newPosts).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response " + response.body());

                if (response.isSuccessful()) {
                   PostModel post= (PostModel) response.body();
                   newPost.setValue(post);
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

        private  Call<PostModel> createNewPost( PostModel post) {
            return DataProvider.getInstance().friendVerseAPI.createNewPost(post);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Posts Request");
            cancelRequest = true;
        }
    }

    private class UpdatePostInformationRunnable implements Runnable {
        boolean cancelRequest;
        private PostModel newPosts;
        private String idPost;
        public UpdatePostInformationRunnable(String idPost,PostModel post)
        {
            this.idPost= idPost;
            newPosts = post;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = updatePostInformation(idPost,newPosts).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response " + response.body());

                if (response.isSuccessful()) {
                    PostModel post= (PostModel) response.body();
                    newPost.setValue(post);
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

        private Call<PostModel> updatePostInformation(String idPost,  PostModel updatePost) {
            return DataProvider.getInstance().friendVerseAPI.updatePostInformation(idPost, updatePost);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Posts Request");
            cancelRequest = true;
        }
    }
    private class DeletePostRunnable implements Runnable {
        boolean cancelRequest;

        private String idPost;
        public DeletePostRunnable(String idPost)
        {
            this.idPost= idPost;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = deletePost(idPost).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response " + response.body());

                if (response.isSuccessful()) {
                    PostModel post= (PostModel) response.body();
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

        private  Call<Void> deletePost( String idPost) {
            return DataProvider.getInstance().friendVerseAPI.deletePost(idPost);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Posts Request");
            cancelRequest = true;
        }
    }

}




