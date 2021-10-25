package com.wiswell.xmltosqldb.util;

import com.wiswell.xmltosqldb.model.Database;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseContextHolder {

    private static ThreadLocal<Database> CONTEXT = new ThreadLocal<>();

    @Getter
    private static List<Database> databaseList = new ArrayList<>();
    private static int currentDatabaseIndex;
    private final Environment environment;
    private final Logger log = LogManager.getLogger(DatabaseContextHolder.class);

    public DatabaseContextHolder(Environment environment) {
        this.environment = environment;
        databaseList = getDatabasesAndFiles();
        set(databaseList.get(0));
    }

    public static void set(Database database) {
        Assert.notNull(database, "Database cannot be null");
        CONTEXT.set(database);
    }

    public static Database getDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static void incrementDatabase() {
        if(currentDatabaseIndex + 1 < databaseList.size()) {
            set(databaseList.get(++currentDatabaseIndex));
        }
    }

    public static int getDatabaseCount() {
        return databaseList.size();
    }

    /**
     * Search through application.properties and find all databases and corresponding xml documents
     * @return a list of all databases in the properties with their associated files
     */
    private List<Database> getDatabasesAndFiles() {
        List<Database> newDatabaseList = new ArrayList<>();
        boolean areMoreDatabases = true;
        int i = 1;

        do {
            String databaseProperty = String.format("database%d", i);
            String xmlProperty = String.format("xml%d", i);
            Database newDatabase = getDatabaseFromProperties(databaseProperty, xmlProperty);

            if(newDatabase.getUrl() == null || newDatabase.getUrl().length() == 0) {
                areMoreDatabases = false;
            } else if(newDatabase.Validate(databaseProperty)) {
                newDatabaseList.add(newDatabase);
                String message = String.format("%s added successfully", databaseProperty);
                log.info(message);
            } else {
                String message = String.format("%s failed to be validated. It will not be processed", databaseProperty);
                log.warn(message);
            }

            i++;

        } while(areMoreDatabases);

        return newDatabaseList;
    }

    private Database getDatabaseFromProperties(String databaseProperty, String xmlProperty) {
        String databaseUrl = environment.getProperty(String.format("%s.url", databaseProperty));
        String databaseUsername = environment.getProperty(String.format("%s.username", databaseProperty));
        String databasePassword = environment.getProperty(String.format("%s.password", databaseProperty));
        String xmlPath = environment.getProperty(xmlProperty);

        return new Database(databaseUrl, databaseUsername, databasePassword, xmlPath);
    }
}
