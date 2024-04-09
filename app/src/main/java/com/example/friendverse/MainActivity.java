package com.example.friendverse;

import static com.example.friendverse.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.friendverse.Fragment.HomeFragment;
import com.example.friendverse.Fragment.NotificationFragment;
import com.example.friendverse.Fragment.NotifyFragment;
import com.example.friendverse.Fragment.ProfileFragment;
import com.example.friendverse.Fragment.ReelFragment;
import com.example.friendverse.Fragment.SearchFragment;
import com.example.friendverse.Fragment.WatchFragment;
import com.example.friendverse.Login.LoginActivity;
import com.example.friendverse.Login.StartActivity;
import com.example.friendverse.Login.StartUpActivity;
import com.example.friendverse.Model.User;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Response.UserListResponse;
import com.example.friendverse.Resquest.Service;
import com.example.friendverse.Utils.FriendVerseAPI;
import com.example.friendverse.ViewModel.UserListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public Fragment selectedFragment = null;

    //    // User
    FirebaseAuth auth;
//    //

    private UserListViewModel userListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListViewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        //Calling the observers
        ObserveAnyChange();
        searchUserApi("manDuong");
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
            return;
        }
        setContentView(layout.activity_main);

        bottomNavigationView = findViewById(id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);


        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String publisher = intent.getString("profileid");
            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            Bundle bundle = new Bundle();
            bundle.putString("profileid", publisher);
            bundle.putString("isClose", "1");

            Fragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

    }

    private BottomNavigationView.OnItemSelectedListener navigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case id.nav_home:
                selectedFragment = new HomeFragment();
                HomeFragment.position = 0;
                break;
            case id.nav_search:
                selectedFragment = new SearchFragment();
                break;
            case id.nav_watch:
                selectedFragment = new ReelFragment();
                break;
            case id.nav_notify:
                selectedFragment = new NotificationFragment();
                break;
            case id.nav_profile:
                Bundle passData = new Bundle();
                passData.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                selectedFragment = new ProfileFragment();
                selectedFragment.setArguments(passData);
                break;
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, selectedFragment).commit();
        }
        return true;
    };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
            finishAffinity();
            return;
        }
    }

    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null)
            return;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
        reference.child(User.ACTIVITYKEY).setValue(0);

        getAllUser();
    }

    //Observing any data change
    private void ObserveAnyChange(){
        userListViewModel.getUsers().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                if (userModels!=null){
                    try {
                        for (UserModel userModel:userModels){
                            Log.v("Tag", "onChange: "+ userModel.getUsername()+"\n");
                            System.out.println("USer: "+userModel.getUsername());
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    private void getAllUser() {
        FriendVerseAPI friendVerseAPI = Service.getInstance().friendVerseAPI;
        Call<Map<String, UserModel>> responseCall = friendVerseAPI.getListOfUser();
        responseCall.enqueue(new Callback<Map<String, UserModel>>() {
            @Override
            public void onResponse(Call<Map<String, UserModel>> call, Response<Map<String, UserModel>> response) {
                if (response.isSuccessful()) {
                    System.out.println("The response: " + response.body());
//                    List<UserModel> users = new ArrayList<>(response.body().getUserList()) ;
//                    for (UserModel user : users) {
//                        System.out.println("userId: " + user.getId());
//                    }
//                   UserListResponse users = response.body();

//                    System.out.println("New Map: "+ users.userList);
                }
                else{
                    try {
                        System.out.println("Error "+ response.errorBody().string());
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, UserModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void searchUserApi(String query){
        userListViewModel.searchUserApi(query);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
            reference.child(User.ACTIVITYKEY).setValue(0);
        } catch (Exception ex) {
            Log.e("TAG", "User not login");
        }
    }
}
