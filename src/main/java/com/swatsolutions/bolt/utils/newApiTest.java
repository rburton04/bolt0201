package com.swatsolutions.bolt.utils;

import com.swatsolutions.bolt.apiObjects.DemoTwoObject;
import com.swatsolutions.bolt.apiObjects.MaybeThisWorks;
import com.swatsolutions.bolt.apiObjects.demoObject;

import java.util.List;

public class newApiTest {

	public static void main (String[] args){
		newApiDemo trial = new newApiDemo();

		//create object and set it equal to trial.m3
		//adjust m3 to take in url and such
		//ensure response code is returned
		//trial.m2("http://api.geonames.org/findNearestAddressJSON?formatted=true&lat=37.451&lng=-122.18&username=apitrial&style=full");
//		MaybeThisWorks obj6 = trial.m6("http://api.geonames.org/findNearestAddressJSON?formatted=true&lat=37.451&lng=-122.18&username=apitrial&style=full");

//		String obj6String = obj6.getAddress().toString();
		List<MaybeThisWorks> m5s = trial.m7("http://api.geonames.org/findNearestAddressJSON?formatted=true&lat=37.451&lng=-122.18&username=apitrial&style=full");
		String dm5s = m5s.toString();

		//trial.m2("localhost:3000/posts/1");
		//List<demoObject> demos;
		//demos = trial.m4("localhost:3000/posts/1");
		//String results = demos.toString();


	}

}
