package com.example.friendverse.Utils;

import com.example.friendverse.Models.CommentModel;
import com.example.friendverse.Models.ConversationsModel;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.ReportModel;
import com.example.friendverse.Models.UserModel;
import com.example.friendverse.Response.UserListResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FriendVerseAPI {
    /*
     * USER API
     * */
    @GET("/Users.json")
    Call<Map<String, UserModel>> getListOfUser();

    @GET("/Users.json")
    Call<Map<String, UserModel>> searchUserByUserName(@Query("orderBy") String orderBy, @Query("equalTo") String userName);

    @POST("/Users.json")
    Call<UserModel> register(@Body UserModel user);
    @PUT("/Users/{id}.json")
    Call<UserModel> updateUserInformation(@Path("id") String idUser, @Body UserModel updateUser);

    @DELETE("/Users/{id}.json")
    Call<Void> deleteUser(@Path("id") String idUser);
    /*
     * USER API
     * */

    /*
     * POST API
     * */
    @GET("/Posts.json")
    Call<Map<String, PostModel>> getListOfPost();
    @POST("/Posts.json")
    Call<PostModel> createNewPost(@Body PostModel post);

    @PUT("/Posts/{id}.json")
    Call<PostModel> updatePostInformation(@Path("id") String idPost, @Body PostModel updatePost);

    @DELETE("/Posts/{id}.json")
    Call<Void> deletePost(@Path("id") String idPost);
    /*
     * POST API
     * */


    /*
     * REPORTS API
     * */
    @GET("/Reports.json")
    Call<Map<String, ReportModel>> getListOfReports();
    @POST("/Reports.json")
    Call<ReportModel> createNewReport(@Body ReportModel report);

    @DELETE("/Reports/{id}.json")
    Call<Void> deleteReport(@Path("id") String idReport);
    /*
     * REPORTS API
     * */

    /*
     * CONVERSATIONS API
     * */
    @GET("/conversations.json")
    Call<Map<String, ConversationsModel>> getListOfConversations();
    @POST("/conversations.json")
    Call<ConversationsModel> createNewConversations(@Body ConversationsModel conversation);

    @PATCH("/conversations/{id}/receiverName.json")
    Call<ConversationsModel> updateMessage(@Path("id") String idConversation, @Body String newMessage);

    @DELETE("/conversations/{id}.json")
    Call<Void> deleteConversation(@Path("id") String idConversation);

    /*
     * CONVERSATIONS API
     * */


}
