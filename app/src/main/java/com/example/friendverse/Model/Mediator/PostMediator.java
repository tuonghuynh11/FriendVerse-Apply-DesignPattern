package com.example.friendverse.Model.Mediator;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.friendverse.AddPost;

import java.util.HashMap;

public class PostMediator implements NavigationMediator{
    AppCompatActivity addPost;

    public PostMediator(AppCompatActivity addPost){
        this.addPost = addPost;
    }
    @Override
    public void notify(int itemId) {

    }

    @Override
    public void notify(Bundle intentExtra) {

    }

    @Override
    public void notify(AlertDialog.Builder alertBuilder, String media) {

        alertBuilder.setTitle("Change " + media);
        alertBuilder.setMessage("Do you want to change " + media + "?");
        alertBuilder.setCancelable(true);
    }


}
