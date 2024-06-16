package com.example.friendverse;

import static com.example.friendverse.R.*;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.friendverse.Login.StartActivity;
import com.example.friendverse.Model.Mediator.MainNavigationMediator;
import com.example.friendverse.Model.Mediator.NavigationMediator;
import com.example.friendverse.Model.State.HomeState;
import com.example.friendverse.Model.State.NavigationState;
import com.example.friendverse.Model.State.NotificationState;
import com.example.friendverse.Model.State.ProfileState;
import com.example.friendverse.Model.State.ReelState;
import com.example.friendverse.Model.State.SearchState;
import com.example.friendverse.Model.User;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Resquest.DataProvider;
import com.example.friendverse.Utils.FriendVerseAPI;
import com.example.friendverse.ViewModel.UserListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    NavigationMediator navigationMediator = new MainNavigationMediator(this);
    NavigationState state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = new HomeState(this);
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
//        if (intent != null) {
//            String publisher = intent.getString("profileid");
//            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//            editor.putString("profileid", publisher);
//            editor.apply();
//
//            Bundle bundle = new Bundle();
//            bundle.putString("profileid", publisher);
//            bundle.putString("isClose", "1");
//
//            Fragment fragment = new ProfileFragment();
//            fragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    fragment).commit();
//        } else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new HomeFragment()).commit();
//        }
        navigationMediator.notify(intent);

    }

    private BottomNavigationView.OnItemSelectedListener navigationItemSelectedListener = item -> {
        state.handleNavigationSelected(item.getItemId());
        return true;
    };

    public void setState(NavigationState state) {
        this.state = state;
    }

    public void navigate(){
        state.navigate(getSupportFragmentManager());
    }

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
        FriendVerseAPI friendVerseAPI = DataProvider.getInstance().friendVerseAPI;
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
