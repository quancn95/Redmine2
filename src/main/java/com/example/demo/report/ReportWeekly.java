package com.example.demo.report;

import com.example.demo.bean.redmine.Issue;
import com.example.demo.util.JiraUtils;
import com.example.demo.util.RedmineAPIUtils;

import java.util.List;

public class ReportWeekly {
    public static void main(String[] args) throws Exception {
        //Redmine

        // Ngay bat dau Sprint
        String sprint_start_date = "2019-01-07";

        // Ngay ket thuc Sprint
        String sprint_end_date = "2019-01-18";
//        RedmineUtils.getRedmine(sprint_start_date,sprint_end_date);
        int sprintNumber = 22;

        getRedmineReport(sprintNumber);
        JiraUtils.getWeeklyReport(sprintNumber);
    }

    private static void getRedmineReport(int sprintNumber) {
        List<Issue> issueList = RedmineAPIUtils.getAllIssue(sprintNumber);

        int count_approve = 0;
        int count_pedding = 0;
        int count_assign = 0;
        int count_wip = 0;
        int count_done = 0;
        int count_verifiy = 0;
        int total = 0;

        for (Issue issue : issueList) {
            String statusText = issue.getStatus();
            switch (statusText) {
                case "Closed":
                    count_approve++;
                    break;
                case "Resolved":
                    count_done++;
                    break;
                case "WIP":
                    count_wip++;
                    break;
                case "Fixed and verified":
                    count_verifiy++;
                    break;
                default:
                    String assigned_toText = issue.getAssigned_to();
                    if (assigned_toText.isEmpty()) {
                        count_pedding++;
                    } else {
                        count_assign++;
                    }
            }
        }

        System.out.println(count_approve);
        System.out.println(count_pedding);
        System.out.println(count_assign);
        System.out.println(count_wip);
        System.out.println(count_done);
        System.out.println(count_verifiy);
        System.out.println(count_approve);
        System.out.println("TOTAL:" + total);
        System.out.println("END REDMINE");
    }

}
