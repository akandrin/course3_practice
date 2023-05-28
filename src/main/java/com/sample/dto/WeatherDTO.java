package com.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    @JsonProperty("temperature")
    private Byte temperature;

    /*private Short pressure;

    private Byte humidity;

    @JsonProperty("date")
    private XMLGregorianCalendar date;*/
}