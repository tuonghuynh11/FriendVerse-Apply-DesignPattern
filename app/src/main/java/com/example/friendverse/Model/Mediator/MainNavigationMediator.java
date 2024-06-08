package com.example.friendverse.Model.Mediator;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.friendverse.Fragment.HomeFragment;
import com.example.friendverse.Fragment.NotificationFragment;
import com.example.friendverse.Fragment.ProfileFragment;
import com.example.friendverse.Fragment.ReelFragment;
import com.example.friendverse.Fragment.SearchFragment;
import com.example.friendverse.MainActivity;
import com.example.friendverse.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class MainNavigationMediator implements NavigationMediator{
    MainActivity mainActivity;
    public MainNavigationMediator(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    public void notify(int itemId) {
        Fragment selectedFragment = mainActivity.selectedFragment;
        switch (itemId) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                HomeFragment.position = 0;
                break;
            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;
            case R.id.nav_watch:
                selectedFragment = new ReelFragment();
                break;
            case R.id.nav_notify:
                selectedFragment = new NotificationFragment();
                break;
            case R.id.nav_profile:
                Bundle passData = new Bundle();
                passData.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                selectedFragment = new ProfileFragment();
                selectedFragment.setArguments(passData);
                break;
        }
        if (selectedFragment != null) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }

    }

    @Override
    public void notify(Bundle intentExtra) {
        if (intentExtra != null) {
            String publisher = intentExtra.getString("profileid");
            SharedPreferences.Editor editor = mainActivity.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            Bundle bundle = new Bundle();
            bundle.putString("profileid", publisher);
            bundle.putString("isClose", "1");

            Fragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        } else {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    @Override
    public void notify(AlertDialog.Builder builder, String media) {

    }


}
