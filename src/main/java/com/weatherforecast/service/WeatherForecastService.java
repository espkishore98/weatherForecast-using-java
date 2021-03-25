package com.weatherforecast.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import com.weatherforecast.domain.ResponseObject;
import com.weatherforecast.domain.WeatherResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class WeatherForecastService implements IWeatherForecastService{
	@Value("${api_key}")
	String apiKey;
	
	@Override
	public ResponseObject weatherforecast(String cityName) {
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
			
				WeatherResponse weatherForecastResponse= new WeatherResponse();
			weatherForecastResponse.setTemperature(temp);
			weatherForecastResponse.setHumidity(humidity.toString());
			weatherForecastResponse.setAtmosphericPressure(pressure.toString());
			weatherForecastResponse.setWeatherDescription(description.toString());

				return new ResponseObject(weatherForecastResponse,null,HttpStatus.OK);
	
			}catch (Exception e) {
				return new ResponseObject(null,e.getMessage(),HttpStatus.BAD_REQUEST);
				
			}
			
		}
			
		}catch (Exception e) {
			return new ResponseObject(null,e.getMessage(),HttpStatus.BAD_REQUEST);
		}

	}

}
