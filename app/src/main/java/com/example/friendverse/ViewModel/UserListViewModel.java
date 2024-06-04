package com.example.friendverse.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.friendverse.Models.UserModel;
import com.example.friendverse.repositories.UserRepository;

import java.util.List;

public class UserListViewModel extends ViewModel {

    private UserRepository userRepository;
    //Constructor
    public UserListViewModel() {
        userRepository = UserRepository.getInstance();
    }

    public LiveData<List<UserModel>> getUsers() {
        return this.userRepository.getUsers();
    }
    public void searchUserApi(String query) {
        userRepository.searchUserApi(query);
    }
    public LiveData<UserModel> createNewUser(UserModel userModel){
       return userRepository.createNewUser(userModel);
    }
    public LiveData<UserModel> updateUserInformation(UserModel userModel){
        return userRepository.updateUserInformation(userModel);
    }
    public void deleteUser(String idUser){
        userRepository.deleteUser(idUser);
    }
}
