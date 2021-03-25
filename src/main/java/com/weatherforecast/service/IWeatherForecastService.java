package com.weatherforecast.service;

import com.weatherforecast.domain.ResponseObject;

public interface IWeatherForecastService {

	ResponseObject weatherforecast(String cityName);

}
