package com.example.friendverse.Resquest;

import com.example.friendverse.Utils.Credentials;
import com.example.friendverse.Utils.FriendVerseAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataProvider {
    //Singleton Design Pattern
    public FriendVerseAPI friendVerseAPI;
    private static DataProvider instance;
    private DataProvider(){
        Retrofit.Builder retrofitBuilder=new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= retrofitBuilder.build();
        friendVerseAPI= retrofit.create(FriendVerseAPI.class);
    }


    public static DataProvider getInstance(){
        if(instance==null){
            instance= new DataProvider();
        }
        return instance;
    }
}
