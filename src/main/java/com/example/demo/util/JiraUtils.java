package com.example.demo.util;

 ;
 import com.example.demo.bean.jira.Board;
 import com.example.demo.bean.jira.Story;
 import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 13/5/17.
 */

public class JiraUtils {

    public static void getWeeklyReport(int sprintNumber) throws Exception {
        System.out.println("START JIRA");
        List<Story> storyList = getJira(sprintNumber);
        int count_close = 0;
        int count_open = 0;
        int count_wip = 0;
        for (Story story : storyList) {
            if (story.getStatus().equals("DONE")) {
                count_close++;
            } else if (story.getStatus().equals("DEV in Progress")) {
                count_wip++;
            } else {
                count_open++;
            }
        }

        System.out.println(count_close);
        System.out.println(count_open);
        System.out.println(count_wip);
        System.out.println("END JIRA");
    }

    public static void getRelease(){

    }


    public static List<Story> getJira(int sprintNumber ) throws Exception {

        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("https.proxyHost", "10.225.3.1");
        System.setProperty("https.proxyPort", "3128");

        String baseUrl = "https://jira.energisme.com/";
        String username = "nd.long";
        String password = "Ifi@2018";


        String energyName = "Energy Sprint " + sprintNumber;
        String analysisName = "Analysis Sprint " + sprintNumber;
        String adminName = "Admin Sprint " + sprintNumber;
        String financialName = "Financial Sprint " + sprintNumber;

        int kuritaSprintNumber = sprintNumber - 11;
        String kuritaName = "Migration Kurita Sprint " + kuritaSprintNumber;

        List<Board> boardList = new ArrayList<>();
        // get all id board: https://jira.energisme.com/rest/agile/latest/board
        boardList.add(new Board(45, energyName, null));
        boardList.add(new Board(43, analysisName, null));
        boardList.add(new Board(44, adminName, null));
        boardList.add(new Board(46, financialName, null));
        boardList.add(new Board(85, kuritaName, null));

        RESTInvoker rest = new RESTInvoker(baseUrl, username, password);
        List<Story> storyList = new ArrayList<>();

        // Get all stories of board
        for (Board board : boardList) {
            // Get current Sprint ID of board.
            String boardJson = rest.getDataFromServer("rest/agile/1.0/board/" + board.getBoardId() + "/sprint");
            JsonObject boardJsonObject = new JsonParser().parse(boardJson).getAsJsonObject();
            JsonArray boardArray = (JsonArray) boardJsonObject.get("values");
            for (int i = 0; i < boardArray.size(); i++) {
                JsonObject boardObject = (JsonObject) boardArray.get(i);
                String sprintName = JsonUtils.getChildOfJson(boardObject, "name");
                if (sprintName.equals(board.getCurrentSprintName())) {
                    String sprintIdS = JsonUtils.getChildOfJson(boardObject, "id");
                    board.setCurrentSprintId(Integer.parseInt(sprintIdS));
                }
            }
            if (board.getCurrentSprintId() == null) {
                System.out.println("-----ERORRRRRRRRRR Sprint:" + board.getCurrentSprintName());
                break;
            }
            int sprintId = board.getCurrentSprintId();

            // get all story of current sprint
            String js = rest.getDataFromServer("rest/agile/latest/board/"+board.getBoardId()+"/issue?jql=Sprint="+sprintId);
            JsonObject jsonObject = new JsonParser().parse(js).getAsJsonObject();
            JsonArray issueArray = (JsonArray) jsonObject.get("issues");
            for (int i = 0; i < issueArray.size(); i++) {
                JsonObject issue = (JsonObject) issueArray.get(i);
                String key = JsonUtils.getChildOfJson(issue, "key");
                String name = JsonUtils.getChildOfJson(issue, "fields", "summary");
                String issueType = JsonUtils.getChildOfJson(issue, "fields", "issuetype", "name");
                String status = JsonUtils.getChildOfJson(issue, "fields", "status", "name");
                Story story = new Story(key, name, issueType, status);
                storyList.add(story);
            }
        }

        return storyList;
        // Count story of all
    }

}
