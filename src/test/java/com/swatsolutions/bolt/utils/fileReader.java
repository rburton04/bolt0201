package com.swatsolutions.bolt.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class fileReader {

    public static Map<String,String> processCsv (String fileName){
        //TODO setup to return a map with key "PAGE:OBJECT" and value "TYPE:value"
        //Should be efficient and usable throughout testing
        Map<String, String> convertedFile = new HashMap<String, String>();
        String[] csvLine;
        String lastPage = "";
        boolean headerRowFound = false;
        CSVReader reader = null;
        fileName = System.getProperty("user.dir") + "/" + fileName;

        try{
            reader = new CSVReader (new FileReader(fileName));
            while((csvLine = reader.readNext()) != null){
                if(csvLine.length == 4){
                    if(headerRowFound){
                        if(!csvLine[0].isEmpty())
                            lastPage = csvLine[0];
                        convertedFile.put(lastPage + ":" + csvLine[1],csvLine[2] + "~" + csvLine[3]);
                    }
                }
                headerRowFound = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return convertedFile;
    }
}
