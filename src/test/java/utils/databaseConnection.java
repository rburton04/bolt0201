package utils;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class databaseConnection {
    //use .clone() if a copy of the map is needed as compared to a reference
    //TODO handle errors correctly

    private String dbUrl, username, password;
    private Connection connection;
    private Map<String, List<String>> queryResponse = new HashMap<String, List<String>>();

    public void databaseConnection(String url, String username, String password){
        dbUrl = url;
        this.username = username;
        this.password = password;
        makeConnection();
    }

    private void makeConnection (){
        try {
            Properties creds = new Properties();
            creds.put("user", username);
            creds.put("password", password);

            connection = DriverManager.getConnection(dbUrl, creds);
        } catch (Exception e){

        }
    }

    public boolean queryUpdate (String query){
        boolean resultSet = false;
        try {
            Statement stmt = connection.createStatement();
            //break out to verify result set
            resultSet = stmt.execute(query);
        } catch (Exception e){

        }
        return resultSet;
    }

    public Map<String, List<String>> querySelect (String query){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            //break out to verify result set
            //boolean resultSet = stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData  rsmd = rs.getMetaData();

            for(int colIndex = 1; colIndex < rsmd.getColumnCount(); colIndex++){
                queryResponse.put(rsmd.getCatalogName(colIndex), (List<String>)rs.getArray(colIndex).getArray());
            }
        } catch (Exception e){

        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception e){}
        }
        return queryResponse;
    }

    public void closeConnection (){
        try {
            connection.close();
        } catch (Exception e){

        }
    }

    public Map<String, List<String>> getQueryResponse(){
        return queryResponse;
    }
}
