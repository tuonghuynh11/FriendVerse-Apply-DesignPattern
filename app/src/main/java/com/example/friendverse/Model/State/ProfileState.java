package com.example.friendverse.Model.State;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.friendverse.Fragment.NotificationFragment;
import com.example.friendverse.Fragment.ProfileFragment;
import com.example.friendverse.MainActivity;
import com.example.friendverse.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileState extends NavigationState{

    public ProfileState(MainActivity activity) {
        super(activity);
    }

    @Override
    public void handleNavigationSelected(int id) {
        if(id != R.id.nav_profile) {
            switch(id){
                case R.id.nav_search:
                    activity.setState(new SearchState(activity));
                    break;
                case R.id.nav_notify:
                    activity.setState(new NotificationState(activity));
                    break;
                case R.id.nav_home:
                    activity.setState(new HomeState(activity));
                    break;
                case R.id.nav_watch:
                    activity.setState(new ReelState(activity));
                    break;
            }
            activity.navigate();
        }
    }

    @Override
    public void navigate(FragmentManager manager) {
        Bundle passData = new Bundle();
        passData.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        Fragment selectedFragment = new ProfileFragment();
        selectedFragment.setArguments(passData);
        manager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }
}
