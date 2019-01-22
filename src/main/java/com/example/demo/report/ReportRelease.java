package com.example.demo.report;

import com.example.demo.bean.jira.Story;
import com.example.demo.util.JiraUtils;

import java.util.List;

public class ReportRelease {
    public static void main(String[] args) throws Exception {
        //Redmine
        List<Story> storyList = JiraUtils.getJira(22);
        // List OF Upgrade, Task, Support,
        System.out.println("-------TOTAL:----------"+storyList.size());
        System.out.println("-------TASK/BUG/SUPPORT----------");
        for (Story story : storyList) {
            if(!story.getType().equals("Story")){
                printStory(story);
            }
        }
        // New Feature
        System.out.println("-------STORY----------");
        for (Story story : storyList) {
            if(story.getType().equals("Story")){
                printStory(story);
            }
        }

    }

    private static void printStory(Story story){
        System.out.println(story.getKey() + "\t" + story.getSummary());
    }
}
