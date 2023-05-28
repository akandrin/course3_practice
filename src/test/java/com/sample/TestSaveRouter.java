package com.sample;

import com.sample.dto.WeatherDTO;
import com.sample.generated.ObjectFactory;
import com.sample.generated.Weather;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TestSaveRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        /*from("jpa:com.sample.entity.Weather")
                .to("direct:test_save_to_db");
        from("kafka:results?brokers=localhost:9092").
                to("direct:test_kafka_results");*/
    }
}
