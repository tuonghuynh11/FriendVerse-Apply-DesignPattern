package com.example.friendverse.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.friendverse.Models.ConversationsModel;
import com.example.friendverse.Models.ReportModel;
import com.example.friendverse.repositories.ConversationRepository;
import com.example.friendverse.repositories.ReportRepository;

import java.util.List;

public class ConversationViewModel {
    private ConversationRepository conversationRepository;
    //Constructor
    public ConversationViewModel() {
        conversationRepository = ConversationRepository.getInstance();
    }

    public LiveData<List<ConversationsModel>> getConversations() {
        return this.conversationRepository.getConversations();
    }
    public void getAllConversations() {
        conversationRepository.getAllConversations();
    }


    public LiveData<ConversationsModel> createNewConversation(ConversationsModel conversationsModel){
        return conversationRepository.createNewConversation(conversationsModel);
    }
    public LiveData<ConversationsModel> updateConversationMessage(String idConversation,String message){
        return conversationRepository.updateConversationMessage(idConversation,message);
    }
    public void deleteConversation(String idConversation){
        conversationRepository.deleteConversation(idConversation);
    }
}
