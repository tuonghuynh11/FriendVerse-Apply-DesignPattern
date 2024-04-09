package com.example.friendverse.Utils;

import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Response.UserListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendVerseAPI {
    /*
     * USER API
     * */
    @GET("/Users.json")
    Call<Map<String, UserModel>> getListOfUser();

    @GET("/Users.json")
    Call<Map<String, UserModel>> searchUserByUserName(@Query("orderBy") String orderBy, @Query("equalTo") String userName);
    /*
     * USER API
     * */

    /*
     * POST API
     * */
    @GET("/Posts.json")
    Call<Map<String, PostModel>> getListOfPost();
    /*
     * POST API
     * */

}
