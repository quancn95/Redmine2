package com.example.demo.util;

import com.example.demo.bean.jira.Board;
import com.example.demo.bean.jira.Story;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 13/5/17.
 */

public class JsonUtils {

    public static String getChildOfJson(JsonObject object, String... keys) {
        JsonObject childObj = object;
        String txt = null;
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (i == keys.length - 1) {
                JsonPrimitive jsonPrimitive = childObj.getAsJsonPrimitive(key);
                if(jsonPrimitive != null){
                    txt = childObj.getAsJsonPrimitive(key).getAsString();
                }
            } else {
                childObj = (JsonObject) childObj.get(key);
            }
        }
        return txt;
    }
}
