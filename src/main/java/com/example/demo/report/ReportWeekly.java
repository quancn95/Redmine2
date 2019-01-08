package com.example.demo.report;

import com.example.demo.util.RedmineUtils;

public class ReportWeekly {
    public static void main(String[] args) throws Exception{
        //Redmine

        // Ngay bat dau Sprint
        String sprint_start_date = "2018-12-17";

        // Ngay ket thuc Sprint
        String sprint_end_date = "2019-01-04";
        RedmineUtils.getRedmine(sprint_start_date,sprint_end_date);

//        JiraUtils.getWeeklyReport(21);
    }
}
