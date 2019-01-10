package com.example.demo.controller;

import com.example.demo.bean.redmine.Issue;
import com.example.demo.util.Contants;
import com.example.demo.util.RedmineAPIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ntquan on 1/7/2019.
 */
@Controller
@RequestMapping(value = {"/redmine-counter"})
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

    @GetMapping
    public String home(){
        return "index";
    }

    @GET
    @ResponseBody
    @RequestMapping(value = "/getAllIssue1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public List<Issue> getRedmineIssues1(@RequestParam int sprint) {
        List<Issue> listIssues = RedmineAPIUtils.getAllIssue(sprint);
        return listIssues;
    }

    @GET
    @ResponseBody
    @RequestMapping(value = "/getAllIssue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public Map<String,Map> getRedmineIssues(@RequestParam int sprint) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Map> issuesResponse = new HashMap<>();
        int low, normal,high,urgent,immediate;
        int closed,newst,inprogress,resolved,feedback,rejected,cancelled;
        low = normal = high = urgent = immediate = closed = newst = inprogress = resolved = feedback = rejected = cancelled = 0;

        List<Issue> listIssues = null;
        try {
            listIssues = RedmineAPIUtils.getAllIssue(sprint);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,Integer> priorityIssue = new HashMap<>();
        Map<String,Integer> statusIssue = new HashMap<>();
        Map<String,Integer> noIssue = new HashMap<>();
        if(listIssues!=null){
            for(Issue i : listIssues){
                if(i.getTracker()!=null && i.getTracker().equals(Contants.RedmineIssues.BUG)){
                    if (i.getPriority()!=null) {
                        if (i.getPriority().equals(Contants.RedmineIssues.LOW)) {
                            low++;
                        }
                        else if (i.getPriority().equals(Contants.RedmineIssues.NORMAL)) {
                            normal++;
                        }
                        else if (i.getPriority().equals(Contants.RedmineIssues.HIGH)) {
                            high++;
                        }
                        else if (i.getPriority().equals(Contants.RedmineIssues.URGENT)) {
                            urgent++;
                        }
                        else if (i.getPriority().equals(Contants.RedmineIssues.IMMEDIATE)) {
                            immediate++;
                        }
                    }
                    if (i.getStatus()!=null) {
                        if (i.getStatus().equals(Contants.RedmineIssues.CLOSED))
                            closed++;
                        if (i.getStatus().equals(Contants.RedmineIssues.NEW))
                            newst++;
                        if (i.getStatus().equals(Contants.RedmineIssues.INPROGRESS))
                            inprogress++;
                        if (i.getStatus().equals(Contants.RedmineIssues.RESOLVED))
                            resolved++;
                        if (i.getStatus().equals(Contants.RedmineIssues.FEEDBACK))
                            feedback++;
                        if (i.getStatus().equals(Contants.RedmineIssues.REJECTED))
                            rejected++;
                        if (i.getStatus().equals(Contants.RedmineIssues.CANCELLED))
                            cancelled++;
                    }
                }
            }
            // set map priority
            priorityIssue.put(Contants.RedmineIssues.LOW, low);
            priorityIssue.put(Contants.RedmineIssues.NORMAL, normal);
            priorityIssue.put(Contants.RedmineIssues.HIGH, high);
            priorityIssue.put(Contants.RedmineIssues.URGENT, urgent);
            priorityIssue.put(Contants.RedmineIssues.IMMEDIATE, immediate);

            //set map status
            statusIssue.put(Contants.RedmineIssues.CLOSED, closed);
            statusIssue.put(Contants.RedmineIssues.NEW, newst);
            statusIssue.put(Contants.RedmineIssues.INPROGRESS, inprogress);
            statusIssue.put(Contants.RedmineIssues.RESOLVED, resolved);
            statusIssue.put(Contants.RedmineIssues.FEEDBACK, feedback);
            statusIssue.put(Contants.RedmineIssues.REJECTED, rejected);
            statusIssue.put(Contants.RedmineIssues.CANCELLED, cancelled);

            //set map response
            try {
                String priority = mapper.writeValueAsString(priorityIssue);
                String status = mapper.writeValueAsString(statusIssue);
                issuesResponse.put("ByPriority", priorityIssue);
                issuesResponse.put("ByStatus", statusIssue);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else{
            // set map not found issue
            noIssue.put(Contants.RedmineIssues.NOTFOUND,0);
            //set map response
            try {
                issuesResponse.put("NotFound", noIssue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return issuesResponse;
    }
}
