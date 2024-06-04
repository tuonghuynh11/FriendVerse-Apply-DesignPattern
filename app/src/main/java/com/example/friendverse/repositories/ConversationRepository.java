package com.example.friendverse.repositories;

import androidx.lifecycle.LiveData;

import com.example.friendverse.Models.ConversationsModel;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Resquest.ConversationApiClient;
import com.example.friendverse.Resquest.PostApiClient;

import java.util.List;

public class ConversationRepository {
    private static ConversationRepository instance;

    //LiveData
    private ConversationApiClient conversationApiClient;

    //Constructor
    private ConversationRepository(){
        conversationApiClient= ConversationApiClient.getInstance();
    }
    public static ConversationRepository getInstance(){
        if(instance==null){
            instance= new ConversationRepository();
        }
        return instance;
    }
    public LiveData<List<ConversationsModel>> getConversations(){return conversationApiClient.getConversations();}
    public void getAllConversations() {
        conversationApiClient.getConversations();
    }

    public LiveData<ConversationsModel> createNewConversation(ConversationsModel conversationsModel){
        conversationApiClient.createNewConversationApi(conversationsModel);
        return conversationApiClient.getNewConversation();
    }
    public LiveData<ConversationsModel> updateConversationMessage(String idConversation,String message){
        conversationApiClient.updateConversationMessageApi(idConversation,message);
        return conversationApiClient.getNewConversation();
    }
    public void deleteConversation(String idConversation){
        conversationApiClient.deleteConversationApi(idConversation);
    }
}
