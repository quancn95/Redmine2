package com.example.demo.util;

import org.apache.commons.codec.binary.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RESTInvoker {
    private final String baseUrl;
    private final String username;
    private final String password;

    public RESTInvoker(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

//    public String getRESTResponse(String accountId) {
//        return getDataFromServer("account/" + accountId);
//    }

    public String getDataFromServer(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(baseUrl + path);
            URLConnection urlConnection = setUsernamePassword(url);
            BufferedReader reader = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private URLConnection setUsernamePassword(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = username + ":" + password;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return urlConnection;
    }
}