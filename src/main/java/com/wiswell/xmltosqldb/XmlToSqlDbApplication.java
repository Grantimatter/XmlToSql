package com.wiswell.xmltosqldb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XmlToSqlDbApplication {

    private static PopulateDB populate;

    public XmlToSqlDbApplication(PopulateDB populate) {
        XmlToSqlDbApplication.populate = populate;
    }

    public static void main(String[] args) {
        SpringApplication.run(XmlToSqlDbApplication.class, args);
        populate.start();
    }
}
