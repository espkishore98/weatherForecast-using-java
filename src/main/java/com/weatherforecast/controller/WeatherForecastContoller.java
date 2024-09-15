package com.weatherforecast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherforecast.domain.ResponseObject;
import com.weatherforecast.service.IWeatherForecastService;

import io.swagger.annotations.ApiOperation;

@RestController
public class WeatherForecastContoller {
	
	@Autowired
	IWeatherForecastService weatherForecastService;
	
	@ApiOperation(value = "This endpoint is used to get the weather details")
	@GetMapping("/weatherForecast")
	public ResponseObject weatherForecast(@RequestParam String cityName) {
		return weatherForecastService.weatherforecast(cityName);
	}

}
