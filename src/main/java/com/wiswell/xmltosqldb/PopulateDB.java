package com.wiswell.xmltosqldb;

import com.wiswell.xmltosqldb.model.Database;
import com.wiswell.xmltosqldb.model.KeyValue;
import com.wiswell.xmltosqldb.service.KeyValueService;
import com.wiswell.xmltosqldb.util.XmlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopulateDB {

    private final Logger log = LogManager.getLogger(PopulateDB.class);
    private final KeyValueService keyValueService;
    private final Environment environment;

    private static final String FILEPATH = "C:\\Users\\gwisw\\Desktop\\testfile.txt";

    public PopulateDB(KeyValueService keyValueService, Environment environment) {
        this.keyValueService = keyValueService;
        this.environment = environment;
    }

    public void start() {
        List<Database> databaseList = getDatabasesAndFiles();
        List<KeyValue> keyValueList = XmlHelper.getKeyValuePairs(FILEPATH);

        String message = String.format("Retrieved %d elements%n", keyValueList.size());
        log.info(message);

        keyValueList.forEach(keyValueService::createKeyValuePair);
    }

    /**
     * Search through application.properties and find all databases and corresponding xml documents
     * @return a list of all databases in the properties with their associated files
     */
    private List<Database> getDatabasesAndFiles() {
        List<Database> databaseList = new ArrayList<>();
        
        boolean moreDatabases = true;
        int i = 1;
        do {
            String databaseProperty = String.format("database%d", i);
            if(environment.getProperty(String.format("%s.url",databaseProperty)) != null) {
                String databaseUrl = environment.getProperty(String.format("%s.url", databaseProperty));
                String databaseUsername = environment.getProperty(String.format("%s.username", databaseProperty));
                String databasePassword = environment.getProperty(String.format("%s.password", databaseProperty));
                if(databaseUrl == null || databaseUsername == null || databasePassword == null) {
                    String message = String.format("%s is a missing url, username, and/or password property", databaseProperty);
                    log.warn(message);
                    i++;
                    continue;
                }

                String xmlDocumentProperty = String.format("xml%d", i);
                String xmlDocumentPath = environment.getProperty(xmlDocumentProperty);
                log.info(String.format("%s path: %s", xmlDocumentProperty, xmlDocumentPath));
                if(xmlDocumentPath != null && xmlDocumentPath.length() > 0) {
                    Database newDatabase = new Database(databaseUrl, databaseUsername, databasePassword, xmlDocumentPath);

                    if(newDatabase.Validate(String.format("database%d",i))) {
                        String successMessage = String.format("%s added successfully", databaseProperty);
                        databaseList.add(newDatabase);
                        log.info(successMessage);
                    } else {
                        String failMessage = String.format("%s failed to be validated. It will not be processed", databaseProperty);
                        log.warn(failMessage);
                    }
                } else {
                    String message = String.format("%s does not have a valid xml document. Add a path to the xml document under the property name \"%s\"", databaseProperty, xmlDocumentProperty);
                    log.warn(message);
                }

                i++;
            } else {
                moreDatabases = false;
            }
        } while(moreDatabases);

        return databaseList;
    }
}
