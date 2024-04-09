package com.example.friendverse.Response;

import com.example.friendverse.Models.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class UserListResponse {
    public Map<String,UserModel> userList;
    public Map<String,UserModel> getUserList(){
        return this.userList;
    }
}
