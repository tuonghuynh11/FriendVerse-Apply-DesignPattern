package com.example.friendverse.repositories;

import androidx.lifecycle.LiveData;

import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.ReportModel;
import com.example.friendverse.Resquest.PostApiClient;
import com.example.friendverse.Resquest.ReportApiClient;

import java.util.List;

public class ReportRepository {
    private static ReportRepository instance;

    //LiveData
    private ReportApiClient reportApiClient;

    //Constructor
    private ReportRepository(){
        reportApiClient= ReportApiClient.getInstance();
    }
    public static ReportRepository getInstance(){
        if(instance==null){
            instance= new ReportRepository();
        }
        return instance;
    }
    public LiveData<List<ReportModel>> getReports(){return reportApiClient.getReports();}
    public void getAllReports() {
        reportApiClient.getReportApi();
    }

    public LiveData<ReportModel> createNewReport(ReportModel reportModel){
        reportApiClient.createNewReportApi(reportModel);
        return reportApiClient.getNewReport();
    }
    public void deleteReport(String idReport){
        reportApiClient.deleteReportApi(idReport);
    }
}
