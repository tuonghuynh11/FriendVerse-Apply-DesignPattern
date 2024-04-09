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
}
