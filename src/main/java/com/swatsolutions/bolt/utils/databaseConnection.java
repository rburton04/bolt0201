package com.swatsolutions.bolt.utils;

import java.sql.*;
import java.util.*;

public class databaseConnection {
    //use .clone() if a copy of the map is needed as compared to a reference
    //TODO handle errors correctly

    private String dbUrl, username, password, dbType;
    private Connection connection;
    private Map<String, List<String>> queryResponse = new HashMap<String, List<String>>();

    public databaseConnection(String url, String username, String password, String type){
        dbUrl = url;
        this.username = username;
        this.password = password;
        dbType = type.toUpperCase();
        switch(type.toUpperCase()){
            case "SQL":
                makeSQLConnection();
                break;
            default:
                makeSQLConnection();
        }
    }

    private void makeSQLConnection(){
        try {
            //TODO build so this is adaptable for other types of dbs
            Class.forName("com.mysql.jdbc.Driver");
            Properties creds = new Properties();
            creds.put("user", username);
            creds.put("password", password);

            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean queryUpdate(String query){
        boolean resultSet = false;
        try {
        	switch (dbType) {
        		default:
		        case "SQL":
			        Statement stmt = connection.createStatement();
			        //break out to verify result set
			        resultSet = stmt.execute(query);
			        break;
	        }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public Map<String, List<String>> querySelect(String query){
    	switch (dbType) {
    		default:
		    case "SQL":
			    Statement stmt = null;
			    try {
				    stmt = connection.createStatement();
				    //break out to verify result set
				    //boolean resultSet = stmt.execute(query);
				    ResultSet rs = stmt.executeQuery(query);
				    ResultSetMetaData rsmd = rs.getMetaData();

				    int count = 0;
				    List<String> columnList = new ArrayList<>();
				    for (int colIndex = 1; colIndex <= rsmd.getColumnCount(); colIndex++) {
					    columnList.add(rsmd.getColumnName(colIndex));
				    }
				    queryResponse.put(String.valueOf(count), columnList);

				    count++;
				    while (rs.next()) {
					    //create a list
					    List<String> resultList = new ArrayList<>();
					    for (int colIndex = 1; colIndex <= rsmd.getColumnCount(); colIndex++) {
						    resultList.add(rs.getString(colIndex));
					    }
					    queryResponse.put(String.valueOf(count), resultList);
					    count++;
				    }
				    //for(int colIndex = 1; colIndex < rsmd.getColumnCount(); colIndex++){
				    //    queryResponse.put(rsmd.getCatalogName(colIndex), (List<String>)rs.getArray(colIndex).getArray());
				    //}
			    } catch (Exception e) {
				    System.out.println(e.getMessage());
			    } finally {
				    try {
					    if (stmt != null)
						    stmt.close();
				    } catch (Exception e) {
				    }
			    }
	    }
        return queryResponse;
    }

    public void closeConnection(){
        try {
        	switch (dbType) {
		        case "SQL":
		        	connection.close();
		        	break;
		        default:
			        connection.close();
	        }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, List<String>> getQueryResponse(){
        return queryResponse;
    }
}
