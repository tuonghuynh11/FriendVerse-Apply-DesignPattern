package com.example.friendverse.Service;

import com.example.friendverse.Models.PostModel;

import java.util.ArrayList;
import java.util.List;

public interface PostCallback {
    void onError(String e);

    void onSuccess(ArrayList<PostModel> postModels);
}
