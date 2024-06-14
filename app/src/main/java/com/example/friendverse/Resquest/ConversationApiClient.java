package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.ConversationsModel;
import com.example.friendverse.Models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ConversationApiClient {
    private MutableLiveData<List<ConversationsModel>> mConversations;
    private MutableLiveData<ConversationsModel> newConversation;

    private static ConversationApiClient instance;


    //making Global Runnable
    private RetrieveConservationsRunnable retrieveConservationsRunnable;
    private CreateNewConservationsRunnable createNewConservationsRunnable;
    private UpdateConversationMessageRunnable updateConversationMessageRunnable;
    private DeleteConversationRunnable deleteConversationRunnable;


    private ConversationApiClient() {
        mConversations = new MutableLiveData<>();
        newConversation = new MutableLiveData<>();
    }

    public static ConversationApiClient getInstance() {
        if (instance == null) {
            instance = new ConversationApiClient();
        }
        return instance;
    }

    public LiveData<List<ConversationsModel>> getConversations() {
        return mConversations;
    }
    public LiveData<ConversationsModel> getNewConversation() {
        return newConversation;
    }

    public void getListConservationApi() {
        if (retrieveConservationsRunnable != null) {
            retrieveConservationsRunnable = null;
        }
        retrieveConservationsRunnable = new RetrieveConservationsRunnable();
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrieveConservationsRunnable);

        // Call set timeout for api session call (set timeout cho phiên gọi api
        // nếu quá lâu không phản hồi)

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }
    public void createNewConversationApi(ConversationsModel conversationsModel) {
        if (createNewConservationsRunnable != null) {
            createNewConservationsRunnable = null;
        }
        createNewConservationsRunnable = new CreateNewConservationsRunnable(conversationsModel);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(createNewConservationsRunnable);

        // Call set timeout for api session call (set timeout cho phiên gọi api
        // nếu quá lâu không phản hồi)

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }

    public void updateConversationMessageApi(String idConversation,String message) {
        if (updateConversationMessageRunnable != null) {
            updateConversationMessageRunnable = null;
        }
        updateConversationMessageRunnable = new UpdateConversationMessageRunnable(idConversation, message);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(updateConversationMessageRunnable);

        // Call set timeout for api session call (set timeout cho phiên gọi api
        // nếu quá lâu không phản hồi)

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }
    public void deleteConversationApi(String idConversation) {
        if (deleteConversationRunnable != null) {
            deleteConversationRunnable = null;
        }
        deleteConversationRunnable = new DeleteConversationRunnable(idConversation);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(deleteConversationRunnable);

        // Call set timeout for api session call (set timeout cho phiên gọi api
        // nếu quá lâu không phản hồi)

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);

    }
    //Retrieving Data from RestAPI bu runnable class

    private class RetrieveConservationsRunnable implements Runnable {
        boolean cancelRequest;

        public RetrieveConservationsRunnable() {
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getListOfConversations().execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    Map<String, ConversationsModel> list = (Map<String, ConversationsModel>) response.body();
                    mConversations.postValue(new ArrayList<ConversationsModel>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);

                e.printStackTrace();
                mConversations.postValue(null);
            }


            //Search Method /query

        }

        private Call<Map<String, ConversationsModel>> getListOfConversations() {
            return DataProvider.getInstance().friendVerseAPI.getListOfConversations();
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }

    private class CreateNewConservationsRunnable implements Runnable {
        private ConversationsModel conversationsModel;
        boolean cancelRequest;

        public CreateNewConservationsRunnable(ConversationsModel conversationsModel) {
            this.conversationsModel =conversationsModel ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = createNewConversations(conversationsModel).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    ConversationsModel conversationsModel1 = (ConversationsModel) response.body();
                    newConversation.setValue(conversationsModel1);
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);

                e.printStackTrace();
                newConversation.setValue(null);
            }


        }

        private    Call<ConversationsModel> createNewConversations( ConversationsModel conversation) {
            return DataProvider.getInstance().friendVerseAPI.createNewConversations(conversation);
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }

    private class UpdateConversationMessageRunnable implements Runnable {
        private String message;
        private String idConversation;
        boolean cancelRequest;

        public UpdateConversationMessageRunnable(String idConversation,String message) {
            this.idConversation= idConversation;
            this.message =message ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = updateMessage(idConversation,message).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    ConversationsModel conversationsModel = (ConversationsModel) response.body();
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);
                e.printStackTrace();
            }

            //Search Method /query

        }


        private    Call<ConversationsModel> updateMessage( String idConversation,  String newMessage){
            return DataProvider.getInstance().friendVerseAPI.updateMessage(idConversation, newMessage);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
    private class DeleteConversationRunnable implements Runnable {
        private String idConversation;
        boolean cancelRequest;

        public DeleteConversationRunnable(String idConversation) {
            this.idConversation =idConversation ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = deleteConversation(idConversation).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    UserModel user = (UserModel) response.body();
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);
                e.printStackTrace();
            }

            //Search Method /query

        }


        private   Call<Void> deleteConversation( String idConversation){
            return DataProvider.getInstance().friendVerseAPI.deleteConversation(idConversation);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
}
