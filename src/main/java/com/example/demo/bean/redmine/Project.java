package com.example.demo.bean.redmine;

import java.util.List;

public class Project {
    private int projectId;
    private String projectName;
    private List<Version> versionList;
    private List<Issue> lastIssueList;

    public Project() {

    }

    public Project(int _projectId, String _projectName) {
       this.projectId = _projectId;
       this.projectName = _projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    public List<Issue> getLastIssueList() {
        return lastIssueList;
    }

    public void setLastIssueList(List<Issue> lastIssueList) {
        this.lastIssueList = lastIssueList;
    }
}
