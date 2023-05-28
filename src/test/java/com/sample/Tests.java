package com.sample;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.ExchangePatternProcessor;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest(properties = {"kafka-requests-path=direct:requests"})
//@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
public class Tests {

    @Autowired
    ProducerTemplate producerTemplate;

    @EndpointInject("mock:jpa:com.sample.entity.Weather")
    public MockEndpoint saveToDb;

    @EndpointInject("mock:kafka:results")
    public MockEndpoint kafkaResults;

    @EndpointInject("mock:kafka:status_topic")
    public MockEndpoint kafkaStatusTopic;

    @Test
    public void saveToDBTest() throws InterruptedException {
        com.sample.entity.Weather weather = new com.sample.entity.Weather();
        weather.setTemperature(25);
        saveToDb.expectedBodiesReceived(weather);

        producerTemplate.sendBody("direct:requests", "<weather><temperature>25</temperature>" +
                "<pressure>760</pressure><humidity>75</humidity><date>2002-09-24</date></weather>");

        MockEndpoint.assertIsSatisfied(saveToDb);
    }

    @Test
    public void kafkaResultsResultsTest() throws InterruptedException {
        kafkaResults.expectedBodiesReceived("{\"temperature\":25}");
        //kafkaResults.expectedMessageCount(1);

        producerTemplate.sendBody("direct:requests", "<weather><temperature>25</temperature>" +
                "<pressure>760</pressure><humidity>75</humidity><date>2002-09-24</date></weather>");

        MockEndpoint.assertIsSatisfied(kafkaResults);
    }

    @Test
    public void sendOKStatusTest() throws InterruptedException {
        kafkaStatusTopic.expectedBodiesReceived("<status>ok</status>");

        producerTemplate.sendBody("direct:requests", "<weather><temperature>25</temperature>" +
                "<pressure>760</pressure><humidity>75</humidity><date>2002-09-24</date></weather>");

        kafkaStatusTopic.assertIsSatisfied(5000);
    }

    @Test
    public void sendErrorStatusTest() throws InterruptedException {
        kafkaStatusTopic.expectedBodiesReceived("<status>error</status><message>Unmarshaling failed</message>");

        producerTemplate.sendBody("direct:requests", "<not_weather><temperature>25</temperature>" +
                "<pressure>760</pressure><humidity>75</humidity><date>2002-09-24</date></weather>");

        kafkaStatusTopic.assertIsSatisfied(5000);
    }
}
