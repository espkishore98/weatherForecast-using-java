package com.weatherforecast.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherForecastService implements IWeatherForecastService{
	@Value("${api_key}")
	String apiKey;
	
	@Override
	public String weatherforecast(String cityName) {
		try {
		String baseUrl="https://api.openweathermap.org/data/2.5/weather?";
		URL url=new URL(baseUrl+"q="+cityName+"&appid="+apiKey);
		System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responseCode=conn.getResponseCode();
		System.out.println(responseCode);
		if(responseCode!=200) {
			throw new RuntimeException("HttpResponseCode:"+responseCode);
			
		}
		else {
			Scanner sc = new Scanner(url.openStream());
			String inline="";
			while(sc.hasNext())
			{
			inline+=sc.nextLine();
			}
			System.out.println(inline);
			System.out.println("\nToday\'s "+cityName+" weather condition is:");
			try {
			JSONParser parser = new JSONParser();
			JSONObject jobj= (JSONObject)parser.parse(inline);
		
			JSONArray jsonarray1= (JSONArray)jobj.get("weather");//json array
			JSONObject getArrayObject=(JSONObject)jsonarray1.get(0);//getting json arrays first object
			Object description=getArrayObject.get("description");//getting individual object from json object
			JSONObject mainObj= (JSONObject) jobj.get("main");
			Object temperature=mainObj.get("temp");
			Object pressure=mainObj.get("pressure");
			Object humidity=mainObj.get("humidity");
			
			
			double tempInCelsius= ((double)temperature-273.15);
			float temp=(float) tempInCelsius;
			
			return "Temperature in "+cityName+" in celsius is " +temp+"\n"+"atmospheric Pressure (in hPa unit):"+pressure+"\n"+"Humidity:"+humidity+"\n"+"Weather Description:"+description;
			
	
			}catch (Exception e) {
				return e.getMessage();
			}
			
		}
			
		}catch (Exception e) {
			return e.getMessage();// TODO: handle exception
		}
		
	}

}
