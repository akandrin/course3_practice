package com.sample.mapper;

import com.sample.dto.WeatherDTO;
import com.sample.entity.Weather;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-27T20:30:37+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public WeatherDTO mapWithoutId(Weather weather) {
        if ( weather == null ) {
            return null;
        }

        WeatherDTO weatherDTO = new WeatherDTO();

        if ( weather.getTemperature() != null ) {
            weatherDTO.setTemperature( weather.getTemperature().byteValue() );
        }

        return weatherDTO;
    }

    @Override
    public Weather mapGenerated(com.sample.generated.Weather generated) {
        if ( generated == null ) {
            return null;
        }

        Weather weather = new Weather();

        weather.setTemperature( (int) generated.getTemperature() );

        return weather;
    }
}
