package com.example.friendverse.Model.Mediator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import com.example.friendverse.Fragment.FriendMapsFragment;
import com.example.friendverse.FriendMapActivity;
import com.example.friendverse.R;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;

public class MapNavigationMediator implements NavigationMediator{
    FriendMapActivity mapActivity;
    public MapNavigationMediator(FriendMapActivity activity){
        mapActivity = activity;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void notify(int itemId) {
        switch (itemId) {
            case R.id.normal:
                ((FriendMapsFragment)mapActivity.getSupportFragmentManager().findFragmentByTag("GGMAP")).map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                ((FriendMapsFragment) mapActivity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                ((FriendMapsFragment) mapActivity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                ((FriendMapsFragment) mapActivity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.none:
                ((FriendMapsFragment) mapActivity.getSupportFragmentManager().findFragmentById(R.id.map)).map.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }

    @Override
    public void notify(Bundle intentExtra) {

    }

    @Override
    public void notify(AlertDialog.Builder builder, String media) {

    }


}
