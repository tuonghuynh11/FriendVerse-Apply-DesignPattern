package com.example.friendverse.Model.State;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.friendverse.MainActivity;

public abstract class NavigationState {
    MainActivity activity;
    public NavigationState(MainActivity activity){
        this.activity = activity;
    }
    abstract public void handleNavigationSelected(int id);
    abstract public void navigate(FragmentManager manager);
}
