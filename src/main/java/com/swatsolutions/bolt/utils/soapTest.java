package com.swatsolutions.bolt.utils;

public class soapTest {

	public static void main (String[] args){
		SoapUI soap = new SoapUI();

		soap.testRunner(System.getProperty("user.dir") + "/soapui/CurrencyConvertor.xml", null);
	}

}
