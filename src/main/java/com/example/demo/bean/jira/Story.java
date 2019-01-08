package com.example.demo.bean.jira;

public class Story {
    private String key;
    private String summary;
    private String type;
    private String status;

    public Story(String _key, String _summary, String _type, String _status) {
        this.key = _key;
        this.summary = _summary;
        this.type = _type;
        this.status = _status;
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
