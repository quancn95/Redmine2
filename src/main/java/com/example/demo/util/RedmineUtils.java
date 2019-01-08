package com.example.demo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ashish on 13/5/17.
 */

public class RedmineUtils {

    public static void getRedmine(String sprint_start_date, String sprint_end_date) throws Exception {
        // Lay session cua redmine. bat F12 vao application -> cookie de lay value cua _redmine_session
        String session_key = "NXROaWEvRE93MGluQjB3LzZGWGVsTWdoMExqTUZIOVZDRVBaUGF6ZjFFK0Irak0vdWNCbENLalpueFVuUnFSTGdFbGkvSWR3L2VlUVkyUWN5L3IzcUZiMjZjSTNtcWdkdVBQTXp1KzU1SURlSyttZWRGZzVYbit0eUIrZnI1SzdvZUFGT3JqZ3JmaDhkNDRUMnk2dW1lOWNleGxIbE9pZ2p3bUNtMGlkYzIwbkt6L2E3M1pPdHFkQ0E3cEVuaEp0Z281Vm9KN2JuQlpjR0FsVHE1a09pa3A5VHlCUzlVOEdoV1lBazNJYStzRGpxMlNCdVh5VTlaWGpwK0VSU1VOQjFKUjA0WW5QSzJMVGJMZVdIUEpnanJ6ekJvUnRzT3ZGdi9lUngyNWt1UElEWkxETVBadW9XdXNwOWEyUVI2ZmF6M1ZNbjRwS1gzZ2ZBWExzUmNpM0pZeEJIZGZra3RkWHhCMCtzYWlvbXRKTTN3cVdwVFNPKyt6NjRKQTd3YmdNRStBbExpSC9YMEdRaTJDd3pPeS9XZnNRSVk1eXF6ektvb0FWZjloV3pZekRFOVAzMDhCZzhGSE1FUzVqM1gyZ252elVjRU5hZnVDUDRpdjhuL1BaZms2V2w0MGxpemZIYWxHejFVR2V1OGk0MG1yL0FSaDRMQmdZUk94WmJhZGM0K29WRGczTCtLRDFWTDBDd1NzYjRnditYQ09FNWJPam92aHRKV2s1OVM4PS0tb2NMd01Hd0pWTndra3IzOHdIU2hVdz09--8c6ef78e00de131f82357e016650682d3f1207a4";
        // 1. Lay thong tin tu task redmine
        // Get tong so trang
        System.out.println("START REDMINE");
        int per_page = 100;
        Queue<Integer> pageQueue = new LinkedList<>();
        pageQueue.add(1);
        boolean isGetAllPage = true;

        int count_approve = 0;
        int count_pedding = 0;
        int count_assign = 0;
        int count_wip = 0;
        int count_done = 0;
        int count_verifiy = 0;
        int total = 0;

        while (!pageQueue.isEmpty()) {
            int pageNumber = pageQueue.peek();
            pageQueue.remove();
            String link = "http://apis.ifisolution.local:8080/projects/portal-standard/issues?c%5B%5D=tracker&c%5B%5D=status&c%5B%5D=priority&c%5B%5D=category&c%5B%5D=subject&c%5B%5D=assigned_to&c%5B%5D=updated_on&f%5B%5D=created_on&f%5B%5D=&group_by=&op%5Bcreated_on%5D=%3E%3C&page=" + pageNumber + "&&per_page=" + per_page + "&set_filter=1&utf8=%E2%9C%93&v%5Bcreated_on%5D%5B%5D=" + sprint_start_date + "&v%5Bcreated_on%5D%5B%5D=" + sprint_end_date;
            Document doc = Jsoup.connect(link).cookie("_redmine_session", session_key).get();
            Elements issues = doc.select("tr.issue");

            for (int i = 0; i < issues.size(); i++) {
                Element element = issues.get(i);
                Element status = element.select("td.status").first();
                Element assigned_to = element.select("td.assigned_to").first();

                if (status != null) {
                    String statusText = status.text();
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
                            String assigned_toText = assigned_to.text();
                            if (assigned_toText.isEmpty()) {
                                count_pedding++;
                            } else {
                                count_assign++;
                            }
                    }
                } else {
                    System.out.println("ERROR!!");
                }
                total++;
            }
            if (isGetAllPage) {
//                System.out.println(link);
                isGetAllPage = false;
                Elements pages = doc.select("li.page");
                for (int j = 0; j < pages.size(); j++) {
                    Element page = pages.get(j);
                    try {
                        Integer p = Integer.parseInt(page.text());
                        pageQueue.add(p);
                    } catch (NumberFormatException nfe) {

                    }
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
