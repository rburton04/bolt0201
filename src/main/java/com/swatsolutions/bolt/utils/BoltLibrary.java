package com.swatsolutions.bolt.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BoltLibrary {

    /**
     * @param elementList List of elements that text values are needed from.
     * @return List String  of the text from each element in the list.
     */
    public static List<String> elementListToUppercaseStringList(List<WebElement> elementList){
        List<String> uppercaseList = new LinkedList<String>();
        for(WebElement element:elementList){
            uppercaseList.add(element.getText().toUpperCase());
        }
        return uppercaseList;
    }

    /**
     * @param miliseconds number of miliseconds to delay
     */
    public static void hardDelay(int miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	/**
	 * @param driver WebDriver
	 * @param miliseconds number of miliseconds to wait
	 */
	public static void implicitWait(WebDriver driver, long miliseconds){
        try {
            driver.manage().timeouts().implicitlyWait(miliseconds, TimeUnit.MILLISECONDS);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeMapToFile (Map<String, List<String>> data, String fileLocation){
		if(data.isEmpty() || fileLocation.isEmpty())
			return;
	    FileWriter fw = null;
	    try{
			//ensure file does not exist
			try {
				Path file = Paths.get(System.getProperty("user.dir") + "/" + fileLocation);
				Files.deleteIfExists(file);
			} catch (Exception e){ //handles an exeption if the file doesn't exist. No action needed.
			}
			fw = new FileWriter(System.getProperty("user.dir") + "/" + fileLocation);

			for(Map.Entry<String, List<String>> entry: data.entrySet()){
				int entrySize = entry.getValue().size();
				for(int i = 0; i < entrySize; i++){
					fw.append(entry.getValue().get(i));
					if((i+1) < entrySize)
						fw.append(",");
				}
				fw.append("\n");
			}

		} catch (Exception e){
			System.out.println("Error in writeMapToFile: " + e.getMessage());
		} finally{
			try{
				fw.flush();
				fw.close();
			} catch (IOException e){
				System.out.println(e.getMessage());
			}
		}
    }

}
