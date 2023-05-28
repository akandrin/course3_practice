package com.sample.mapper;

import com.sample.dto.WeatherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.util.GregorianCalendar;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    @Mapping(target = "temperature", source = "temperature")
    //@Mapping(target = "pressure", source = "pressure")
    //@Mapping(target = "humidity", source = "humidity")
    //@Mapping(target = "date", source = "date")
    WeatherDTO mapWithoutId(com.sample.entity.Weather weather);

    @Mapping(target = "temperature", source = "temperature")
    //@Mapping(target = "pressure", source = "pressure")
    //@Mapping(target = "humidity", source = "humidity")
    //@Mapping(target = "date", source = "date")
    com.sample.entity.Weather mapGenerated(com.sample.generated.Weather generated);

    /*default Date map(XMLGregorianCalendar calendar)
    {
        return new java.sql.Date(calendar.toGregorianCalendar().getTime().getTime());
    }

    default String mapXMLGregorianCalendar(XMLGregorianCalendar date) {
        return date.toString();
    }

    default XMLGregorianCalendar mapDate(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        DatatypeFactory dTF = null;
        try {
            dTF = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        return dTF.newXMLGregorianCalendar(gregorianCalendar);
    }*/
}