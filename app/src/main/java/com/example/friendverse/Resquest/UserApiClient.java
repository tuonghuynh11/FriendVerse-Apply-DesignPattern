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
    private MutableLiveData<UserModel> newUser;

    private static UserApiClient instance;


    //making Global Runnable
    private RetrieveUsersRunnable retrieveUsersRunnable;
    private RegisterRunnable registerRunnable;
    private UpdateUserInformationRunnable updateUserInformationRunnable;
    private DeleteUserRunnable deleteUserRunnable;

    private UserApiClient() {
        mUsers = new MutableLiveData<>();
        newUser = new MutableLiveData<>();
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
    public LiveData<UserModel> getRegisteredUser() {
        return newUser;
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
    public void registerUserApi(UserModel user) {
        if (registerRunnable != null) {
            registerRunnable = null;
        }
        registerRunnable = new RegisterRunnable(user);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(registerRunnable);

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

    public void updateUserInformationApi(String idUser, UserModel user) {
        if ( updateUserInformationRunnable!= null) {
            updateUserInformationRunnable = null;
        }
        updateUserInformationRunnable = new UpdateUserInformationRunnable(idUser,user);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(updateUserInformationRunnable);

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

    public void deleteUserApi(String idUser) {
        if ( deleteUserRunnable!= null) {
            deleteUserRunnable = null;
        }
        deleteUserRunnable = new DeleteUserRunnable(idUser);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(deleteUserRunnable);

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
            return DataProvider.getInstance().friendVerseAPI.searchUserByUserName('"'+"username"+'"','"'+query+'"');
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }

    private class RegisterRunnable implements Runnable {
        private UserModel newUsers;
        boolean cancelRequest;

        public RegisterRunnable(UserModel newUser) {
            this.newUsers =newUser ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = register(newUsers).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                   UserModel user = (UserModel) response.body();
                    newUser.setValue(user);
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

        private Call<UserModel> register(UserModel userModel) {
            return DataProvider.getInstance().friendVerseAPI.register(userModel);
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }

    private class UpdateUserInformationRunnable implements Runnable {
        private UserModel updateUser;
        private String idUser;
        boolean cancelRequest;

        public UpdateUserInformationRunnable(String idUser,UserModel newUser) {
            this.updateUser =newUser ;
            this.idUser = idUser;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = updateUserInformation(idUser,updateUser).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    UserModel user = (UserModel) response.body();
                    newUser.setValue(user);
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

        private Call<UserModel> updateUserInformation( String idUser,  UserModel updateUser){
            return DataProvider.getInstance().friendVerseAPI.updateUserInformation(idUser,updateUser);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
    private class DeleteUserRunnable implements Runnable {
        private String idUser;
        boolean cancelRequest;

        public DeleteUserRunnable(String idUser) {
            this.idUser =idUser ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = deleteUser(idUser).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    UserModel user = (UserModel) response.body();
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






        private Call<Void> deleteUser( String idUser){
            return DataProvider.getInstance().friendVerseAPI.deleteUser(idUser);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
}
