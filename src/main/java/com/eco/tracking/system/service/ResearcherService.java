package com.eco.tracking.system.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;

import org.springframework.web.reactive.function.client.WebClient;

import com.eco.tracking.system.request.AirPollutionRequest;
import com.eco.tracking.system.request.SolarIrradianceRequest;

import com.eco.tracking.system.util.Helper;

import com.eco.tracking.system.exception.ExceptionType.ValidationException;

@Service
public class ResearcherService
{
    private final WebClient webClient;

    @Value(value = "${open_weather_key}")
    private String openWeatherApiKey;

    @Value(value = "${open_weather_air_pollution}")
    private String openWeatherAirPollutionPath;

    @Value(value = "${open_weather_solar-irradiance}")
    private String openWeatherSolarIrradiancePath;

    @Autowired
    public ResearcherService(
        WebClient.Builder webClientBuilder,
        @Value(value = "${open_weather_base_url}")
        final String openWeatherBaseUrl
    )
    {
        webClient = webClientBuilder
            .baseUrl(openWeatherBaseUrl)
            .build();
    }

    public String getAirPollution(
        AirPollutionRequest request,
        BindingResult result
    ) throws ValidationException
    {
        Helper.fieldsValidate(result);

        return webClient.get()
            .uri(uriBuilder ->
                uriBuilder
                    .path(openWeatherAirPollutionPath)
                    .queryParam("lat", request.getParsedLatitude())
                    .queryParam("lon", request.getParsedLongitude())
                    .queryParam("appid", openWeatherApiKey)
                    .build()
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public String getSolarIrradiance(SolarIrradianceRequest request, BindingResult result)
    {
        Helper.fieldsValidate(result);

        return webClient.get()
            .uri(uriBuilder ->
                uriBuilder
                    .path(openWeatherSolarIrradiancePath)
                    .queryParam("date", request.getDate())
                    .queryParam("lat", request.getParsedLatitude())
                    .queryParam("lon", request.getParsedLongitude())
                    .queryParam("appid", openWeatherApiKey)
                    .build()
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
