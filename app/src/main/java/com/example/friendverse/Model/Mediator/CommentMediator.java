package com.example.friendverse.Model.Mediator;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.friendverse.Adapter.CommentAdapter;
import com.example.friendverse.CommentActivity;

import java.util.HashMap;

public class CommentMediator implements NavigationMediator{
    CommentAdapter adapter;
    public CommentMediator(CommentAdapter activity){
        adapter = activity;
    }

    @Override
    public void notify(int itemId) {

    }

    @Override
    public void notify(Bundle intentExtra) {

    }

    @Override
    public void notify(AlertDialog.Builder builder, String mediaTypeAndBehavior) {
        builder.setCancelable(true);
        builder.setTitle("Delete comment?");
        builder.setMessage("Do you want to delete?");
    }


}
