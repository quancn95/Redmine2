package com.example.demo.util;

import com.example.demo.bean.redmine.Issue;
import com.example.demo.bean.redmine.Project;
import com.example.demo.bean.redmine.Version;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndlong on 01/07/2019.
 */

public class RedmineAPIUtils {

    private static List<Project> projectPSList = new ArrayList<>();

    static {
        //http://apis.ifisolution.local:8080/projects.json
        projectPSList.add(new Project(190, "Energy Modeling"));
        projectPSList.add(new Project(188, "Financial Modeling"));
        projectPSList.add(new Project(191, "Administration"));
        projectPSList.add(new Project(189, "Analysis"));
    }

    public static void main(String[] args){
        List<Issue> isList = getAllIssue(21);
        System.out.println(isList.size());
        for(Issue issue : isList){
            System.out.print(issue.getIssueId());
            System.out.print(" ");
            System.out.print(issue.getStatus());
            System.out.print(" ");
            System.out.print(issue.getPriority());
            System.out.println();
        }
    }

    public static List<Issue> getAllIssue(int sprint) {
        projectPSList = getAllTargetVersion(projectPSList);
        List<Version> versionOfSprintList = new ArrayList<>();
        for (Project project:projectPSList) {
            List<Version> versionList = project.getVersionList();
            for(Version version : versionList){
                if(version.getVersionName().contains(String.valueOf(sprint))){
                    versionOfSprintList.add(version);
                }
            }
        }
        List<Issue> issueList = getAllIssueByVersion(versionOfSprintList);
        return issueList;
    }

    private static List<Issue> getAllIssueByVersion(List<Version> versionList) {

        List<Issue> issueList = new ArrayList<>();
        //http://apis.ifisolution.local:8080/issues.json?project_id=187&fixed_version_id=1326
        for (Version version : versionList) {
            String path = "issues.json?project_id=" + version.getProjectId() + "&limit=100&fixed_version_id=" + version.getVersionId() + "&status_id=*";
            String js = getJSONFromRedmine(path);
            JsonObject jsonObject = new JsonParser().parse(js).getAsJsonObject();
            JsonArray issueArray = (JsonArray) jsonObject.get("issues");

            for (int i = 0; i < issueArray.size(); i++) {
                JsonObject issueObj = (JsonObject) issueArray.get(i);
                String tracker = JsonUtils.getChildOfJson(issueObj, "tracker", "name");
                String status = JsonUtils.getChildOfJson(issueObj, "status", "name");
                String priority = JsonUtils.getChildOfJson(issueObj, "priority", "name");
                String id = JsonUtils.getChildOfJson(issueObj, "id");
                Issue issue = new Issue();
                issue.setTracker(tracker);
                issue.setStatus(status);
                issue.setPriority(priority);
                issue.setIssueId(Integer.parseInt(id));
                issue.setAssigned_to(JsonUtils.getChildOfJson(issueObj, "assigned_to", "name"));
                issueList.add(issue);
            }
        }
        return issueList;
    }

    private static List<Project> getAllTargetVersion(List<Project> projectList) {
        for (Project project : projectList) {
            String path = "projects/" + project.getProjectId() + "/versions.json";
            String js = getJSONFromRedmine(path);
            JsonObject jsonObject = new JsonParser().parse(js).getAsJsonObject();
            JsonArray issueArray = (JsonArray) jsonObject.get("versions");
            List<Version> versionList = new ArrayList<>();
            for (int i = 0; i < issueArray.size(); i++) {
                JsonObject issue = (JsonObject) issueArray.get(i);
                String name = JsonUtils.getChildOfJson(issue, "name");
                String due_date = JsonUtils.getChildOfJson(issue, "due_date");
                String id = JsonUtils.getChildOfJson(issue, "id");
                Version version = new Version();
                version.setVersionName(name);
                version.setDueName(due_date);
                version.setProjectId(project.getProjectId());
                version.setVersionId(Integer.parseInt(id));
                versionList.add(version);
            }
            project.setVersionList(versionList);
        }
        return projectList;
    }

    private static String getJSONFromRedmine(String path) {
        String baseUrl = "http://apis.ifisolution.local:8080/";
        String username = "ndlong";
        String password = "Ndl@123456";
        RESTInvoker rest = new RESTInvoker(baseUrl, username, password);
        return rest.getDataFromServer(path);
    }


}
