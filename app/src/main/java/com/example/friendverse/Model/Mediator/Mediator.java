package com.example.friendverse.Model.Mediator;


import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public interface Mediator {
    void NotifyFragment(Fragment f);
    void NotifyActivity(AppCompatActivity activity);
    void NotifyAlertBuilder(AlertDialog.Builder builder, String title, String message, boolean cancelable);
}
