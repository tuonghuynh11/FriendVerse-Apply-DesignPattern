package com.example.friendverse.Model.Mediator;

import android.app.AlertDialog;
import android.os.Bundle;

import java.util.HashMap;

public interface NavigationMediator {
    void notify(int itemId);
    void notify(Bundle intentExtra);
    void notify(AlertDialog.Builder builder, String mediaTypeAndBehavior);
}

