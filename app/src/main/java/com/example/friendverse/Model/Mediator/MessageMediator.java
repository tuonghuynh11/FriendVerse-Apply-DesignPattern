package com.example.friendverse.Model.Mediator;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MessageMediator implements Mediator{
    @Override
    public void NotifyFragment(Fragment f) {

    }

    @Override
    public void NotifyActivity(AppCompatActivity activity) {

    }


    @Override
    public void NotifyAlertBuilder(AlertDialog.Builder alertBuilder, String title, String message, boolean cancelable) {
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(cancelable);
    }
}
