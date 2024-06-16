package com.example.friendverse.Model.State;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.friendverse.Fragment.NotificationFragment;
import com.example.friendverse.Fragment.ReelFragment;
import com.example.friendverse.Fragment.SearchFragment;
import com.example.friendverse.MainActivity;
import com.example.friendverse.R;

public class ReelState extends NavigationState {


    public ReelState(MainActivity activity) {
        super(activity);
    }

    @Override
    public void handleNavigationSelected(int id) {
        if(id != R.id.nav_watch) {
            switch(id){
                case R.id.nav_search:
                    activity.setState(new SearchState(activity));
                    break;
                case R.id.nav_notify:
                    activity.setState(new NotificationState(activity));
                    break;
                case R.id.nav_profile:
                    activity.setState(new ProfileState(activity));
                    break;
                case R.id.nav_home:
                    activity.setState(new HomeState(activity));
                    break;
            }
            activity.navigate();
        }
    }

    @Override
    public void navigate(FragmentManager manager) {
        manager.beginTransaction().replace(R.id.fragment_container, new ReelFragment()).commit();
    }
}
