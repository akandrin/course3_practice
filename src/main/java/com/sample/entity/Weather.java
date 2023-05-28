package com.sample.entity;

import javax.persistence.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;

@Data
@Entity
@Table(name = "temperature_records")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer temperature;

    //@Column(nullable = false)
    //private Integer pressure;

    //@Column(nullable = false)
    //private Integer humidity;

    //@Column(nullable = false)
    //private Date date;
}
