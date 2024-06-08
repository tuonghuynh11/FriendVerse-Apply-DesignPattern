package com.example.friendverse.Model.Mediator;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.friendverse.AddPost;
import com.example.friendverse.AddReelActivity;
import com.example.friendverse.Fragment.FriendMapsFragment;
import com.example.friendverse.Fragment.HomeFragment;
import com.example.friendverse.Fragment.NotificationFragment;
import com.example.friendverse.Fragment.ProfileFragment;
import com.example.friendverse.Fragment.ReelFragment;
import com.example.friendverse.Fragment.SearchFragment;
import com.example.friendverse.FriendMapActivity;
import com.example.friendverse.MainActivity;
import com.example.friendverse.R;
import com.example.friendverse.StoryActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class ConcreteNavigationMediator implements NavigationMediator{
    AppCompatActivity activity;
    public ConcreteNavigationMediator(AppCompatActivity appCompatActivity){
        activity = appCompatActivity;
    }
    @Override
    public void notify(int itemId) {
        if(activity instanceof MainActivity){
            Fragment selectedFragment = ((MainActivity) activity).selectedFragment;
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
                ((MainActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
        }else if(activity instanceof FriendMapActivity){
            switch (itemId) {
                case R.id.normal:
                    ((FriendMapsFragment)activity.getSupportFragmentManager().findFragmentByTag("GGMAP")).map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.satellite:
                    ((FriendMapsFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.hybrid:
                    ((FriendMapsFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case R.id.terrain:
                    ((FriendMapsFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                case R.id.none:
                    ((FriendMapsFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_NONE);
                    break;
            }
        }
    }

    @Override
    public void notify(Bundle intentExtra) {
        if (intentExtra != null) {
            String publisher = intentExtra.getString("profileid");
            SharedPreferences.Editor editor = activity.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            Bundle bundle = new Bundle();
            bundle.putString("profileid", publisher);
            bundle.putString("isClose", "1");

            Fragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        } else {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    @Override
    public void notify(AlertDialog.Builder builder, String mediaTypeAndBehavior) {
        if(activity instanceof AddPost || activity instanceof AddReelActivity){
            builder.setTitle("Change" + mediaTypeAndBehavior);
            builder.setMessage("Do you want to change " + mediaTypeAndBehavior + "?");
            builder.setCancelable(true);
        }
        else if(activity instanceof StoryActivity){
            builder.setCancelable(true);
            builder.setTitle("Delete?");
            builder.setMessage("Do you want to delete?");
        }
    }


}
