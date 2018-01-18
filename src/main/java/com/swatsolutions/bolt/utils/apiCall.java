package com.swatsolutions.bolt.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class apiCall { //TODO possibly throw all errors to what called this

    private String callURL, response = "", callType;
    private Map<String, Object> parameters;
    private int responseCode, maxAttempts = 5;
    private byte[] byteBody = new byte[0];
    //TODO expand to take in a string to check the response with so that it continues or ends making api calls based on that string

    //TODO process response into a readable/usable format

    /**
     * Sets initial values for an api call. These can be modified with the put methods
     * @param url
     * @param callType
     * @param params
     */
    public apiCall(String url, String callType, Map<String, Object> params){
        this.callType = callType;
        callURL = url;
        parameters = params;
        buildParams();
    }

    //TODO call whenever the URL is changed or when the parameters are changed
    private void buildParams(){
        StringBuilder body = new StringBuilder();
        try {
            if (parameters != null) {
                for (Map.Entry<String, Object> pram : parameters.entrySet()) {
                    if (body.length() != 0)
                        body.append("&");

                    body.append(URLEncoder.encode(pram.getKey(), "UTF-8"));
                    body.append("=");
                    body.append(URLEncoder.encode(pram.getValue().toString(), "UTF-8"));
                }
            }
            byteBody = body.toString().getBytes();
        } catch (Exception e){

        }
    }

    public void makeAPICall(){

        try {
            URL postURL = new URL(callURL);
            int attempts = 0;
            BufferedReader responseIn;
            String currentLine;

//TODO may need to update this while statement
            while(response.isEmpty() || response.contains("error:response not found")){
                response = "";
                //TODO possibly add a driver.getCurrnetURL() to help keep the session active
                if(attempts > maxAttempts) {
                    response = "EXCEEDED MAXIMUM ATTEMPTS";
                    break;
                } else if(attempts > 0){
                    //10 second delay between attemped calls against the API
                    library.hardDelay(10000);
                }
                HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
                connection.setRequestMethod(callType);
                switch (callType.toUpperCase()){
                    case "POST":
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        connection.setRequestProperty("Content-Length", String.valueOf(byteBody.length));
                        connection.setDoOutput(true);
                        connection.getOutputStream().write(byteBody);
                        break;
                    case "GET":
                        break;
                }
                connection.connect();

                responseCode = connection.getResponseCode();
                if(responseCode >= 200 && responseCode <= 299){
                    responseIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else{
                    responseIn = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }

                while ((currentLine = responseIn.readLine()) != null) {
                    response += currentLine;
                }
                attempts++;
            }


        } catch (Exception e){
            String msg = e.getMessage();
        }
    }

    public int getResponseCode (){
        return responseCode;
    }

    public String getRawResponseBody (){
        return response;
    }

    public String getURL(){
        return callURL;
    }

    public void putParamaters(Map<String,Object> newParams){
        parameters = newParams;
        buildParams();
    }

    public void putURL(String newURL){
        callURL = newURL;
    }

    public void putMaxAttempts(int newMaxAttempt){
        if(newMaxAttempt < 1)
            newMaxAttempt = 1;
        maxAttempts = newMaxAttempt;
    }
}
