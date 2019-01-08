package com.example.demo.controller;

import com.example.demo.bean.redmine.Issue;
import com.example.demo.util.RedmineAPIUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * Created by ntquan on 1/7/2019.
 */
@Controller
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

    @GetMapping(value = "/")
    public String home(){
        return "index";
    }

    @GET
    @RequestMapping(value = "/home", method = RequestMethod.GET) //, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public String getHome() {
        return "index";
    }

    @GET
    @ResponseBody
    @RequestMapping(value = "/getAllIssue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public List<Issue> getRedmineIssues(@RequestParam int sprint) {
        List<Issue> listIssues = RedmineAPIUtils.getAllIssue(sprint);
        return listIssues;
    }
}
