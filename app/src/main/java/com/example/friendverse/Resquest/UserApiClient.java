package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class UserApiClient {
    //LiveData
    private MutableLiveData<List<UserModel>> mUsers;

    private static UserApiClient instance;


    //making Global Runnable
    private RetrieveUsersRunnable retrieveUsersRunnable;

    private UserApiClient() {
        mUsers = new MutableLiveData<>();
    }

    public static UserApiClient getInstance() {
        if (instance == null) {
            instance = new UserApiClient();
        }
        return instance;
    }

    public LiveData<List<UserModel>> getUsers() {
        return mUsers;
    }

    public void searchUserApi(String query) {
        if (retrieveUsersRunnable != null) {
            retrieveUsersRunnable = null;
        }
        retrieveUsersRunnable = new RetrieveUsersRunnable(query);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrieveUsersRunnable);

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

    private class RetrieveUsersRunnable implements Runnable {
        private String query;
        boolean cancelRequest;

        public RetrieveUsersRunnable(String query) {
            this.query = query;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getUsers(query).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    Map<String, UserModel> list = (Map<String, UserModel>) response.body();
                    mUsers.postValue(new ArrayList<UserModel>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);

                e.printStackTrace();
                mUsers.postValue(null);
            }


            //Search Method /query

        }

        private Call<Map<String, UserModel>> getUsers(String query) {
            return Service.getInstance().friendVerseAPI.searchUserByUserName('"'+"username"+'"','"'+query+'"');
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
}
