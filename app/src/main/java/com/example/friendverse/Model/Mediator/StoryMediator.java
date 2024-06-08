package com.example.friendverse.Model.Mediator;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.friendverse.StoryActivity;

import java.util.HashMap;

public class StoryMediator implements NavigationMediator{
    StoryActivity storyActivity;
    public StoryMediator(StoryActivity activity){
        storyActivity = activity;
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
        builder.setTitle("Delete?");
        builder.setMessage("Do you want to delete?");
    }


}
