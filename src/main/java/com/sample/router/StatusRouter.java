package com.sample.router;

import com.sample.generated.ObjectFactory;
import com.sample.generated.Weather;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class StatusRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:status")
                .log("Send to status_topic : ${body}")
                .to("kafka:status_topic?brokers=localhost:9092");
    }
}
