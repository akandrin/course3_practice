package com.sample.router;

import com.sample.dto.WeatherDTO;
import com.sample.generated.Weather;
import com.sample.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.catalog.JSonSchemaResolver;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import org.apache.camel.model.dataformat.JaxbDataFormat;

@Component
@RequiredArgsConstructor
public class SaveRouter extends RouteBuilder {
    private final WeatherMapper mapper;

    public void configure() {
        from("direct:save_to_db")
            .choice()
            .when(body().isInstanceOf(Weather.class))
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .process(exchange -> {
                    Weather in = exchange.getIn().getBody(Weather.class);
                    com.sample.entity.Weather weather = mapper.mapGenerated(in);

                    exchange.getMessage().setBody(weather, com.sample.entity.Weather.class);
                })
                .log("Saving ${body} to database...")
                .to("jpa:com.sample.entity.Weather")
                .process(exchange -> {
                    com.sample.entity.Weather in = exchange.getIn().getBody(com.sample.entity.Weather.class);
                    WeatherDTO weather = mapper.mapWithoutId(in);

                    exchange.getMessage().setBody(weather, WeatherDTO.class);
                })
                .marshal().json(JsonLibrary.Jackson)
                .log("Saving ${body} to kafka")
                .to("kafka:results?brokers=localhost:9092")
                .setBody(simple("<status>ok</status>"))
                .to("direct:status")
                .to("direct:metrics_router_increment_success_messages")
                .to("direct:metrics_router_stop_timer")
            .otherwise()
                .setBody(simple("<status>error</status><message>XML data isn't instance of Weather</message>"))
                .to("direct:status")
                .to("direct:metrics_router_increment_fail_messages")
                .to("direct:metrics_router_stop_timer");
    }
}
