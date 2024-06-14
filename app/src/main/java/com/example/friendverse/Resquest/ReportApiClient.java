package com.example.friendverse.Resquest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.friendverse.Executors.AppExecutors;
import com.example.friendverse.Models.ReportModel;
import com.example.friendverse.Models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ReportApiClient {
    //LiveData
    private MutableLiveData<List<ReportModel>> mReports;
    private MutableLiveData<ReportModel> newReport;

    private static ReportApiClient instance;


    //making Global Runnable
    private RetrieveReportsRunnable retrieveReportsRunnable;
    private CreateNewReportRunnable createNewReportRunnable;
    private DeleteReportRunnable deleteReportRunnable;

    private ReportApiClient() {
        mReports = new MutableLiveData<>();
        newReport = new MutableLiveData<>();
    }

    public static ReportApiClient getInstance() {
        if (instance == null) {
            instance = new ReportApiClient();
        }
        return instance;
    }

    public LiveData<List<ReportModel>> getReports() {
        return mReports;
    }
    public LiveData<ReportModel> getNewReport() {
        return newReport;
    }

    public void getReportApi() {
        if (retrieveReportsRunnable != null) {
            retrieveReportsRunnable = null;
        }
        retrieveReportsRunnable = new RetrieveReportsRunnable();
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(retrieveReportsRunnable);

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
    public void createNewReportApi(ReportModel reportModel) {
        if (createNewReportRunnable != null) {
            createNewReportRunnable = null;
        }
        createNewReportRunnable = new CreateNewReportRunnable(reportModel);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(createNewReportRunnable);

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
    public void deleteReportApi(String idReport) {
        if (deleteReportRunnable != null) {
            deleteReportRunnable = null;
        }
        deleteReportRunnable = new DeleteReportRunnable(idReport);
        // Call  get Data from API
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(deleteReportRunnable);

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

    private class RetrieveReportsRunnable implements Runnable {
        boolean cancelRequest;

        public RetrieveReportsRunnable() {
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getReports().execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    Map<String, ReportModel> list = (Map<String, ReportModel>) response.body();
                    mReports.postValue(new ArrayList<ReportModel>(list.values()));
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);

                e.printStackTrace();
                mReports.postValue(null);
            }


            //Search Method /query

        }

        private Call<Map<String, ReportModel>> getReports() {
            return DataProvider.getInstance().friendVerseAPI.getListOfReports();
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }

    private class CreateNewReportRunnable implements Runnable {
        private ReportModel reportModel;
        boolean cancelRequest;

        public CreateNewReportRunnable(ReportModel reportModel) {
            this.reportModel =reportModel ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = createNewReport(reportModel).execute();

                if (cancelRequest) {
                    return;
                }
                System.out.println("Response "+ response.body());

                if (response.isSuccessful()) {
                    ReportModel reportModel1 = (ReportModel) response.body();
                    newReport.setValue(reportModel1);
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                }

            } catch (IOException e) {
                System.out.println("Request Err "+ e);

                e.printStackTrace();
                newReport.postValue(null);
            }


            //Search Method /query

        }

        private  Call<ReportModel> createNewReport( ReportModel report) {
            return DataProvider.getInstance().friendVerseAPI.createNewReport(report);
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }


    private class DeleteReportRunnable implements Runnable {
        private String idReport;
        boolean cancelRequest;

        public DeleteReportRunnable(String idReport) {
            this.idReport =idReport ;
//            this.cancelRequest = cancelRequest;
            cancelRequest=false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = deleteReport(idReport).execute();

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


        private   Call<Void> deleteReport( String idReport){
            return DataProvider.getInstance().friendVerseAPI.deleteReport(idReport);
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search User Request");
            cancelRequest = true;
        }
    }
}
