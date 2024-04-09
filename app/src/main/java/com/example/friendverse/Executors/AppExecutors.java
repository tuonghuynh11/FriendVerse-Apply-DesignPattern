package com.example.friendverse.Executors;

import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Resquest.UserApiClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;
    private AppExecutors(){
    }
    public  static  AppExecutors getInstance(){
        if(instance==null){
            instance=new AppExecutors();
        }
        return instance;
    }

    private  final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService mNetworkIO(){
        return mNetworkIO;
    }

}
