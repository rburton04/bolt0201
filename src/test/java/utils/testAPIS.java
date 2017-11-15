package utils;

public class testAPIS {


    public static void main(String [] args){
        apiCall call = new apiCall("https://maps.googleapis.com/maps/api/timezone/json", "GET", null);
        call.makeAPICall();
        int code = call.getResponseCode();
        String rawResponse = call.getRawResponseBody();

    }

}
