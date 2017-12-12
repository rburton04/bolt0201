package swatsolutions.bolt.utils;

import java.util.List;
import java.util.Map;

public class testAPIS {


    public static void main(String [] args){
        //apiCall call = new apiCall("https://maps.googleapis.com/maps/api/timezone/json", "GET", null);
        //call.makeAPICall();
        //int code = call.getResponseCode();
        //String rawResponse = call.getRawResponseBody();

        databaseConnection dbc = new databaseConnection();
        dbc.databaseConnection("jdbc:mysql://192.168.40.15:13306/test","root","bolt");

        Map<String, List<String>> response = dbc.querySelect("SELECT * FROM demo");

        System.out.println(dbc.getQueryResponse().toString());

        System.out.println(response.toString());

        dbc.closeConnection();

    }

}
