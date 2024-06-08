package com.example.friendverse.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.friendverse.Models.PostModel;
import com.example.friendverse.Models.ReportModel;
import com.example.friendverse.repositories.PostRepository;
import com.example.friendverse.repositories.ReportRepository;

import java.util.List;

public class ReportViewModel {
    private ReportRepository reportRepository;
    //Constructor
    public ReportViewModel() {
        reportRepository = ReportRepository.getInstance();
    }

    public LiveData<List<ReportModel>> getReports() {
        return this.reportRepository.getReports();
    }
    public void getAllReports() {
        reportRepository.getAllReports();
    }


    public LiveData<ReportModel> createNewReport(ReportModel reportModel){
        return reportRepository.createNewReport(reportModel);
    }


    public void deleteReport(String idReport){
        reportRepository.deleteReport(idReport);
    }
}
