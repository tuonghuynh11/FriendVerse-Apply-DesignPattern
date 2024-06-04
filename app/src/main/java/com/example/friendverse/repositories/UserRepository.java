package com.example.friendverse.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Model.User;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Resquest.UserApiClient;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    //LiveData
    private UserApiClient userApiClient;

    //Constructor
    private UserRepository(){
        userApiClient= UserApiClient.getInstance();
    }
    public static UserRepository getInstance(){
        if(instance==null){
            instance= new UserRepository();
        }
        return instance;
    }
    public LiveData<List<UserModel>> getUsers(){return userApiClient.getUsers();}
    public void searchUserApi(String query) {
        userApiClient.searchUserApi(query);
    }

    public LiveData<UserModel> createNewUser(UserModel userModel){
         userApiClient.registerUserApi(userModel);
         return userApiClient.getRegisteredUser();
    }
    public LiveData<UserModel> updateUserInformation(UserModel userModel){
        userApiClient.updateUserInformationApi(userModel.getId(),userModel);
        return userApiClient.getRegisteredUser();
    }

    public void deleteUser(String idUser){
        userApiClient.deleteUserApi(idUser);
    }
}
